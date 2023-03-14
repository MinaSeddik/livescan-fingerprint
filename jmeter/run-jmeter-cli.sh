#!/bin/bash

echo "Running JMeter JMX script from the Command line (No GUI)"

# jmeter -n -t [jmx file] -l [results file] -e -o [Path to web report folder]
#-n : No GUI
#-t : JMX file
#-l : result data (prefer CSV format)
#-o : Generating HTML Report - output folder for HTML Report

#JVM_ARGS to set the min and max heap memory

rm -rf /home/mina/Desktop/jmeter_reports/*
rm /home/mina/Desktop/result.csv

JVM_ARGS="-Xms8096m -Xmx8096m -server" /home/mina/Desktop/apache-jmeter-5.5/bin/jmeter.sh -n -t /home/mina/Desktop/livescan-fingerprint/jmeter/MyFingerprintAppGraphResults.jmx -l /home/mina/Desktop/result.csv -e -o /home/mina/Desktop/jmeter_reports

