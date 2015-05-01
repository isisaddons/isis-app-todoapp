# isis-app-todoapp #

[![Build Status](https://travis-ci.org/isisaddons/isis-app-todoapp.png?branch=master)](https://travis-ci.org/isisaddons/isis-app-todoapp)

[Apache Isis](http://isis.apache.org)™ software is a framework for rapidly developing domain-driven apps in Java.  You write your business logic in entities, domain services and repositories, and the framework dynamically generates a representation of that domain model as a webapp or a RESTful API.

The repository contains a TodoApp example app that is a reasonably complete application for tracking to-do items, based around a single domain class `ToDoItem` and repository, `ToDoItems`.

While not quite a "kitchen-sink" example (there is, after all, just a single domain class), the app nevertheless demonstrates a good number of Isis' capabilities: the use of contributed actions/collections/properties is demonstrated by `ToDoItemContributions`; view models are demonstrated by `ToDoItemsByCategoryViewModel` and `ToDoItemsByDateRangeViewModel`; a dashboard is demonstrated by `ToDoAppDashboard`; the use of the internal event bus is demonstrated through the `DemoDomainEventSubscriptions` service.

The app also integrates with many of the [Isis Addons](https://www.isisaddons.org), such as [security](https://github.com/isisaddons/isis-module-security), [command profiling](https://github.com/isisaddons/isis-module-command), [auditing](https://github.com/isisaddons/isis-module-audit), [event publishing](https://github.com/isisaddons/isis-module-publishing).  While the Isis Addons are not part of the Apache Software Foundation, they are all licensed under Apache License 2.0 and are maintained by the Isis committers.

Running this app is a good way to get familiar with the structure of a not-too-complex Isis application.  However, to get started with your own application, we generally recommend that you use the [simple archetype](http://isis.apache.org/intro/getting-started/simpleapp-archetype.html).  This will generate a completely stripped back and minimal application for you to refactor and extend; you can then use this example todoapp to guide your own development.


## Screenshots

The screenshots below should give you an idea of what Isis is all about.


### Sign-in

Apache Isis integrates with [Apache Shiro](http://shiro.apacheorg)™.  The core framework supports file-based realms, while the Isis Addons [security module](http://github.com/isisaddons/isis-module-security) provides a well-features subdomain of users, roles and permissions against features derived from the Isis metamodel.  The example todoapp integrates with the security module.

![](https://raw.github.com/apache/isis/master/images/010-login.png)

### Install Fixtures

Apache Isis has lots of features to help you prototype and then fully test your application.  One such are fixture scripts, which allow pre-canned data to be installed in the running application.  This is great to act as the starting point for identifying new stories; later on when the feature is being implemented, the same fixture script can be re-used within that feature's integration tests.  (More on tests later).

![](https://raw.github.com/apache/isis/master/images/020-install-fixtures.png)

### Dashboard and View Models

Most of the time the end-user interacts with representations of persistent domain entities, but Isis also supports view models which can aggregate data from multiple sources.  The todoapp example uses a "dashboard" view model to list todo items not yet done vs those completed.

![](https://raw.github.com/apache/isis/master/images/030-dashboard-view-model.png)

In general we recommend to initially focus only on domain entities; this will help drive out a good domain model.  Later on view models can be introduced in support of specific use cases.

### Domain Entity

The screenshot below is of the todoapp's `ToDoItem` domain entity.  Like all web pages, this UI is generated at runtime, directly from the domain object itself.  There are no controllers or HTML to write.

![](https://raw.github.com/apache/isis/master/images/040-domain-entity.png)

In addition to the domain entity, Apache Isis allows layout metadata hints to be provided, for example to specify the grouping of properties, the positioning of those groups into columns, the association of actions (the buttons) with properties or collections, the icons on the buttons, and so on.  This metadata can be specified either as annotations or in JSON form; the benefit of the latter is that it can be updated (and the UI redrawn) without restarting the app.

Any production-ready app will require this metadata but (like the view models discussed above) this metadata can be added gradually on top of the core domain model.

### Edit properties

By default properties on domain entities are editable, meaning they can be changed directly.  In the todoapp example, the `ToDoItem`'s description is one such editable property:

![](https://raw.github.com/apache/isis/master/images/050-edit-property.png)

Note that some of the properties are read-only even in edit mode; individual properties can be made non-editable.  It is also possible to make all properties disabled and thus enforce changes only through actions (below).

### Actions

The other way to modify an entity is to an invoke an action.  In the screenshot below the `ToDoItem`'s category and subcategory can be updated together using an action:

![](https://raw.github.com/apache/isis/master/images/060-invoke-action.png)

There are no limitations on what an action can do; it might just update a single object, it could update multiple objects.  Or, it might not update any objects at all, but could instead perform some other activity, such as sending out email or printing a document.

In general though, all actions are associated with some object, and are (at least initially) also implemented by that object: good old-fashioned encapsulation.  We sometimes use the term "behaviourally complete" for such domain objects.

### Contributions

As an alternative to placing actions (business logic) on a domain object, it can instead be placed on an (application-scoped, stateless) domain service.  When an object is rendered by Apache Isis, it will automatically render all "contributed" behaviour; rather like traits or aspect-oriented mix-ins).

In the screenshot below the highlighted "export as xml" action, the "relative priority" property (and "previous" and "next" actions) and also the "similar to" collection are all contributed:

![](https://raw.github.com/apache/isis/master/images/065-contributions.png)

Contributions are defined by the signature of the actions on the contributing service.  The code snippet below shows how this works for the "export as xml" action:

![](https://raw.github.com/apache/isis/master/images/067-contributed-action.png)

## Extensible Views

The Apache Isis viewer is implemented using [Apache Wicket](http://wicket.apache.org)™, and has been architected to be extensible.  For example, when a collection of objects is rendered, this is just one several views, as shown in the selector drop-down:

![](https://raw.github.com/apache/isis/master/images/070-pluggable-views.png)

The Isis Addons' [gmap3 component](https://github.com/isisaddons/isis-wicket-gmap3) will render any domain entity (such as `ToDoItem`) that implements its `Locatable` interface:

![](https://raw.github.com/apache/isis/master/images/080-gmap3-view.png)

Simiarly the Isis Addons' [fullcalendar2 component](https://github.com/isisaddons/isis-wicket-fullcalendar2) will render any domain entity (such as `ToDoItem`) that implements its `Calendarable` interface:

![](https://raw.github.com/apache/isis/master/images/090-fullcalendar2-view.png)

Yet another "view" (though this one is rather simpler is that provided by the Isis Addons [excel component](https://github.com/isisaddons/isis-wicket-excel).  This provides a download button to the table as a spreadsheet:

![](https://raw.github.com/apache/isis/master/images/100-excel-view-and-docx.png)

The screenshot above also shows an "export to Word" action.  This is *not* a view but instead is a (contributed) action that uses the Isis Addons [docx module](https://github.com/isisaddons/isis-module-docx) to perform a "mail-merge":

![](https://raw.github.com/apache/isis/master/images/110-docx.png)

## Security, Auditing and other Services

As well as providing extensions to the UI, the Isis addons provides a rich set of modules to support various cross-cutting concerns.

Under the activity menu are four sets of services which provide support for [user session logging/auditing](https://github.com/isisaddons/isis-module-sessionlogger), [command profiling](https://github.com/isisaddons/isis-module-command), [(object change) auditing](https://github.com/isisaddons/isis-module-audit) (shown) and (inter-system) [event publishing](https://github.com/isisaddons/isis-module-publishing):

![](https://raw.github.com/apache/isis/master/images/120-auditing.png)

In the security menu is access to the rich set of functionality provided by the Isis addons [security module](https://github.com/isisaddons/isis-module-security):

![](https://raw.github.com/apache/isis/master/images/130-security.png)

In the prototyping menu is the ability to download a GNU gettext `.po` file for translation.  This file can then be translated into multiple languages so that your app can support different locales.  Note that this feature is part of Apache Isis core (it is not in Isis Addons):

![](https://raw.github.com/apache/isis/master/images/140-i18n.png)

The Isis addons also provides a module for managing application and user [settings](https://github.com/isisaddons/isis-module-settings).  Most apps (the todoapp example included) won't expose these services directly, but will usually wrap them in their own app-specific settings service that trivially delegates to the settings module's services:

![](https://raw.github.com/apache/isis/master/images/150-appsettings.png)

### Multi-tenancy support

Of the various Isis addons, the [security module](https://github.com/isisaddons/isis-module-security) has the most features.  One significant feature is the ability to associate users and objects with a "tenancy".  The todoapp uses this feature so that different users' list of todo items are kept separate from one another.  A user with administrator is able to switch their own "tenancy" to the tenancy of some other user, in order to access the objects in that tenancy:

![](https://raw.github.com/apache/isis/master/images/160-switch-tenancy.png)

For more details, see the [security module](https://github.com/isisaddons/isis-module-security) README.

### Me

Most of the [security module](https://github.com/isisaddons/isis-module-security)'s services are on the security module, which would normally be provided only to administrators.  Kept separate is the "me" action:

![](https://raw.github.com/apache/isis/master/images/170-me.png)

Assuming they have been granted permissions, this allows a user to access an entity representing their own user account:

![](https://raw.github.com/apache/isis/master/images/180-app-user-entity.png)

If not all of these properties are required, then they can be hidden either using security or though Isis' internal event bus (described below).  Conversely, additional properties can be "grafted onto" the user using the contributed properties/collections discussed previously.

### Themes

Apache Isis' Wicket viewer uses [Twitter Bootstrap](http://getbootstrap.com), which means that it can be themed.  If more than one theme has been configured for the app, then the viewer allows the end-user to switch their theme:

![](https://raw.github.com/apache/isis/master/images/190-switch-theme.png)

## REST API

In addition to Isis' Wicket viewer, it also provides a fully fledged REST API, as an implementation of the [Restful Objects](http://restfulobjects.org) specification.  The screenshot below shows accessing this REST API using a Chrome plugin:

![](https://raw.github.com/apache/isis/master/images/200-rest-api.png)

Like the Wicket viewer, the REST API is generated automatically from the domain objects (entities and view models).

## Integration Testing Support

Earlier on we noted that Apache Isis allows fixtures to be installed through the UI.  These same fixture scripts can be reused within integration tests.  For example, the code snippet below shows how the  `FixtureScripts` service injected into an integration test can then be used to set up data:

![](https://raw.github.com/apache/isis/master/images/210-fixture-scripts.png)

The tests themselves are run in junit.  While these are integration tests (so talking to a real database), they are no more complex than a regular unit test:

![](https://raw.github.com/apache/isis/master/images/220-testing-happy-case.png)

To simulate the business rules enforced by Apache Isis, the domain object can be "wrapped" in a proxy.  For example, if using the Wicket viewer then Apache Isis will enforce the rule (implemented in the `ToDoItem` class itself) that a completed item cannot have the "completed" action invoked upon it.  The wrapper simulates this by throwing an appropriate exception:

![](https://raw.github.com/apache/isis/master/images/230-testing-wrapper-factory.png)

## Internal Event Bus

Contributions, discussed earlier, are an important tool in ensuring that the packages within your Isis application are decoupled; by extracting out actions the order of dependency between packages can effectively be reversed.

Another important tool to ensure your codebase remains maintainable is Isis' internal event bus.  It is probably best explained by example; the code below says that the "complete" action should emit a `ToDoItem.Completed` event:

![](https://raw.github.com/apache/isis/master/images/240-domain-events.png)

Domain service (application-scoped, stateless) can then subscribe to this event:

![](https://raw.github.com/apache/isis/master/images/250-domain-event-subscriber.png)

And this test verifies that completing an action causes the subscriber to be called:

![](https://raw.github.com/apache/isis/master/images/260-domain-event-test.png)

In fact, the domain event is fired not once, but (up to) 5 times.  It is called 3 times prior to execution, to check that the action is visible, enabled and that arguments are valid.  It is then additionally called prior to execution, and also called after execution.  What this means is that a subscriber can in either veto access to an action of some publishing object, and/or it can perform cascading updates if the action is allowed to proceed.

Moreover, domain events are fired for all properties and collections, not just actions.  Thus, subscribers can therefore switch on or switch off different parts of an application.  Indeed, the example todoapp demonstrates this.


## JDOQL vs Typesafe Queries

DataNucleus provides two ways to query the database; either using the (JDO API) JDOQL language, or using a type-safe API from "Q" classes that are generated as a side-effect of the enhancement process.  You can learn more about type-safe API in [DataNucleus' documentation](http://www.datanucleus.org/products/datanucleus/jdo/jdoql_typesafe.html).

The todoapp demonstrates both approaches.  The `ToDoItems` domain service delegetes to an injected `ToDoItemRepository` to actually query for objects; and we have two implementations to show the alternative approaches:

![](https://raw.github.com/isisaddons/isis-app-todoapp/master/images/ToDoItemRepository.png)

The diagram was generated by [yuml.me](http://yuml.me); see appendix at end of page for the DSL.

For example, the JDOQL to search for a `ToDoItem` by its `atPath` and `complete` properties requires this JDOQL definition:

    @javax.jdo.annotations.Query(
            name = "findByAtPathAndComplete", language = "JDOQL",
            value = "SELECT "
                    + "FROM todoapp.dom.module.todoitem.ToDoItem "
                    + "WHERE atPath.indexOf(:atPath) == 0 "
                    + "   && complete == :complete"),

along with this implementation:

    public class ToDoItemRepositoryUsingTypesafeQueries extends ToDoItemRepository {
        @Override
        protected List<ToDoItem> doFindByAtPathAndComplete(final String atPath, final boolean complete) {
            return container.allMatches(
                    new QueryDefault<>(ToDoItem.class,
                            "findByAtPathAndComplete",
                            "atPath", atPath,
                            "complete", complete));
        }
        ...
    }

Using the type-safe query approach on the other hand requires no JDOQL, but uses the generated `QToDoItem` class:

    public class ToDoItemRepositoryUsingTypesafeQueries extends ToDoItemRepository {
    
        @Override
        protected List<ToDoItem> doFindByAtPathAndComplete(
                final String atPath,
                final boolean complete) {
            final QToDoItem candidate = QToDoItem.candidate();
            return newQuery().filter(
                    candidate.atPath.eq(atPath).and(
                    candidate.complete.eq(complete))).executeList();
        }
        ...
    }

You can try out either implementation by commenting in/out the `@DomainService` for these two implementations.  (Isis does not - yet - provide a way to switch dynamically implementations). 
    
Type-safe queries have several advantages over JDOQL: they're type-safe (obviously!) and involve less code overall.  But there are some downsides too.  Arguably they are a little harder to understand than the free-form JDOQL string).  Perhaps more significantly, they complicate the project somewhat: the repository implementation must reside in the "down-stream" `domrepo` module, because of the dependency on the `QToDoItem` class that is only generated in the "process-classes" phase of the Maven lifecycle; ie after the code has been compiled.  


## Usage of AssertJ

The example makes some limited use of [AssertJ](http://joel-costigliola.github.io/assertj/) assertions.
Joel (author/maintainer of AssertJ) was good enough to provide a [pull request](https://github.com/isisaddons/isis-app-todoapp/pull/1) demoing more extensive use of AssertJ, in particular its support for domain-specific assertions.

As I want this repo to primarily be a demonstration of Apache Isis rather than AssertJ, I've chosen to
his PR but leave it in an unmerged branch.  If you want to explore this feature of AssertJ, check out the [issue-1-assertj-domain-specific-usage](https://github.com/isisaddons/isis-app-todoapp/tree/issue-1-assertj-domain-specific-usage) branch.

## Building the App

Switch into the root directory of the app, and simply use:

    mvn clean install

## Running the App

The `todoapp` app generates a single WAR file, configured to run both the [Wicket viewer](../../components/viewers/wicket/about.html) and the [Restful Objects viewer](../../components/viewers/wicket/about.html).  The app also configures the [JDO Objectstore](../../components/objectstores/jdo/about.html) to use an in-memory HSQLDB connection.

Once you've built the app, you can run the WAR in a variety of ways.

The recommended approach when getting started is to run the self-hosting version of the WAR, allowing Isis to run as a standalone app; for example:

    java -jar webapp/target/todoapp-webapp-1.0-SNAPSHOT-jetty-console.jar

This can also be accomplished using an embedded Ant target provided in the build script:

    mvn -P self-host antrun:run

The first is to simply deploying the generated WAR (`webapp/target/todoapp-webapp-1.0-SNAPSHOT.war`) to a servlet container.

Alternatively, you could run the WAR in a Maven-hosted Jetty instance, though you need to `cd` into the `webapp` module:

    cd webapp
    mvn jetty:run -D jetty.port=9090

In the above, we've passed in a property to indicate a different port from the default port (8080).

Note that if you use `mvn jetty:run`, then the context path changes; check the console output (eg [http://localhost:9090/todoapp-webapp](http://localhost:9090/todoapp-webapp)).

Finally, you can also run the app by deploying to a standalone servlet container such as [Tomcat](http://tomcat.apache.org).

## Using the App

The app provides a welcome page that explains the classes and files generated, and provides detailed guidance and what to do next.

The app itself is configured to run using shiro security, as configured in the `WEB-INF/shiro.ini` config file.  To log in, use `todoapp-admin/pass`.


## App Structure

As noted above, the generated app is a reasonably complete application for tracking to-do items.  It consists of the following modules:

<table class="table table-striped table-bordered table-condensed">
<tr><th>Module</th><th>Description</th></tr>
<tr><td>todoapp</td><td>The parent (aggregator) module</td></tr>
<tr><td>todoapp-dom</td><td>The domain object model, consisting of <tt>ToDoItem</tt> and <tt>ToDoItems</tt> domain service.  Also defines the (abstract) <tt>ToDoItemRepository</tt> repository class.</td></tr>
<tr><td>todoapp-domrepo</td><td>Implementations of the repository class (defined in the <tt>-dom</tt> module, demonstrating queries using either JDOQL or DataNucleus' type-safe queries as a means of querying the database.</td></tr>
<tr><td>todoapp-fixture</td><td>Domain object fixtures used for initializing the system when being demo'ed or for unit testing.</td></tr>
<tr><td>todoapp-integtests</td><td>End-to-end integration tests that exercise from the UI through to the database</td></tr>
<tr><td>todoapp-webapp</td><td>Run as a webapp (from <tt>web.xml</tt>) using either the Wicket viewer or the RestfulObjects viewer</td></tr>
</table>


## Learning More

The Apache Isis [website](http://isis.apache.org) has lots of useful information and is being continually updated.

Or, you can just start coding using the [Maven archetype](http://isis.apache.org/intro/getting-started/simple-archetype.html).

And if you need help or support, join the [mailing lists](http://isis.apache.org/support.html).


## Appendix: yuml.me DSL

[edit](http://yuml.me/edit/852cb3b1):
<pre>
[dom.ToDoItems]->[dom.ToDoItemRepository]
[dom.ToDoItemRepository]^-[domrepo.ToDoItemRepositoryUsingJdoQl]
[dom.ToDoItemRepository]^-[domrepo.ToDoItemRepositoryUsingTypesafeQueries]
</pre>
