#!/bin/bash

git fetch \
  && git pull \
  && docker build -t sustain/sparkplayground . \
  && docker push sustain/sparkplayground \
  && kubectl delete -f deploy/job.yaml

kubectl apply -f deploy/job.yaml

if [[ $? -eq 0 ]]; then
  while [[ $(kubectl get pod/sparkplayground | grep "ContainerCreating") ]]; do
    sleep 1
  done

  kubectl get pod/sparkplayground | grep "Running" && kubectl logs -f pod/sparkplayground
fi
