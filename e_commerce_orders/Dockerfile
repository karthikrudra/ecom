FROM openjdk:11-jdk-alpine
VOLUME /tmp
ADD target/e_commerce_orders.jar e_commerce_orders.jar

ENTRYPOINT ["java","-jar","e_commerce_orders.jar"]
