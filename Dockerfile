FROM openjdk:15-jdk-alpine
LABEL description="its a sample of servr for Forward Auth middleware"
RUN addgroup -S otusdemo && adduser -S otusspring -G otusdemo
USER otusspring:otusdemo
WORKDIR /usr/local/myapp
EXPOSE 8000
ARG JAR_FILE=build/libs/jwt-middleware-1.0-JWTMiddleware.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/usr/local/myapp/app.jar", "--server.port=8000"]