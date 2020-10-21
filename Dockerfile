From openjdk:12
copy ./target/nba-challenge-0.0.1-SNAPSHOT.jar nba-challenge.jar
EXPOSE 8089
CMD ["java","-jar","nba-challenge.jar"]