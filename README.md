
# Food Ordering System

Reference implementation of a cloud-native microservices architecture using **Clean Architecture**, **Hexagonal Architecture**, and **Domain-Driven Design (DDD)**.

This project demonstrates how to structure a scalable and maintainable backend using modern architectural patterns and messaging-driven communication.

## Architecture Overview

The system follows a layered hexagonal approach:

* **order-domain-core** → pure business rules
* **order-application-service** → use cases and orchestration
* **order-application** → application layer
* **order-data-access** → persistence adapters
* **order-messaging** → messaging adapters (Kafka/events)
* **order-container** → composition root / Spring Boot bootstrap

This separation enforces:

* dependency inversion
* high testability
* clear bounded contexts
* infrastructure independence

## Tech Stack

* Java 24
* Spring Boot 2.6.7
* Maven multi-module
* Apache Kafka (event-driven communication)
* Clean & Hexagonal Architecture
* Domain-Driven Design (DDD)
* CQRS, SAGA, Outbox (in progress)

## Project Structure

```
food-ordering-system
└── order-service
    ├── order-domain
    │   ├── order-domain-core
    │   └── order-application-service
    ├── order-application
    ├── order-data-access
    ├── order-messaging
    └── order-container
    This project needs git config --global core.longpaths true since some paths may be >260 chars long
```

## Getting Started

```bash
mvn clean install
```

To run the application:

```bash
cd order-service/order-container
mvn spring-boot:run
```

## Infrastructure Configuration & Execution
This project relies on Docker Compose to run the local infrastructure (Zookeeper, Kafka Cluster, Schema Registry, and Kafka Manager).

Note for Windows Users: The commands below are identical and can be executed in both PowerShell and Git Bash.

### 1. Starting Zookeeper
Zookeeper acts as the centralized service for maintaining configuration information and naming for the Kafka brokers.
We must start it first.

``` bash
docker compose -f common.yml -f zookeeper.yml up -d
```

Understanding the flags used:

* ```docker compose``` : The command to run Docker Compose (in newer versions, it replaces ```docker-compose```).
* ```-f <filename>``` : Stands for "file". It tells Docker which configuration file to use. We pass it twice to merge
 our common.yml (which contains our shared network) with the ```zookeeper.yml``` file.
* ```up``` : The command to create and start the containers.
* ```-d``` : Stands for "detach" (background mode). It runs the containers in the background so your terminal doesn't get locked, allowing you to type new commands.

### 2. Verifying Zookeeper Health
Before starting Kafka, it is highly recommended to check if Zookeeper is fully operational.

First, list your running containers to get the Zookeeper container ID or name:
```docker ps -a ```

(The ```-a``` flag stands for "all", which lists all containers, even the stopped ones).
Then, execute the health check command using the container ID or name (e.g., docker-compose-zookeeper-1 or 74767a087db1):

```docker exec <container_id_or_name> sh -c "echo ruok | nc localhost 2181" ```

#### Expected Output: imok (I am OK).
Understanding the flags used:

* ```exec```: Tells Docker to execute a command inside an already running container.
* ```sh -c```: Opens the shell inside the Linux container and passes a string command to be executed.
* ```echo ruok | nc localhost 2181```: Sends the "Are you OK?" (```ruok```) command using Netcat (```nc```) to Zookeeper's default port (```2181```).


### 3. Starting the Kafka Cluster
Once Zookeeper is healthy, start the Kafka cluster (Brokers, Schema Registry, and Kafka Manager):
```docker compose -f common.yml -f kafka_cluster.yml up -d```

### 3.1 Creating Kafka Topics
After the cluster is up and running, we need to create the specific topics required by our Domain-Driven Design (DDD)
business rules. Run the initialization script (without the -d flag so you can see the logs finish):

```docker compose -f common.yml -f init_kafka.yml up ```

## 4. Configuring Kafka Manager (CMAK)
To visually monitor your Kafka cluster, topics, and brokers, we use Kafka Manager.

1. Open your browser and access: ```http://localhost:9000/```
2. In the top menu, go to Cluster > Add Cluster.
3. Fill in the following fields:
    * Cluster Name: ``` food-ordering-system-cluster```
    * Cluster Zookeeper Hosts: ```zookeeper:2181```
        * **What does zookeeper:2181 mean**? > Since all our containers are running inside the same internal Docker network
        (```food-ordering-system```), they can talk to each other using their service names instead of IP addresses. zookeeper
        is the internal DNS hostname of our container, and 2181 is the port it uses to communicate.
4. Enable the checkbox: ```Enable JMX Polling``` (Optional but recommended for metrics).
5. Click Save.

Now you can access http://localhost:9000/clusters/food-ordering-system-cluster to see your running brokers and dynamically created topics!

To run everything:
```docker compose -f common.yml -f zookeeper.yml -f kafka_cluster.yml -f init_kafka.yml up -d --build```

To create the project dependency graph:
```mvn com.github.ferstl:depgraph-maven-plugin:aggregate -DcreateImage=true -DreduceEdges=false -Dscope=compile "-Dincludes=com.food.ordering.system*:*"```

## Goals of This Project

* Demonstrate enterprise-grade architecture
* Practice tactical DDD patterns
* Explore event-driven microservices
* Serve as a reference blueprint for scalable systems

## Status

Work in progress, new modules and patterns will be added incrementally.

