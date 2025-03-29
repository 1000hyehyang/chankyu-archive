# Step 1: Maven을 사용하여 빌드
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

# 의존성 먼저 복사 (빌드 캐싱 최적화)
COPY pom.xml .
RUN mvn dependency:go-offline

# 소스코드 복사 및 빌드
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: 실행 환경
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 빌드된 jar 파일 복사
COPY --from=builder /app/target/*.jar app.jar

# 환경변수 설정 (Render는 포트를 동적으로 설정함)
ENV PORT=8080

# Render는 환경변수로 포트를 지정하기 때문에 포트 설정 필수!
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "/app/app.jar"]