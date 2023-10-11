# Build Stage
FROM ubuntu:latest as BUILD

# Install utilities and download JDK
RUN apt -y update && apt -y upgrade && apt -y install wget
RUN wget https://download.java.net/java/GA/jdk21/fd2272bbf8e04c3dbaee13770090416c/35/GPL/openjdk-21_linux-x64_bin.tar.gz

# Extract JDK and set JAVA_HOME
RUN mkdir /jdk-21 && tar zxvf openjdk-21_linux-x64_bin.tar.gz -C /jdk-21 --strip-components=1
ENV JAVA_HOME=/jdk-21
ENV PATH="$JAVA_HOME/bin:${PATH}"

# Set up working directory
WORKDIR /app

# Copy source code and build configurations
COPY src /app/src
COPY pom.xml /app
COPY .mvn /app/.mvn
COPY mvnw /app/mvnw

# Build the application
RUN ./mvnw clean package -DskipTests || (echo "Maven build failed. Exiting." && exit 1)

# Runtime Stage
FROM ubuntu:latest as RUNTIME

# Copy compiled WAR file and JDK from build stage
COPY --from=BUILD /app/target/psych-clinic-0.0.1-SNAPSHOT.war /psych-clinic.war
COPY --from=BUILD /jdk-21 /jdk-21

# Set up environment variables
ENV JAVA_HOME=/jdk-21
ENV PATH="$JAVA_HOME/bin:${PATH}"

# Expose necessary port
EXPOSE 8080

# Run the application
ENTRYPOINT [ "java", "-jar", "./psych-clinic.war" ]