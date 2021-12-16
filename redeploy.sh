#!/bin/bash

git fetch && git pull && docker build -t sustain/sparkplayground . && docker push sustain/sparkplayground && kubectl apply -f deploy/job.yaml