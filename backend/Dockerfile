FROM openjdk:21-jdk
WORKDIR /workspace/app/backend
COPY . .
RUN microdnf install findutils
RUN bash -c "if [ -f gradlew ]; then chmod +x gradlew; fi"
ENTRYPOINT ["./gradlew", "bootRun"]
