# isis-app-todoapp #

[![Build Status](https://travis-ci.org/isisaddons/isis-app-todoapp.png?branch=master)](https://travis-ci.org/isisaddons/isis-app-todoapp)

The TodoApp example is a reasonably complete application for tracking to-do items, based around a single domain class `ToDoItem` and repository, `ToDoItems`.

While not quite a "kitchen-sink" example (there is, after all, just a single domain class), the app nevertheless demonstrates a good number of Isis' capabilities.  In particular, the use of contributed actions etc is demonstrated by `ToDoItemContributions`; view models are demonstrated by `ToDoItemsByCategoryViewModel` and `ToDoItemsByDateRangeViewModel`; a dashboard is demonstrated by `ToDoAppDashboard`.

The example also integrates with Isisaddons for [security](https://github.com/isisaddons/isis-module-security), [command profiling](https://github.com/isisaddons/isis-module-command), [auditing](https://github.com/isisaddons/isis-module-audit), [event publishing](https://github.com/isisaddons/isis-module-publishing).

Running this app is a good way to get familiar with the structure of a not-too-complex Isis application.  However, to get started with your own application, we generally recommend that you use the [simple archetype](http://isis.apache.org/intro/getting-started/simpleapp-archetype.html).  This will generate a completely stripped back and minimal application for you to refactor and extend; you can then use this example todoapp to guide your own development.


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

The app itself is configured to run using shiro security, as configured in the `WEB-INF/shiro.ini` config file.  To log in, use `sven/pass`.

## Modifying the App

Once you are familiar with the generated app, you'll want to start modifying it.  There is plenty of guidance on this site; check out the 'programming model how-tos' section on the main [documentation](../../documentation.html) page first).

If you use Eclipse, do also install the [Eclipse templates](../resources/editor-templates.html); these will help you follow the Isis naming conventions.

## App Structure

As noted above, the generated app is a reasonably complete application for tracking to-do items.  It consists of the following modules:

<table class="table table-striped table-bordered table-condensed">
<tr><th>Module</th><th>Description</th></tr>
<tr><td>todoapp</td><td>The parent (aggregator) module</td></tr>
<tr><td>todoapp-dom</td><td>The domain object model, consisting of <tt>ToDoItem</tt> and <tt>ToDoItems</tt> (repository) domain service.</td></tr>
<tr><td>todoapp-fixture</td><td>Domain object fixtures used for initializing the system when being demo'ed or for unit testing.</td></tr>
<tr><td>todoapp-integtests</td><td>End-to-end <a href="../../core/integtestsupport.html">integration tests</a>, that exercise from the UI through to the database</td></tr>
<tr><td>todoapp-webapp</td><td>Run as a webapp (from <tt>web.xml</tt>) using either the Wicket viewer or the RestfulObjects viewer</td></tr>
</table>
