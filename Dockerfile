FROM openjdk:8
EXPOSE 8080
ADD target/pms.war pms.war
ENTRYPOINT ["sh", "-c", "java -jar /pms.war"]