FROM grubykarol/locust:0.13.5-python3.8-alpine3.11

# Install OpenJDK-8
RUN apk update
RUN apk add --no-cache openjdk9

# Setup JAVA_HOME -- useful for docker commandline

ENV JAVA_HOME /usr/lib/jvm/java-9-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-9-openjdk/jre/bin:/usr/lib/jvm/java-9-openjdk/bin

# Install Maven
RUN apk add maven

# Needed for the report
RUN apk --update add fontconfig ttf-dejavu

# Copy the files from the machine
RUN mkdir locustapitest
COPY / /locustapitest
WORKDIR /locustapitest


# Mvn Install the project
RUN mvn install

## Zipping the Report for better copying
RUN apk add zip unzip
RUN zip -r report.zip ./target/cucumber-reports/


ENTRYPOINT java -jar ./target/locustapitest-0.0.1-SNAPSHOT.jar