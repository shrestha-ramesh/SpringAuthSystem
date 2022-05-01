FROM gradle:latest
RUN mkdir user
WORKDIR /user
ADD . .
EXPOSE 8080
EXPOSE 3306
EXPOSE 587
RUN ./gradlew build
CMD ["cd", "/user"]
CMD ["./gradlew", "bootRun"]
