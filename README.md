
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

## Goals of This Project

* Demonstrate enterprise-grade architecture
* Practice tactical DDD patterns
* Explore event-driven microservices
* Serve as a reference blueprint for scalable systems

## Status

Work in progress, new modules and patterns will be added incrementally.

