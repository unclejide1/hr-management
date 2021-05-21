FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
RUN addgroup -S piuser && adduser -S piuser -G piuser
USER piuser

ENV JAVA_OPTS=""
COPY ./target/hr-management-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /hr-management-0.0.1-SNAPSHOT.jar"]