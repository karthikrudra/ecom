FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/e_commerce_user.jar e_commerce_user.jar

ENTRYPOINT ["java","-jar","e_commerce_user.jar"]