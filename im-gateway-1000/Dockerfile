FROM openjdk:17
EXPOSE 1000
#MAINTAINER donkor
LABEL authors="donkor"

VOLUME /mydata
COPY /im-gateway-1000.jar /gateway.jar

ENTRYPOINT ["java", "-jar","-Xms128m","-Xmx256m","gateway.jar"]