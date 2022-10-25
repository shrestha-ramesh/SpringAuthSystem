#FROM gradle:latest
#RUN mkdir user
#WORKDIR /user
#ADD . .
#EXPOSE 8080
#CMD ["cd", "/user"]
#CMD ["./gradlew", "build"]
#CMD ["./gradlew", "bootRun"]

FROM openjdk:11
EXPOSE 8080
ARG JAR_File=build/libs/*jar
COPY ${JAR_File} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]