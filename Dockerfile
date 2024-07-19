FROM eclipse-temurin:21-jdk AS build
WORKDIR /home/gradle/src
COPY . /home/gradle/src
RUN ./gradlew :application:bootJar --no-daemon

FROM eclipse-temurin:21-jre
COPY --from=build /home/gradle/src/application/build/libs/application.jar /application.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/application.jar"]