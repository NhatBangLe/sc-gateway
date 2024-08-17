FROM eclipse-temurin:21-jdk-jammy AS build
COPY . /sc-gateway
WORKDIR /sc-gateway
RUN ./mvnw clean install -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-jammy
RUN addgroup --system spring && adduser --system spring && adduser spring spring
USER spring:spring
COPY --from=build /sc-gateway/target/sc-gateway.jar /sc-gateway/app.jar
WORKDIR /sc-gateway
CMD ["java", "-jar", "app.jar"]