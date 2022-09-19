FROM openjdk:11
RUN addgroup --system spring 
RUN adduser --system spring --gecos spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY ./data/dictionary.txt ./data/dictionary.txt
ENTRYPOINT ["java","-jar","/app.jar"]