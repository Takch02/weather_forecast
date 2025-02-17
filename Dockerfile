# 1단계: Gradle 빌드 단계
FROM gradle:8-jdk17 AS build

# 작업 디렉토리 설정 (Gradle 공식 이미지에서는 기본 사용자가 gradle입니다)
WORKDIR /app

# 전체 프로젝트 파일 복사
COPY . .

# Gradle Wrapper가 포함되어 있다면 Wrapper를 사용하여 빌드합니다.
# (Wrapper가 없을 경우, gradle 명령어가 바로 실행됩니다.)
RUN ./gradlew bootJar

# 시간대 설정
RUN apt-get update && apt-get install -y tzdata

# 시간대 설정 (Asia/Seoul)
ENV TZ=Asia/Seoul

# 다른 명령어들...


# 2단계: 실행 단계
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/weatherTest001*.jar app.jar

# 컨테이너에서 8080 포트 사용
EXPOSE 8080

# 컨테이너 시작 시 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
