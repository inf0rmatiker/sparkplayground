#!/bin/bash

git fetch && git pull && docker build -t sustain/sparkplayground . && docker push sustain/sparkplayground && kubectl delete -f deploy/job.yaml && kubectl apply -f deploy/job.yaml