#!/bin/bash

PATH_TO_FX=/tmp/javafx-sdk-17.0.7/lib
java --module-path jars/Client-1.0-SNAPSHOT.jar:$PATH_TO_FX --module com.client/com.client.FirstWindowApplication