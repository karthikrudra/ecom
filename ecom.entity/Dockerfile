FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/ecom-entity.jar ecom-entity.jar

ENTRYPOINT ["java","-jar","ecom-entity.jar"]