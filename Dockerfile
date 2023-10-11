# Use the official Tomcat 10 image as the base image
FROM tomcat:10

# Remove the default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy your application WAR file into the Tomcat webapps directory
COPY ./target/ProgrammingProject_Chatterbox-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port on which Tomcat will run (default is 8080)
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]