# CI

This is the repository for assignment #2 CI in the course DD2480, Software Engineering Fundamentals, created by group 9. The goal of the assignment is to create a CI program that clone build and test a java application.

## The history log server

In order to start the history log server you have to install Netbeans IDE

* [NETBEANS] (https://netbeans.org/downloads/8.2/rc/)

Import the project logserv and download/select a server from the available servers in Netbeans to run the servlets,
after installing all the Dependencies (see list below), you can run the application with the (Run play button)
visit: http://localhost:XXXX/logserv/Build to view all logs in the database or find a build by id with http://localhost:29565/logserv/Build?id=your_build_id

### Dependencies

Dependencies for the project

* [Servlet](https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api/3.0.1) 3.0.1

* [SQLite](https://jar-download.com/artifacts/org.xerial/sqlite-jdbc/3.14.2/source-code) 3.14.2

* [Gson](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.6/) 2.8.6

The Servlet Dependency should be named "servlet" and the SQLite"sqllite" also make sure that you copy the libraries files to the project directory

### Execution

# 1.
    Start the CI server by running `src/logserv/CIServer.java`
    The server will be run at `localhost:8888` 
    
