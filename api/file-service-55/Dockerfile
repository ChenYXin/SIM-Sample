FROM openjdk:17
EXPOSE 55
#MAINTAINER donkor
LABEL authors="donkor"

VOLUME /mydata
COPY /file-service-55.jar /file-service.jar

ENTRYPOINT ["java", "-jar","-Xms128m","-Xmx256m","file-service.jar"]