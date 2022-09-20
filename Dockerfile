FROM openjdk:11
RUN addgroup --system spring 
RUN adduser --system spring --gecos spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
WORKDIR /user/local/bin/app
ADD ./src/main/resources ./src/main/resources/
ENTRYPOINT ["java","-jar","/app.jar"]