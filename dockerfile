FROM openjdk:21
WORKDIR /opt
COPY target/*.jar  /opt/app.jar
CMD ["java","-jar","app.jar"]

