# 1단계: Gradle 빌드 단계
FROM gradle:8-jdk17 AS build

WORKDIR /app

COPY . .
RUN chmod +x ./gradlew

RUN ./gradlew bootJar

# 2단계: 실행 단계
FROM openjdk:17-jdk-slim

WORKDIR /app

# ✅ 실행 단계에서 시간대 설정을 위해 tzdata 설치
RUN apt-get update && apt-get install -y tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone \
    && rm -rf /var/lib/apt/lists/*

# 빌드 단계에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/weatherTest001*.jar app.jar

# 컨테이너에서 8080 포트 사용
EXPOSE 8080

# 컨테이너 시작 시 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
