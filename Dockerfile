FROM gradle:latest
RUN mkdir user
WORKDIR /user
ADD . .
EXPOSE 8080
CMD ["cd", "/user"]
CMD ["./gradlew", "build"]
CMD ["./gradlew", "bootRun"]
