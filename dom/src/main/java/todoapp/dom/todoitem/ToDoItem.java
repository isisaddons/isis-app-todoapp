/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package todoapp.dom.todoitem;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.Digits;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

import org.joda.time.LocalDate;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.NonRecoverableException;
import org.apache.isis.applib.RecoverableException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.InvokedOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.security.UserMemento;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.EventBusService;
import org.apache.isis.applib.services.eventbus.ObjectCreatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectLoadedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistingEvent;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.scratchpad.Scratchpad;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.services.user.UserService;
import org.apache.isis.applib.services.wrapper.HiddenException;
import org.apache.isis.applib.services.wrapper.WrapperFactory;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyRepository;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.isisaddons.wicket.gmap3.cpt.service.LocationLookupService;

import lombok.Getter;
import lombok.Setter;
import todoapp.dom.ToDoAppDomainModule;
import todoapp.dom.categories.Categorized;
import todoapp.dom.categories.Category;
import todoapp.dom.categories.Subcategory;
import todoapp.dom.seed.tenancies.UsersTenancy;

@javax.jdo.annotations.PersistenceCapable(
        schema = "todo",
        table = "ToDoItem",
        identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name="ToDoItem_description_must_be_unique", 
            members={"atPath","description"})
})
// queries only used if ToDoItemRepositoryImplUsingJdoql is used rather than ToDoItemRepositoryImplUsingTypesafeQueries
@javax.jdo.annotations.Queries( {
    @javax.jdo.annotations.Query(
            name = "findByAtPathAndComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM todoapp.dom.todoitem.ToDoItem "
                    + "WHERE atPath.indexOf(:atPath) == 0 "
                    + "   && complete == :complete"),
    @javax.jdo.annotations.Query(
            name = "findByAtPathAndCategory", language = "JDOQL",
            value = "SELECT "
                    + "FROM todoapp.dom.todoitem.ToDoItem "
                    + "WHERE atPath.indexOf(:atPath) == 0 "
                    + "   && category == :category"),
    @javax.jdo.annotations.Query(
            name = "findByAtPathAndDescriptionContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM todoapp.dom.todoitem.ToDoItem "
                    + "WHERE atPath.indexOf(:atPath) == 0 "
                    + "   && description.indexOf(:description) >= 0")
})
@DomainObject(
        autoCompleteRepository = ToDoItems.class, // for drop-downs, unless autoCompleteNXxx() or choicesNXxx() present
        autoCompleteAction = "autoComplete",
        updatedLifecycleEvent = ToDoItem.UpdatedEvent.class
)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
public class ToDoItem implements Categorized, Comparable<ToDoItem>, Locatable, CalendarEventable {


