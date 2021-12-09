FROM openjdk:11-jdk-slim
# ----
# Install Maven
RUN apt-get update && apt-get install maven -y
ARG MAVEN_VERSION=3.8.4
ARG USER_HOME_DIR="/root"
#RUN mkdir -p /usr/share/maven && \
#curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
#ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
ENTRYPOINT ["/usr/bin/mvn"]
# ----
# Install project dependencies and keep sources
# make source folder
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . .
# install maven dependency packages (keep in image)
#COPY pom.xml /usr/src/app
#COPY  ./jmaqs-*/ /usr/src/app/
RUN mvn -B -e -T 1C install -Dmaven.test.skip=true
RUN mvn clean -B -e -T 1C
# copy other source files (keep in image)
#COPY  ./jmaqs-*/ /usr/src/app/