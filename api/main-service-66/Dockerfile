FROM openjdk:17
EXPOSE 66
#MAINTAINER donkor
LABEL authors="donkor"

VOLUME /mydata
COPY /main-service-66.jar /main-service.jar

ENTRYPOINT ["java", "-jar","-Xms128m","-Xmx256m","main-service.jar"]