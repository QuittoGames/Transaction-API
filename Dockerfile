# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw \
	&& sed -i 's/\r$//' mvnw \
	&& ./mvnw -q -DskipTests dependency:go-offline

COPY src/ src/

RUN ./mvnw -DskipTests package \
	&& JAR_FILE="$(find target -maxdepth 1 -type f -name '*.jar' ! -name '*.original' -print -quit)" \
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

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
