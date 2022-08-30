FROM openjdk:latest
#WORKDIR /app
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
#RUN ./mvnw package
EXPOSE 8083:8083
COPY target/*.jar app.jar
#FROM openjdk:17
#COPY --from=buildstage /app/app.jar .
ENTRYPOINT ["java","-jar","app.jar"]
CMD ["catalina.sh","run"]