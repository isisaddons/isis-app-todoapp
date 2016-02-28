package todoapp.app.services.routing;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.datanucleus.enhancement.Persistable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.homepage.HomePageProviderService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.routing.RoutingServiceDefault;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.EntityModel;
import org.apache.isis.viewer.wicket.ui.components.widgets.breadcrumbs.BreadcrumbModel;
import org.apache.isis.viewer.wicket.ui.components.widgets.breadcrumbs.BreadcrumbModelProvider;

@DomainService(nature = NatureOfService.DOMAIN)
@DomainServiceLayout(menuOrder = "1")
public class RoutingServiceUsingBreadcrumbs extends RoutingServiceDefault {

    @Override
    public boolean canRoute(final Object original) {
        return super.canRoute(original);
    }

    @Override
    public Object route(final Object original) {

        if(original != null) {
            return original;
        }

        // ensure that any persisted objects have been deleted.
        container.flush();

        final BreadcrumbModelProvider  wicketSession = (BreadcrumbModelProvider) AuthenticatedWebSession.get();
        final BreadcrumbModel breadcrumbModel = wicketSession.getBreadcrumbModel();

        final List<EntityModel> breadcrumbs = breadcrumbModel.getList();
        final Optional<Object> firstViewModelOrNonDeletedPojoIfAny = breadcrumbs.stream()
                .filter(entityModel -> entityModel != null).map(EntityModel::getObject)
                .filter(objectAdapter -> objectAdapter != null).map(ObjectAdapter::getObject)
                .filter(pojo -> !(pojo instanceof Persistable) || !((Persistable)pojo).dnIsDeleted())
                .findFirst();
        return firstViewModelOrNonDeletedPojoIfAny.orElse(homePage());
    }

    private Object homePage() {
        return homePageProviderService.homePage();
    }

    @Inject
    HomePageProviderService homePageProviderService;
    @Inject
    DomainObjectContainer container;
    @Inject
    IsisJdoSupport isisJdoSupport;

}
