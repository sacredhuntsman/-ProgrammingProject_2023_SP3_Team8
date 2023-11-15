# -ProgrammingProject_2023_SP3_Team8
This is the repository for the 2023 SP3 Programming Project for Team 8.

## Getting Started
To get started, clone this repository to your local machine.

https://github.com/s3523025/-ProgrammingProject_2023_SP3_Team8.git



### Prerequisites
The following software is required to run this repository:
* [Java] (https://www.java.com/en/download/) (version 8 or newer)
* [Apache Maven] (https://maven.apache.org/download.cgi) (version 3.6.3 or newer)
* optional [Apache Tomcat] (https://tomcat.apache.org/download-10.cgi) (version 10 or newer)


### Installing
Once you have cloned this repository, navigate to the directory and run the following command:
```
mvn install
```
This will install all the required dependencies to run the project and create the .war file

## Running the Project locally

To run the project locally, copy the .war file to the webapps directory of your preferred local server and run the server.
Navigate to the following URL in your browser:
http://localhost:8080/ProgrammingProject_Chatterbox_war_exploded

This will run the main file for the project and load the login page on your local server.

If using the included dockerfile, you can run the following commands to launch the server locally.

```
docker build -t my-tomcat-app:1.0 .
```
followed by
```
docker run -p 8080:8080 my-tomcat-app:1.0
```
The application will then be available at http://localhost:8080/

## Running the Project live from Azure webapps

Navigate to the following URL in your browser:
https://chatter-box.azurewebsites.net

## Using the app
Create an account and login to the application. 
Once logged in, you will be able to create a new group chat or join an existing group chat.  

## Authors 
ProgrammingProject_2023_SP3_Team8
* [Alexander Cowan]
* [Avrohom Rosenfeld]
* [Brendan Libbis]
* [Jack Burgess]
* [Michael Moon]
* [Tyler Stosic]

