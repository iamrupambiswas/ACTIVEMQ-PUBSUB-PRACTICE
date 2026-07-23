# ActiveMQ Publish/Subscribe Practice

A hands-on Apache ActiveMQ Pub/Sub project built with **Spring Boot**.

This repository demonstrates how multiple independent microservices can consume the same event published by a producer using the **JMS Publish/Subscribe (Topic)** model.

Unlike the Queue model, every active subscriber receives a copy of the published message.

---

# Project Structure

```
ActiveMQ-PubSub-Practice
│
├── order-producer
│
├── email-consumer
│
├── inventory-consumer
│
└── analytics-consumer
```

Each application is an independent Spring Boot project.

| Project | Purpose |
|----------|---------|
| order-producer | Publishes Order Events |
| email-consumer | Simulates Email Notification Service |
| inventory-consumer | Simulates Inventory Service |
| analytics-consumer | Simulates Analytics Service |

---

# Architecture

```
                 POST /orders
                       │
                       ▼
              Order Producer
                       │
                       ▼
         ActiveMQ Topic (order.created.topic)
          ┌────────────┼────────────┐
          │            │            │
          ▼            ▼            ▼
 Email Consumer   Inventory    Analytics
                    Consumer     Consumer
```

Every consumer receives the same event.

---

# Technologies Used

- Java 21
- Spring Boot 3.5.x
- Spring JMS
- Apache ActiveMQ Classic 6.x
- Maven
- Jackson

---

# Publish/Subscribe Model

This project uses **Topics**.

```
Producer

        │

        ▼

order.created.topic

   │      │      │

   ▼      ▼      ▼

Email  Inventory Analytics
```

Unlike Queues,

- every active subscriber receives the event
- consumers do not compete for messages
- one published event can trigger multiple independent services

---

# Message Format

This project intentionally **does not use Java object serialization**.

Messages are published as plain JSON.

Example:

```json
{
  "orderId": 101,
  "customerName": "Rupam",
  "productName": "MacBook Air M4",
  "quantity": 1,
  "price": 99999
}
```

Each consumer converts the JSON into its own `OrderEvent` DTO using Jackson.

This removes coupling between services and is much closer to how production microservices communicate.

---

# Why JSON Instead of Java Objects?

Instead of sending Java class metadata (`_type` header), this project sends plain JSON.

Advantages:

- No ClassNotFoundException
- No package dependency
- Services remain independent
- Compatible with Java, Python, Go, Node.js, etc.
- Similar approach used with Kafka

---

# Running ActiveMQ

Download Apache ActiveMQ Classic.

Navigate to:

```
apache-activemq/bin
```

Start the broker.

Windows

```bash
activemq.bat start
```

Linux / macOS

```bash
./activemq start
```

---

# ActiveMQ Web Console

```
http://localhost:8161/admin
```

Default credentials

```
Username : admin
Password : admin
```

Useful pages

- Queues
- Topics
- Connections
- Subscribers
- Send Message

---

# Running the Applications

Start applications in the following order.

### 1. ActiveMQ

```
activemq.bat start
```

---

### 2. Email Consumer

Run

```
email-consumer
```

---

### 3. Inventory Consumer

Run

```
inventory-consumer
```

---

### 4. Analytics Consumer

Run

```
analytics-consumer
```

---

### 5. Producer

Run

```
order-producer
```

---

# Testing

Send a POST request.

```
POST http://localhost:8081/orders
```

Request Body

```json
{
  "orderId":101,
  "customerName":"Rupam",
  "productName":"MacBook Air M4",
  "quantity":1,
  "price":99999
}
```

Expected Response

```
Order Event Published Successfully
```

---

# Expected Console Output

Producer

```
Publishing Order Event

{
  ...
}
```

Email Consumer

```
EMAIL SERVICE

OrderEvent(...)
```

Inventory Consumer

```
INVENTORY SERVICE

OrderEvent(...)
```

Analytics Consumer

```
ANALYTICS SERVICE

OrderEvent(...)
```

All three consumers receive the same event.

---

# Verifying Through ActiveMQ Web Console

Open

```
http://localhost:8161/admin/topics.jsp
```

You should see

```
order.created.topic
```

Click the topic to inspect

- Active Subscribers
- Enqueue Count
- Dequeue Count

---

# Important Behavior

## If consumers are running

```
Producer

↓

Topic

↓

All consumers receive the event
```

---

## If no consumer is running

Messages are **not stored**.

The event is discarded because Topics broadcast only to active subscribers.

---

# Difference Between Queue and Topic

| Queue | Topic |
|--------|-------|
| One consumer receives the message | Every active subscriber receives the message |
| Messages are stored until consumed | Messages are discarded if nobody is subscribed |
| Competing Consumers | Broadcast Model |
| Point-to-Point | Publish/Subscribe |

---

# Learning Outcomes

This project demonstrates:

- JMS Publish/Subscribe
- Topics
- Spring JMS
- ActiveMQ Classic
- Multiple Independent Consumers
- Event Driven Architecture
- JSON-based Messaging
- Microservice Communication

---

# Future Improvements

- Durable Subscribers
- Dead Letter Queue (DLQ)
- Retry Mechanism
- Message Selectors
- Acknowledgement Modes
- Transactions
- Async Processing
- Docker Compose Setup
- Monitoring with JMX
- Kafka Migration

---

# Author

**Rupam Biswas**

Built as part of a hands-on journey to learn Event-Driven Microservices using ActiveMQ before moving on to Apache Kafka.