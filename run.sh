#!/bin/sh

rm -rf prod-logs/*

java -jar build/libs/FixSimulator-0.1.jar fixsimulator_prod.cfg

