FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/e_commerce_seller.jar e_commerce_seller.jar

ENTRYPOINT ["java","-jar","e_commerce_seller.jar"]