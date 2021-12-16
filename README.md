# Spark Playground

Project for running experiments with Spark in Java

## Usage

### Linux

- `./gradlew clean && ./gradlew install -x test`
- `./run.sh`

Although this project currently assumes it is running within a Kubernetes Pod

### Docker
- `docker build -t sparkplayground .`
- `docker run -i --name=sparkplayground sparkplayground`

### Kubernetes

`./redeploy.sh` or `kubectl apply -f deploy/job.yaml`