    /**
     * It isn't common for entities to log, but they can if required.  
     * Isis uses slf4j API internally (with log4j as implementation), and is the recommended API to use.
     */
    final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ToDoItem.class);

    // region > title, icon, cssClass
    public String title() {

        final TitleBuffer buf = new TitleBuffer();
        buf.append(getDescription());
        if (isComplete()) {
            buf.append("- Completed!");
        } else {
            try {
                final LocalDate dueBy = wrapperFactory.wrap(this).getDueBy();
                if (dueBy != null) {
                    buf.append(" due by", dueBy);
                }
            } catch(final HiddenException ignored) {
            }
        }
        return buf.toString();
    }
    
    public String iconName() {
        return !isComplete() ? "todo" : "done";
    }

    /**
     * Provides a strikethrough for "done" items, see <code>application.css</code>.
     */
    public String cssClass() { return iconName(); }

    //endregion

    //region > description (property)

    public static class DescriptionDomainEvent extends PropertyDomainEvent<String> { }

    @javax.jdo.annotations.Column(allowsNull="false", length=100)
    @Property(
        domainEvent = DescriptionDomainEvent.class,
        regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*",
            editing = Editing.ENABLED
    )
    @Getter @Setter
    private String description;
    //endregion

    //region > dueBy (property), Calendarable impl

    public static class DueByDomainEvent extends PropertyDomainEvent<LocalDate> { }

    @javax.jdo.annotations.Persistent(defaultFetchGroup="true")
    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            domainEvent = DueByDomainEvent.class
    )
    @Getter @Setter
    private LocalDate dueBy;


    /**
     * Demonstrates how to perform security checks within the domain code.
     *
     * <p>
     *     Generally speaking this approach is not recommended; such checks should
     *     wherever possible be externalized in the security subsystem.
     * </p>
     */
    public boolean hideDueBy() {
        final UserMemento user = userService.getUser();
        return user.hasRole("realm1:noDueBy_role");
    }

    // proposed new value is validated before setting
    public String validateDueBy(final LocalDate dueBy) {
        if (dueBy == null) {
            return null;
        }
        return toDoItems.validateDueBy(dueBy);
    }
    //endregion

    //region > CalendarEventable impl
    @Programmatic
    @Override
    public String getCalendarName() {
        return "dueBy";
    }

    @Programmatic
    @Override
    public CalendarEvent toCalendarEvent() {
        return getDueBy() != null
                ? new CalendarEvent(
                getDueBy().toDateTimeAtStartOfDay(), "dueBy", titleService.titleOf(this))
                : null;
    }
    //endregion

    //region > category and subcategory (property)

    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            editing = Editing.DISABLED,
            editingDisabledReason = "Use action to update both category and subcategory"
    )
    @Getter @Setter
    private Category category;



    @javax.jdo.annotations.Column(allowsNull="true")
    @Property(
            editing = Editing.DISABLED,
            editingDisabledReason = "Use action to update both category and subcategory"
    )
    @Getter @Setter
    private Subcategory subcategory;

    //endregion

    //region > atPath (property)

    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            editing = Editing.DISABLED
            //hidden = Where.EVERYWHERE // for demo purposes, so is visible in the UI
    )
    @Getter @Setter
    private String atPath;

    //endregion

    //region > complete (property)
    @Property(
        editing = Editing.DISABLED,
        editingDisabledReason = "Use actions to change status"
    )
    @Getter @Setter
    private boolean complete;

    //endregion

    //region > completed (action)
    public static class CompletedDomainEvent extends ToDoItem.ActionDomainEvent { }

    @Action(
            domainEvent =CompletedDomainEvent.class,
            publishing = Publishing.ENABLED,
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    public ToDoItem completed() {
        setComplete(true);
        
        //
        // remainder of method just demonstrates the use of the ActionInvocationContext service
        //
        @SuppressWarnings("unused")
        final List<Object> allObjects = actionInvocationContext.getDomainObjects();

        LOG.debug("completed: "
                + actionInvocationContext.getIndex() +
                " [" + actionInvocationContext.getSize() + "]"
                + (actionInvocationContext.isFirst() ? " (first)" : "")
                + (actionInvocationContext.isLast() ? " (last)" : ""));

        // if invoked as a regular action, return this object;
        // otherwise return null (so go back to the list)
        return actionInvocationContext.getInvokedOn() == InvokedOn.COLLECTION? null: this;
    }
    // disable action dependent on state of object
    public String disableCompleted() {
        return isComplete() ? "Already completed" : null;
    }


    //endregion

    //region > notYetCompleted (action)

    public static class NotYetCompletedDomainEvent extends ActionDomainEvent { }

    @Action(
        domainEvent = NotYetCompletedDomainEvent.class,
        invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    public ToDoItem notYetCompleted() {
        setComplete(false);

        // if invoked as a regular action, return this object;
        // otherwise (if invoked as bulk), return null (so go back to the list)
        return actionInvocationContext.getInvokedOn() == InvokedOn.OBJECT ? this: null;
    }
    // disable action dependent on state of object
    public String disableNotYetCompleted() {
        return !complete ? "Not yet completed" : null;
    }
    //endregion

    //region > completeSlowly (property)
    @Action(
            hidden = Where.EVERYWHERE
    )
    public void completeSlowly(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ignored) {
        }
        setComplete(true);
    }
    //endregion

    //region > cost (property), updateCost (action)
    @javax.jdo.annotations.Column(allowsNull="true", scale=2)
    @javax.validation.constraints.Digits(integer=10, fraction=2)
    @Property(
            editing = Editing.DISABLED,
            editingDisabledReason = "Update using action"
    )
    @Getter @Setter
    private BigDecimal cost;


    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    public ToDoItem updateCost(
            @Parameter(optionality = Optionality.OPTIONAL)
            @Digits(integer = 10, fraction = 2)
            final BigDecimal newCost) {
        final String titleOf = titleService.titleOf(this);
        LOG.debug("%s: cost updated: %s -> %s", titleOf, getCost(), newCost);

        // just to simulate a long-running action
        try {
            Thread.sleep(3000);
        } catch (final InterruptedException ignored) {
        }

        setCost(newCost);
        return this;
    }

    // provide a default value for argument #0
    public BigDecimal default0UpdateCost() {
        return getCost();
    }

    // validate action arguments
    public String validateUpdateCost(final BigDecimal proposedCost) {
        if(proposedCost == null) { return null; }
        return proposedCost.compareTo(BigDecimal.ZERO) < 0? "Cost must be positive": null;
    }
    //endregion

    //region > notes (property)
    @javax.jdo.annotations.Column(allowsNull="true", length=400)
    @Setter
    private String notes;
    //@SummernoteEditor // causing an issue :-(
    public String getNotes() {
        return notes;
    }
    //endregion


    //region > location (property), Locatable impl

    public static class LocationDomainEvent extends ToDoItem.PropertyDomainEvent<Location> { }

    private Double locationLatitude;
    private Double locationLongitude;

    @Property(
            //ISIS-1138: Location value type not parsed from string, so fails to locate constructor
            //domainEvent = LocationDomainEvent.class,
            optionality = Optionality.OPTIONAL
    )
    public Location getLocation() {
        return locationLatitude != null && locationLongitude != null? new Location(locationLatitude, locationLongitude): null;
    }
    public void setLocation(final Location location) {
        locationLongitude = location != null ? location.getLongitude() : null;
        locationLatitude = location != null ? location.getLatitude() : null;
    }

    @MemberOrder(name="location", sequence="1")
    public ToDoItem updateLocation(
            final String address) {
        final Location location = this.locationLookupService.lookup(address);
        setLocation(location);
        return this;
    }

    //endregion

    //region > attachment (property)
    public static class AttachmentDomainEvent extends PropertyDomainEvent<Blob> { }

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "attachment_name"),
            @javax.jdo.annotations.Column(name = "attachment_mimetype"),
            @javax.jdo.annotations.Column(name = "attachment_bytes", jdbcType = "BLOB", sqlType = "LONGVARBINARY")
    })
    @Property(
            domainEvent = AttachmentDomainEvent.class,
            optionality = Optionality.OPTIONAL
    )
    @Getter @Setter
    private Blob attachment;
    //endregion

    //region > doc (property)
    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "doc_name"),
            @javax.jdo.annotations.Column(name = "doc_mimetype"),
            @javax.jdo.annotations.Column(name = "doc_chars", jdbcType = "CLOB", sqlType = "LONGVARCHAR")
    })
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @Getter @Setter
    private Clob doc;
    //endregion

    //region > dependencies (property), add (action), remove (action)

    // overrides the natural ordering
    public static class DependenciesComparator implements Comparator<ToDoItem> {
        @Override
        public int compare(final ToDoItem p, final ToDoItem q) {
            final Ordering<ToDoItem> byDescription = new Ordering<ToDoItem>() {
                public int compare(final ToDoItem p, final ToDoItem q) {
                    return Ordering.natural().nullsFirst().compare(p.getDescription(), q.getDescription());
                }
            };
            return byDescription
                    .compound(Ordering.<ToDoItem>natural())
                    .compare(p, q);
        }
    }

    @javax.jdo.annotations.Persistent(table="ToDoItemDependencies")
    @javax.jdo.annotations.Join(column="dependingId")
    @javax.jdo.annotations.Element(column="dependentId")

    private Set<ToDoItem> dependencies = new TreeSet<>();
    //private SortedSet<ToDoItem> dependencies = new TreeSet<>();  // not compatible with neo4j (as of DN v3.2.3)

    @Collection()
    // @CollectionLayout(sortedBy = DependenciesComparator.class) // not compatible with neo4j (as of DN v3.2.3)
    public Set<ToDoItem> getDependencies() {
        return dependencies;
    }

    public void setDependencies(final Set<ToDoItem> dependencies) {
        this.dependencies = dependencies;
    }

    public void addToDependencies(final ToDoItem toDoItem) {
        getDependencies().add(toDoItem);
    }
    public void removeFromDependencies(final ToDoItem toDoItem) {
        getDependencies().remove(toDoItem);
    }

    public ToDoItem add(final ToDoItem toDoItem) {
        // By wrapping the call, Isis will detect that the collection is modified
        // and it will automatically send CollectionInteractionEvents to the Event Bus.
        // ToDoItemSubscriptions is a demo subscriber to this event
        wrapperFactory.wrapSkipRules(this).addToDependencies(toDoItem);
        return this;
    }
    public List<ToDoItem> autoComplete0Add(
            @Parameter(minLength = 2)
            final String search) {
        final List<ToDoItem> list = toDoItems.autoComplete(search);
        list.removeAll(getDependencies());
        list.remove(this);
        return list;
    }

    public String disableAdd(final ToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot add dependencies for items that are complete";
        }
        return null;
    }
    // validate the provided argument prior to invoking action
    public String validateAdd(final ToDoItem toDoItem) {
        if(getDependencies().contains(toDoItem)) {
            return "Already a dependency";
        }
        if(toDoItem == this) {
            return "Can't set up a dependency to self";
        }
        return null;
    }

    public ToDoItem remove(final ToDoItem toDoItem) {
        // By wrapping the call, Isis will detect that the collection is modified
        // and it will automatically send a CollectionInteractionEvent to the Event Bus.
        // ToDoItemSubscriptions is a demo subscriber to this event
        wrapperFactory.wrapSkipRules(this).removeFromDependencies(toDoItem);
        return this;
    }
    // disable action dependent on state of object
    public String disableRemove(final ToDoItem toDoItem) {
        if(isComplete()) {
            return "Cannot remove dependencies for items that are complete";
        }
        return getDependencies().isEmpty()? "No dependencies to remove": null;
    }
    // validate the provided argument prior to invoking action
    public String validateRemove(final ToDoItem toDoItem) {
        if(!getDependencies().contains(toDoItem)) {
            return "Not a dependency";
        }
        return null;
    }
    // provide a drop-down
    public java.util.Collection<ToDoItem> choices0Remove() {
        return getDependencies();
    }
    //endregion

    //region > clone (action)

    // the name of the action in the UI
    // nb: method is not called "clone()" is inherited by java.lang.Object and
    // (a) has different semantics and (b) is in any case automatically ignored
    // by the framework
    public ToDoItem duplicate(
            @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*" )
            final String description,
            final Category category,
            final Subcategory subcategory,
            @Parameter(optionality = Optionality.OPTIONAL)
            final LocalDate dueBy,
            @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal cost) {
        return toDoItems.newToDo(description, category, subcategory, dueBy, cost);
    }
    public String default0Duplicate() {
        return getDescription() + " - Copy";
    }
    public Category default1Duplicate() {
        return getCategory();
    }
    public Subcategory default2Duplicate() {
        return getSubcategory();
    }
    public LocalDate default3Duplicate() {
        return getDueBy();
    }
    public List<Subcategory> choices2Duplicate(
            final String description, final Category category) {
        return toDoItems.choices2NewToDo(description, category);
    }
    public String validateDuplicate(
            final String description, 
            final Category category, final Subcategory subcategory,
            final LocalDate dueBy, final BigDecimal cost) {
        return toDoItems.validateNewToDo(description, category, subcategory, dueBy, cost);
    }
    //endregion

    //region > delete (action)

    public static class DeletedDomainEvent extends ToDoItem.ActionDomainEvent { }

    @Action(
            domainEvent = DeletedDomainEvent.class,
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    public Object delete() {

        // obtain title first, because cannot reference object after deleted
        final String title = titleService.titleOf(this);

        final List<ToDoItem> returnList = actionInvocationContext.getInvokedOn().isCollection() ? toDoItems.notYetComplete() : null;

        // there's actually a bug in this method; shouldn't be returning the current object in the list if just deleted.
        // however, ISIS-1269 transparently handles this and won't attempt to render a deleted object.
        repositoryService.remove(this);

        messageService.informUser(
                TranslatableString.tr("Deleted {title}", "title", title), this.getClass(), "delete");

        return returnList;
    }
    //endregion

    //region > totalCost (property)
    @Action(
            semantics = SemanticsOf.SAFE,
            invokeOn = InvokeOn.COLLECTION_ONLY
    )
    public BigDecimal totalCost() {
        BigDecimal total = (BigDecimal) scratchpad.get("runningTotal");
        if(getCost() != null) {
            total = total != null ? total.add(getCost()) : getCost();
            scratchpad.put("runningTotal", total);
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    //endregion

    //region > openSourceCodeOnGithub (action)
    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public URL openSourceCodeOnGithub() throws MalformedURLException {
        return new URL("https://github.com/isisaddons/isis-app-todoapp/blob/master/dom/src/main/java/todoapp/dom/todoitem/ToDoItem.java");
    }
    //endregion

    //region > demoException (action)

    enum DemoExceptionType {
        RECOVERABLE_EXCEPTION,
        RECOVERABLE_EXCEPTION_AUTO_ESCALATED,
        NON_RECOVERABLE_EXCEPTION;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    public void demoException(
            final DemoExceptionType type) {
        switch(type) {
        case NON_RECOVERABLE_EXCEPTION:
            throw new NonRecoverableException("Demo throwing " + type.name());
        case RECOVERABLE_EXCEPTION:
            throw new RecoverableException("Demo throwing " + type.name());
        case RECOVERABLE_EXCEPTION_AUTO_ESCALATED:
            try {
                // this will trigger an exception (because category cannot be null), causing the xactn to be aborted
                setCategory(null);
                transactionService.flushTransaction();
            } catch(final Exception e) {
                // it's a programming mistake to throw only a recoverable exception here, because of the xactn's state.
                // the framework should instead auto-escalate this to a non-recoverable exception
                throw new RecoverableException("Demo throwing " + type.name(), e);
            }
        }
    }
    //endregion

    //region > lifecycle callbacks
    public void created() {
        LOG.debug("lifecycle callback: created: " + this.toString());
    }
    public void loaded() {
        LOG.debug("lifecycle callback: loaded: " + this.toString());
    }
    public void persisting() {
        LOG.debug("lifecycle callback: persisting: " + this.toString());
    }
    public void persisted() {
        LOG.debug("lifecycle callback: persisted: " + this.toString());
    }
    public void updating() {
        LOG.debug("lifecycle callback: updating: " + this.toString());
    }
    public void updated() {
        LOG.debug("lifecycle callback: updated: " + this.toString());
    }
    public void removing() {
        LOG.debug("lifecycle callback: removing: " + this.toString());
    }
    public void removed() {
        LOG.debug("lifecycle callback: removed: " + this.toString());
    }
    //endregion

    //region > object-level validation
    /**
     * Prevent user from viewing another user's data.
     */
    public boolean hidden() {
        // previously we manually checked that the user couldn't modify an object owned by some other user.
        // however, with application tenancy support this is automatically taken care of by Isis.
        return false;
    }

    /**
     * Prevent user from modifying any other user's data.
     */
    public String disabled(final Identifier.Type identifierType){
        // previously we manually checked that the user couldn't modify an object owned by some other user.
        // however, with application tenancy support this is automatically taken care of by Isis.
        return null;
    }

    /**
     * In a real app, if this were actually a rule, then we'd expect that
     * invoking the {@link #completed() done} action would clear the {@link #getDueBy() dueBy}
     * property (rather than require the user to have to clear manually).
     */
    public String validate() {
        if(isComplete() && getDueBy() != null) {
            return "Due by date must be set to null if item has been completed";
        }
        return null;
    }
    //endregion

    //region > programmatic helpers
    @Programmatic // excluded from the framework's metamodel
    public boolean isDue() {
        if (getDueBy() == null) {
            return false;
        }
        return !toDoItems.isMoreThanOneWeekInPast(getDueBy());
    }
    //endregion

    //region > events
    public static abstract class PropertyDomainEvent<T> extends ToDoAppDomainModule.PropertyDomainEvent<ToDoItem, T> { }
    public static abstract class ActionDomainEvent extends ToDoAppDomainModule.ActionDomainEvent<ToDoItem> { }
    //endregion

    //region > predicates

    public static class Predicates {
        
        private Predicates () {}
        
        public static Predicate<ToDoItem> thoseWithAtPath(final String currentUser) {
            return toDoItem -> Objects.equal(toDoItem.getAtPath(), UsersTenancy.TENANCY_PATH + currentUser);
        }

        public static Predicate<ToDoItem> thoseCompleted(
                final boolean completed) {
            return t -> Objects.equal(t.isComplete(), completed);
        }

        public static Predicate<ToDoItem> thoseNot(final ToDoItem toDoItem) {
            return t -> t != toDoItem;
        }

        public static Predicate<ToDoItem> thoseCategorised(final Category category) {
            return toDoItem -> Objects.equal(toDoItem.getCategory(), category);
        }

        public static Predicate<ToDoItem> thoseSubcategorised(final Subcategory subcategory) {
            return t -> Objects.equal(t.getSubcategory(), subcategory);
        }

        public static Predicate<ToDoItem> thoseCategorised(
                final Category category, final Subcategory subcategory) {
            return com.google.common.base.Predicates.and(
                    thoseCategorised(category), 
                    thoseSubcategorised(subcategory)); 
        }

        public static Predicate<ToDoItem> thoseWithDueByDate() {
            return input -> input.getDueBy() != null;
        }
    }

    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "description,complete,dueBy,atPath");
    }

    /**
     * Required so can store in {@link SortedSet sorted set}s (eg {@link #getDependencies()}). 
     */
    @Override
    public int compareTo(final ToDoItem other) {
        return ObjectContracts.compare(this, other, "complete,dueBy,description");
    }
    //endregion

    //region > lifecycle events
    public static class CreatedEvent extends ObjectCreatedEvent<ToDoItem> {}
    public static class LoadedEvent extends ObjectLoadedEvent<ToDoItem> {}
    public static class PersistedEvent extends ObjectPersistedEvent<ToDoItem> {}
    public static class PersistingEvent extends ObjectPersistingEvent<ToDoItem> {}
    public static class UpdatedEvent extends ObjectUpdatedEvent<ToDoItem> {}
    public static class UpdatingEvent extends ObjectUpdatingEvent<ToDoItem> {}
    public static class RemovingEvent extends ObjectRemovingEvent<ToDoItem> {}
    //endregion

    //region > injected services
    @javax.inject.Inject
    MessageService messageService;

    @javax.inject.Inject
    TransactionService transactionService;

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    UserService userService;

    @javax.inject.Inject
    ToDoItems toDoItems;

    @javax.inject.Inject
    Scratchpad scratchpad;

    /**
     * public only so can be injected from integ tests
     */
    @javax.inject.Inject
    public ActionInvocationContext actionInvocationContext;

    /**
     * public only so can be injected from integ tests
     */
    @javax.inject.Inject
    public EventBusService eventBusService;

    @javax.inject.Inject
    WrapperFactory wrapperFactory;

    @javax.inject.Inject
    private LocationLookupService locationLookupService;

    @javax.inject.Inject
    private ApplicationTenancyRepository applicationTenancyRepository;

    @javax.inject.Inject
    private MeService meService;
    //endregion


}
