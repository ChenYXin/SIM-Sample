FROM openjdk:17
EXPOSE 88
#MAINTAINER donkor
LABEL authors="donkor"

VOLUME /mydata
COPY /auth-gateway-88.jar /auth-gateway.jar

ENTRYPOINT ["java", "-jar","-Xms128m","-Xmx256m","auth-gateway.jar"]