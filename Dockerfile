
# 1. Gunakan server Tomcat dengan Java 11
FROM tomcat:9.0-jdk11-openjdk-slim

# 2. Padam aplikasi asal Tomcat untuk elakkan konflik
RUN rm -rf /usr/local/tomcat/webapps/*

# 3. Salin folder hasil build dari NetBeans ke dalam ROOT Tomcat
COPY build/web/ /usr/local/tomcat/webapps/ROOT/

# 4. Dedahkan port 8080
EXPOSE 8080

# 5. Jalankan server Tomcat
CMD ["catalina.sh", "run"]