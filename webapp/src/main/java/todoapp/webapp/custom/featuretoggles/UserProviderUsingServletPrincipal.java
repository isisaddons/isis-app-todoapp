package todoapp.webapp.custom.featuretoggles;

import java.security.Principal;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;

import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;
import org.togglz.servlet.util.HttpServletRequestHolder;

import org.apache.isis.core.runtime.system.context.IsisContext;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import todoapp.dom.seed.roles.FeatureToggleAdminRole;

class UserProviderUsingServletPrincipal implements UserProvider {

    @Override
    public FeatureUser getCurrentUser() {
        final String principalName = getCurrentPrincipalName();
        if(principalName == null) {
            return null;
        }
        return IsisContext.doInSession(() -> {
            final ApplicationUsers applicationUsers = lookupService(ApplicationUsers.class);
            final ApplicationUser applicationUser = applicationUsers.findUserByUsername(principalName);
            if(applicationUser == null) {
                return null;
            }
            return new SimpleFeatureUser(applicationUser.getName(), isAdmin(applicationUser));
        });
    }

    public String getCurrentPrincipalName() {
        HttpServletRequest request = HttpServletRequestHolder.get();
        if (request == null) {
            throw new IllegalStateException(
                    "Could not get request from HttpServletRequestHolder. Did you configure the TogglzFilter correctly?");
        }

        Principal  principal = request.getUserPrincipal();
        return principal != null? principal.getName(): null;
    }

    private boolean isAdmin(final ApplicationUser applicationUser) {
        final SortedSet<ApplicationRole> roles = applicationUser.getRoles();
        for (ApplicationRole role : roles) {
            if (role.getName().equals(FeatureToggleAdminRole.ROLE_NAME)) {
                return true;
            }
        }
        return false;
    }

    private <T> T lookupService(final Class<T> serviceClass) {
        return Util.lookupService(serviceClass);
    }

}
