# Use gradle to build project
FROM gradle:6.7-jdk11 as builder
# Add directories for build
WORKDIR /app
COPY build.gradle .
COPY src ./src
#Build app
RUN gradle build

FROM openjdk:11
# set work dir for the real container
WORKDIR /opt/app
# Copy jar file from builder
COPY --from=builder /app/build/libs/app-*.jar ./app.jar
# run jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]





