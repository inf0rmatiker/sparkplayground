# ------------------------------------------------------------------------------------*/
# Dockerfile -
#
# Description: Provides instructions for building sustain-query-service Docker image.
#
# Author: Caleb Carlson
# ------------------------------------------------------------------------------------*/

FROM openjdk:11 AS base

# --- Project ---

# Add in source code
ENV PROJECT="sparkplayground"
RUN mkdir -p /code/$PROJECT
WORKDIR /code/$PROJECT

COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY src/ ./src
COPY gradle/ ./gradle
COPY run.sh ./run.sh

RUN ./gradlew clean && ./gradlew install -x test

# Build and run project
ENTRYPOINT ["./run.sh"]