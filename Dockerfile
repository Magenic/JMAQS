FROM maven:3.8.4-openjdk-11-slim
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
RUN mkdir -p /root/framework
WORKDIR /root/framework
COPY . .
RUN mvn -B -e -T 1C -Dtesting install -Dmaven.test.skip=true
RUN mvn clean -B -e -T 1C
RUN wget -O - $(wget -O - https://api.github.com/repos/powerman/dockerize/releases/latest | grep -i /dockerize-$(uname -s)-$(uname -m)\" | cut -d\" -f4) | install /dev/stdin /usr/local/bin/dockerize
#CMD dockerize -wait tcp://mssql:1433 -wait http://chrome:4444 -timeout 360s -- echo 'Services running'
#ENTRYPOINT dockerize -wait tcp://mssql:1433 -wait http://chrome:4444 -timeout 360s -- echo 'Services running' && mvn verify package --file pom.xml -e -fae -T 1C -B -Dtesting  -Djdk.version=11