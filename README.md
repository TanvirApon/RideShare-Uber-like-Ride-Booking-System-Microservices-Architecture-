# 🚗 RideShare — Uber-like Ride Booking System (Microservices Architecture)

A scalable, event-driven ride-hailing backend system inspired by Uber, built using **Java Spring Boot microservices**, **Kafka**, **Redis Geospatial Indexing**, and **Zookeeper**. This project simulates real-world ride booking flows — from driver location tracking to intelligent driver matching — designed to handle **1000+ concurrent drivers** efficiently.

---

## 📌 Overview

This system solves the core problem behind apps like Uber/Lyft: **how to find and match the nearest available driver to a rider in real time, at scale.**

It's built with a microservices architecture where each service has a single responsibility, communicating asynchronously via **Apache Kafka** for loose coupling and high throughput.

---

## 🏗️ High-Level Architecture

```
┌─────────────┐      ┌──────────────────┐      ┌─────────────────┐
│   Client     │ ───▶ │   Ride Service    │ ───▶ │  Kafka Broker    │
│ (Rider/App) │      │ (Ride Requests)   │      │ (Ride.Requested) │
└─────────────┘      └──────────────────┘      └────────┬────────┘
                                                          │
                              ┌───────────────────────────┘
                              ▼
                     ┌──────────────────┐      ┌─────────────────┐
                     │ Matching Service  │ ───▶ │ Location Service │
                     │ (Best Driver)     │      │ (Redis GEO Index)│
                     └──────────────────┘      └─────────────────┘
```

**Core Services:**
- 🧭 **Location Service** — Tracks real-time driver locations using Redis Geospatial commands
- 🚕 **Ride Service** — Handles ride requests, fare estimation, and ride lifecycle
- 🤝 **Matching Service** — Finds and assigns the best nearby driver to a ride request
- 📨 **Kafka + Zookeeper** — Event streaming backbone connecting all services asynchronously

---

## ⚙️ Tech Stack

| Category | Technology |
|---|---|
| Language & Framework | Java, Spring Boot |
| Messaging / Event Streaming | Apache Kafka, Zookeeper |
| Geospatial Search | Redis (GEOADD, GEOSEARCH, GEODIST, GEOPOS) |
| Database | PostgreSQL / MySQL *(update based on your setup)* |
| Architecture Style | Microservices, Event-Driven Design |
| Build Tool | Maven / Gradle |
| Containerization | Docker, Docker Compose |

---

## ✨ Key Features

- 📍 Real-time driver location tracking using **Redis Geospatial Index**
- 🔍 Fast nearby-driver search within a given radius (`GEOSEARCH`)
- 🧮 Dynamic **fare estimation algorithm**
- 🏆 Intelligent **best-driver matching algorithm** (not just nearest, but optimal)
- 📡 Asynchronous, event-driven communication via **Kafka** (`Ride.Requested`, `Driver.Assigned` events)
- 🧩 Independently deployable microservices
- 📈 Designed to scale to **1000+ concurrent drivers**

---

## 🗂️ Project Structure

```
rideshare-uber-clone/
├── location-service/      # Driver location tracking (Redis Geo)
├── ride-service/           # Ride requests, fare calculation
├── matching-service/       # Driver-rider matching engine
├── docker-compose.yml      # Kafka, Zookeeper, Redis, DBs setup
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker & Docker Compose
- Redis
- Apache Kafka + Zookeeper

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/rideshare-uber-clone.git
   cd rideshare-uber-clone
   ```

2. **Start infrastructure (Kafka, Zookeeper, Redis, DBs)**
   ```bash
   docker-compose up -d
   ```

3. **Run each microservice**
   ```bash
   cd location-service && ./mvnw spring-boot:run
   cd ride-service && ./mvnw spring-boot:run
   cd matching-service && ./mvnw spring-boot:run
   ```

4. **Test the flow**
   - Register drivers via Location Service (`GEOADD`)
   - Request a ride via Ride Service
   - Matching Service auto-assigns the best available driver

---

## 🧠 How It Works

1. Drivers continuously send their live location → stored in **Redis** using `GEOADD`
2. A rider requests a ride → **Ride Service** publishes a `Ride.Requested` event to **Kafka**
3. **Matching Service** consumes the event, queries **Location Service** for nearby drivers using `GEOSEARCH`
4. Best driver is selected using a matching algorithm (distance + availability + rating, etc.)
5. Driver assignment status is updated and published back through Kafka

---

## 📺 Reference

This project was built by following along a detailed system design & implementation tutorial covering HLD, Redis Geospatial commands, Kafka event flows, and full microservice code walkthroughs.

---

## 📄 License

This project is for learning/educational purposes. Feel free to fork and modify.

---

## 🙋‍♂️ Author

Built by **[Your Name]** — feel free to connect or contribute!
