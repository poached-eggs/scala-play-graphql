# ==================================
# STAGE 1 - build a fat jar
# ==================================
FROM openjdk:11

ENV SBT_VERSION 1.6.2

RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.zip
RUN unzip sbt-$SBT_VERSION.zip -d ops

WORKDIR /app

# Copy all project libraries and build requirements
COPY build.sbt /app/build.sbt
COPY project/plugins.sbt /app/project/plugins.sbt
COPY project/build.properties /app/project/build.properties

# Copy the project
COPY app /app/app
COPY conf /app/conf
COPY test /app/test
COPY public /app/public

# Generate a fat jar
RUN /ops/sbt/bin/sbt package assembly

# ==================================
# STAGE 2 - build a slim jar
# ==================================
FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/scala-play-graphql
WORKDIR /opt/scala-play-graphql
COPY --from=0 /app/target/scala-2.13/scala-play-graphql-assembly-*.jar scala-play-graphql.jar
CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -jar scala-play-graphql.jar