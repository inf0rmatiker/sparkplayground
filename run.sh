#!/bin/bash

./gradlew clean && ./gradlew install -x test && ./build/install/sparkplayground/bin/launch