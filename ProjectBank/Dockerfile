FROM openjdk:11.0-oracle
COPY "./target/ProjectBank-0.0.1-SNAPSHOT.jar" "mscardapp.jar"
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "mscardapp.jar"]