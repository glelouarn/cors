cors: Demonstrate how to manage CORS headers.
========================
Author: Gildas Le Louarn

What is it?
-----------

A demonstrator application used to show CORS headers for a REST service (manage GET, OPTIONS and headers).

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on JBoss Enterprise Application Platform 6 or JBoss AS 7. 

Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#configure-maven) before testing the project.

Start JBoss Enterprise Application Platform 6 or JBoss AS 7 with the Web Profile
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat
 
Build and Deploy the project
-------------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this project.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/cors.war` to the running instance of the server.

Access the application 
---------------------

The application will be running at the following URL: <http://localhost:8080/cors/>.

Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this project.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy
        
Run the Arquillian Tests 
-------------------------

This project provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container. 

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this project.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Parq-jbossas-remote 

Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc
