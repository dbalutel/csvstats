## CLI Application to read CSV file

Application reads provided CSV document and calculates:
- Average income per Canton
- Person with max income in each Canton
- Person with the lowest income in each Canton

---
### Csv structure example:

`PersonId,Canton,Income  `  

`001,ZH,70000`

---
### Run with
- [**JDK 11**]  
`./mvnw package  `  
 `java -jar target/csvstats-0.1.jar -f src/test/resources/mockdata.csv`
- [**GRAALVM**] _Runs as native executable_  
Install graal with https://sdkman.io  
`sdk install java 21.2.0.r11-grl`  
`gu install native-image`  
`./mvnw package -Dpackaging=docker-native`  
`./target/csvstats -f src/test/resources/mockdata.csv` 