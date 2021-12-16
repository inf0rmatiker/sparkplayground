# Spark Playground

Project for running experiments with Spark in Java

## Usage

### Linux

- `./gradlew clean && ./gradlew install -x test`
- `./build/install/sparkplayground/bin/launch`

### Docker
- `docker build -t sparkplayground .`
- `docker run -i --name=sparkplayground sparkplayground`