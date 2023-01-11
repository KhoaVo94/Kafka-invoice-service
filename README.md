# kafka-invoice-service
An simple service based on event streaming using Kafka with schema registry

# Feafures
- Publish and subscribe "OrderPaid" event using Kafka pub/sub queue.
- Store and get invoice record in Database (using MySQL).
- Write simple invoice pdf when invoice issued for "OrderPaid" event.
- Use Schema registry with Google Proto buf for making event template.
- Use MyBatis with CQRS pattern (command and query seperation) for querying and update data in Database.
- Use Flyway to ru DB migration

# Instruction
### 1. Setup MySql DB server
You can start your own MySql DB server (container in Docker Hub, cloud/server domain, install local,...), and version should be equals or larger than `8.0.0`
There are properties in source code that need to be modified as you need (`application.yml` and `application-database.yml`):
- `spring.flyway.url`, `spring.flyway.user`, `spring.flyway.password`
- `spring.datasource.query.jdbc-url`, `spring.datasource.query.username`, `spring.datasource.query.password`
- `spring.datasource.register.jdbc-url`, `spring.datasource.register.username`, `spring.datasource.register.password`
### 2. Install Docker (if not have) and run docker containers for Kafka eco system
You will need to install Docker if your system doesn't installed, go to https://www.docker.com/ and download/install with accordingly OS and then start your Docker process in your system.
run a docker compose at `<path to project>/kafka-invoice-service/docker`: docker compose up
this will fire up 3 things:
- ZooKeeper for manage and do leader election between Kafka instances.
- Start up Kafka instances (in docker file, we have 3: kafka1-2-3, you can increase or remove some).
- Run Kafka-Schema registry that will help include proto file for event template.
Need to wait a minute to everything started up.
### 3. Clean build project
The important that building the project that we need to generate Java class for clarified proto file at Schema project: `paid_order.proto`
You can run command:
```./gradlew clean build```
### 4. Start producer and consumer service
There are 2 projects: `service`(test producer) and `consumer`(main service for invoice issue)
To start them up can run below commands:
- ```./gradlew :service:bootRun```
- ```./gradlew :consumer:bootRun```
### 5. Test "OrderPaid" event
Find the `commands.txt` file at `<path to project>/kafka-invoice-service/curl`
run cUrl command in that file in terminal and check below things when it finished:
- Check if there is and generated invoice file at root project, something line INV-3131a-....pdf
- Check in the database (configured at step 1) invoices, items and persons tables that have according data.

# Description
There are 3 projects: Consumer, Service and Schema
## Consumer
- Main project, used to make a stucture for Invoice issue service.
- Use DDD, CQRS and partly hexagonal architecture pattern to develop.
- All domain classes will be separated enough to hold the logic at itself.
- Repositories will have to type: Query and Command, that will use different DB connection for read and write operation.
- Datasource classes use for implementing repository with Mybatis mapper for SQL running and getting result.
## Service
- This just a testing service for providing a publish channel sending an "OrderPaid" event to Consumer.
- This is also configured for create Kafka event stream with schema template.
## Schema
- Used for defining a template of "OrderPaid" event by using Google proto buf.
- This can be used as a domain generation, since it will create Java class that can be shared accross project, but need to be considered carefully.

### Note: This project use `Java 17` for development.
