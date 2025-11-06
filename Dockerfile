
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y maven
RUN ./mvnw clean package -DskipTests
CMD ["sh", "-c", "java -jar target/$(ls target/*.jar | head -n 1)"]