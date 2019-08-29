# Build
mvn clean package 

# Run
java -jar $PAYARA_MICRO_JAR --contextroot / --deploymentdir target --port 8090 --clustername hqlj 

# Try

1. curl "localhost:8090/things" -X POST -H "Content-Type: application/json" -d '[{"name":"Robert"},{"name":"Allan"}]'
2. curl "localhost:8090/things?query=name%3DAllan"