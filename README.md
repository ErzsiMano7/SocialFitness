# How to
## Build

To build the project you'll need:
* JDK 8
* Maven 3.5

Navigate to the root directory and issue:

```
mvn clean install
```

## Start
To start the application first you'll have to create a database. For this example we'll use Docker to start our Postgre DB.

```
docker run -d -p 5432:5432 -e POSTGRES_USER=fituser -e POSTGRES_PASSWORD=fitpassword -e POSTGRES_DB=FitnessApp --name fitnessdb library/postgres
```

This will create and start a docker container named fitnessdb, anytime from now on when you want to use it, you can start it with:

```
docker start fitnessdb
```