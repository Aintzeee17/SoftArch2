FROM tomcat:9.0-jdk11-openjdk-slim

# Bersihkan default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file dari NetBeans build
COPY dist/SoftArch.war /usr/local/tomcat/webapps/SoftArch.war

# Expose port
EXPOSE 8080

# Run Tomcat
CMD ["catalina.sh", "run"]