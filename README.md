# CI

This is the repository for assignment #2 CI in the course DD2480, Software Engineering Fundamentals, created by group 9. The goal of the assignment is to create a CI program that clone build and test a java application.

## Dependencies

Dependencies for the project

* [Servlet](https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api/3.0.1) 3.0.1

* [SQLite jdbc](https://jar-download.com/artifacts/org.xerial/sqlite-jdbc/3.14.2/source-code) 3.14.2

* [Gson](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.6/) 2.8.6

The Servlet Dependency should be named "servlet" and the SQLite"sqllite" also make sure that you copy the libraries files to the project directory.

## Execution

### 1.

In order to start the history log server you have to install Netbeans IDE

* [NETBEANS] (https://netbeans.org/downloads/8.2/rc/)

Import the project logserv and download a payara server to CI/Payara/ through netbeans.

After installing all the dependencies (see list below), you can run the application with the Run button.

It should print a url that you can use to access the version history of the CI server.

The following are 2 examples of urls:

View all logs in the database:

    http://localhost:8080/logserv/Build

Find a build by id:

    http://localhost:8080/logserv/Build?id=<your_build_id>


### 2.
Build and start the CI server by running the main function in

    src/logserv/buildtools.CIServer.java

The easiest way to do this is using intellij idea

The server will be run at `localhost:8888` 

## Documentation
[Github pages](https://rebwarbajallan95.github.io/CI/)
    
## List of contributions

Johan Besseling

* AutomatedTesting branch: Tests.java

* emailNotification branch: SendMail.java

* Documentation branch: Refactoring all documentation in to Java standard

* Assessment branch: isPrime.java, isPrimeTest.java

Rebwar Ali Omer Bajallan

* Parse the incomming webhooks: WebhookParser.java

* Clone the repo: CloneRepo.java

* Compile the java files in the repo: CompileFiles.java


Simon Binyamin

Branches Documentation, Webserver, main: 

* Initiated BuildServlet.java with its methods

* Created Build.java class that can be instantiated as a build object

* build.xml used to build the project

* Created builds.jsp that works as logserv frontend to display list of builds

* web.xml used to map the servlets

* Documented the BuildServlet.java, Build.java and builds.jsp

Lucas Kristiansson

* Handle creation, insertion, and fetching from db: DBUtils.java

* Some documentation and readme cleanup
