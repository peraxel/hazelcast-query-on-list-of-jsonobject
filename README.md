# Intro

Starting from version 3.12, Hazelcast supports running queries on maps containing JSON Objects. This is an important feature due to the fact that queries on dynamic payloads hasn't been supported at all prior to this - we could only run queries on POJOs.

This tiny Java EE 8 application running on [Payara Micro](https://github.com/payara/Payara) (version >= 5.192) gives an idea of how this new Hazelcast feature can be used.

# Build
mvn clean package 

# Run
java -jar $PAYARA_MICRO_JAR --contextroot / --deploymentdir target --port 8090 --clustername hqlj 

# Try

1. curl "localhost:8090/things" -X POST -H "Content-Type: application/json" -d '[{"name":"Robert"},{"name":"Allan"}]'
2. curl "localhost:8090/things?query=name%3DAllan"