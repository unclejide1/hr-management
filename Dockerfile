FROM hr/hr-builder:latest AS MAVEN_BUILD

COPY pom.xml /build/

WORKDIR /build/

COPY src /build/src/

ENV JAVA_OPTS=""

ENTRYPOINT mvn spring-boot:run -Dspring-boot.run.profiles=dev

#USER piuser
#
#ENV JAVA_OPTS=""
#COPY ./target/hr-management-0.0.1-SNAPSHOT.jar .
#
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /hr-management-0.0.1-SNAPSHOT.jar"]