# 🚀 High-Concurrency with Java 21 Virtual Threads & Spring Boot 3

[![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/javase/21-relnote-issues.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-brightgreen?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## ✨ Overview

This project is a practical demonstration of how to leverage **Java 21's Virtual Threads (Project Loom)** within a **Spring Boot 3.2+** application. The goal is to build highly scalable, I/O-bound services by moving away from the traditional thread-per-request model.

The application is a simple REST API that aggregates cryptocurrency prices from multiple (simulated) external sources concurrently, showcasing the core benefits of Virtual Threads.

---

## 🤔 The Problem: The Scalability Bottleneck

In classic web applications, each incoming request blocks a limited OS-level platform thread while waiting for I/O operations (like API calls or database queries). This leads to resource exhaustion and poor performance under high load, as your application can only handle as many concurrent requests as it has threads.

> **Analogy:** Imagine a call center with a fixed number of phone lines. If every operator puts a caller on hold to look up information, no new calls can come in.

---

## 💡 The Solution: Java 21's Virtual Threads

Virtual Threads are lightweight threads managed by the JVM, not the OS. Millions can be created without significant memory overhead. When a virtual thread encounters a blocking I/O operation, it **unmounts** from its carrier OS thread, freeing it to do other work. This simple change unlocks massive scalability for I/O-bound workloads.

> **New Analogy:** Imagine a team of chefs in a kitchen (OS threads). They can handle thousands of orders (virtual threads). When a chef puts a dish in the oven (I/O operation), they don't wait. They immediately start the next order. When the oven dings, any available chef can finish the dish.

---

## 🛠️ Technology Stack

* **Java 21**
* **Spring Boot 3.2+**
* **Spring Web**
* **Maven**

## 📋 Prerequisites

Ensure you have the following installed:
* JDK 21 or later
* Apache Maven

---

## ▶️ How to Run the Application

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/rpdesenvolve/crypto-aggregator-virtual-threads.git
    cd <project-directory>
    ```

2.  **Run the application using the Maven wrapper:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

---

## 🔍 How to Test

Once the application is running, use `curl` or your browser to access the `/v1/api/quotes` endpoint:

```bash
curl http://localhost:8080/v1/api/quotes
```

You will receive a JSON response with simulated prices:
```json
{
  "BTC": 56491.53,
  "SOL": 39416.67,
  "ETH": 37422.23,
  "DOGE": 43013.53,
  "ADA": 36111.31
}
```

---

## 🪄 Observing the Magic: See Virtual Threads in Action!

The real magic is in the logs. You will see that each concurrent task runs on its own **Virtual Thread**.

<details>
<summary>👀 Click to see the log output with Virtual Threads</summary>

```text
--- INFO 19544 --- [omcat-handler-1] b.c.r.c.a.input.rest.QuoteController     : Request received to search for quotes.
--- INFO 19544 --- [     virtual-67] b.c.r.c.a.service.CryptoService          : Searching for quote for: ADA. Thread: VirtualThread[#67]/runnable@ForkJoinPool-1-worker-6
--- INFO 19544 --- [     virtual-66] b.c.r.c.a.service.CryptoService          : Searching for quote for: ETH. Thread: VirtualThread[#66]/runnable@ForkJoinPool-1-worker-1
--- INFO 19544 --- [     virtual-69] b.c.r.c.a.service.CryptoService          : Searching for quote for: DOGE. Thread: VirtualThread[#69]/runnable@ForkJoinPool-1-worker-5
--- INFO 19544 --- [     virtual-65] b.c.r.c.a.service.CryptoService          : Searching for quote for: BTC. Thread: VirtualThread[#65]/runnable@ForkJoinPool-1-worker-4
--- INFO 19544 --- [     virtual-68] b.c.r.c.a.service.CryptoService          : Searching for quote for: SOL. Thread: VirtualThread[#68]/runnable@ForkJoinPool-1-worker-3
--- INFO 19544 --- [omcat-handler-1] b.c.r.c.a.input.rest.QuoteController     : Quote search complete.
```
</details>

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.