# Content Management Backend - Spring Boot

This is a Spring Boot-based backend application designed for content management. It integrates with **Redis** for caching, **Kafka** for messaging, **Spring Security** for authentication and authorization, and **JPA** (Java Persistence API) for database management. The backend is optimized for handling content such as articles, images, and videos efficiently with real-time updates.

## Features

- **Spring Boot** backend for rapid development and deployment.
- **Redis** caching for fast retrieval of content data.
- **Apache Kafka** for event-driven architecture and messaging.
- **Spring Security** for authentication and authorization.
- **JPA** for database integration with persistence (using Hibernate).
- **Content Management** capabilities to manage various content types like articles, images, and videos.

## Technologies Used

- **Spring Boot**: For building the backend application.
- **Spring Data JPA**: For database management and object-relational mapping (ORM).
- **Redis**: In-memory data store for caching content and reducing database load.
- **Kafka**: Distributed messaging system for real-time event handling.
- **Spring Security**: Authentication and authorization to secure the backend.
- **H2 Database**: In-memory database for development (can be replaced with any relational database like MySQL or PostgreSQL).
- **Lombok**: To reduce boilerplate code.

## Prerequisites

Before running the application, make sure you have the following installed:

- **Java 17 or higher**
- **Maven 3.x or Gradle** (Maven is used in this example)
- **Docker** (optional, for running Redis and Kafka locally)
- **Redis**: Can be installed locally or run via Docker
- **Apache Kafka**: Can be run locally or via Docker

## Setup

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/vivekPandeyDev/publishflow-content-manager.git
   cd publishflow-content-manager
