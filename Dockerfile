FROM java:8-jre
LABEL author="yiuman"

COPY target/store-management-1.0-SNAPSHOT.jar /store-management.jar
COPY src/main/resources/application-docker.yml /config/application.yml

ENV MYSQL_URL jdbc:mysql://host.docker.internal:3306/citrus?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8
ENV MYSQL_USERNAME root
ENV MYSQL_PASSWORD yiuman
ENV PARAMS ""

EXPOSE 80

ENTRYPOINT java -jar /store-management.jar $PARAMS