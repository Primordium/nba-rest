# Nba-rest

Exposes a rest endpoints for queries in https://rapidapi.com/theapiguy/api/free-nba/endpoints and a mysql or h2 database running in parallelly

## Exposed Endpoints

   - root: api/nbadb
   - GET  "/" -> Intructions
   - GET  "/date/{date}" with yyyy-MM-dd format searches for games the date provided;
   - GET  "/game/{gameId}" searches for games with id provided;
   - POST "comments/{gameId}" Creates a new comment for the game with the id provided;
   - PUT  "comments/{commentId}" Edits the comment with the provided comment id;
   - DELETE "comments/{commentId}" Deletes a comment with the provided comment id;

## How to install

In serc/main/resources/application.properties
Choose the profile to be used on execution
dev - will use h2 database, it's a memory sql database commonly used for testing and developing 
prod - to use a mysql database, bellow there are instructions on how to run the program against a mysql db on docker

### dev profile
After running the program database can be accessed on http://localhost:8080/h2-console/
Default user : sa 
Password : {empty}
can be changed in application-dev.profile


### prod profile
Prerequisites:
Docker

1. Clone the repository
2. Run the following commands in bash/terminal

    ```sh
    $ cd nba-rest
    $ docker run --name nba-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=nbadb -p 3306:3306 -d mysql:5.7
    $ docker build -t nbadb .
    $ docker run --name nba-rest -p 8080:8080 -p 8089:8089 -it --link nba-mysql nbadb
    ```


## Usage

With [Postman](https://www.postman.com/) or curl

examples
```sh
$ curl http://localhost:8080/api/nbadb/
$ curl --request POST "localhost:8080/api/nbadb/comments/42122" --header 'text/plain;charset=UTF-8' --data '"comment"'
```
