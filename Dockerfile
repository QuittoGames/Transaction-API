# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw \
	&& ./mvnw -q -DskipTests dependency:go-offline

COPY src/ src/

RUN ./mvnw -DskipTests package \
	&& JAR_FILE="$(ls -1 target/*.jar | grep -v '\.original$' | head -n 1)" \
	&& test -n "$JAR_FILE" \
	&& cp "$JAR_FILE" /workspace/app.jar


FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

RUN addgroup --system app \
	&& adduser --system --ingroup app app

COPY --from=build /workspace/app.jar ./app.jar

EXPOSE 8080

USER app

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
