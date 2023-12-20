FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# convert the mvnw line endings from win to unix
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./mvnw

RUN ./mvnw dependency:resolve

COPY src ./src

EXPOSE 5432

CMD ["./mvnw", "spring-boot:run"]