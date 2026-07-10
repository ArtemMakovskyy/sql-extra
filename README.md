# SQL Extra

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791)
![Docker](https://img.shields.io/badge/Docker-compose-2496ED)
![License](https://img.shields.io/badge/License-MIT-yellow)

A practical project for demonstrating SQL, PostgreSQL, and Spring Boot skills. Simulates web analytics data: user sessions, events, A/B tests, email funnel, and orders. Contains 15+ SQL files, REST API, test data generation, and Docker infrastructure.

Data is automatically generated on first startup so you can start writing queries immediately.

[All project SQL queries here](docs/database-schema.md)

## Tech Stack

| Technology | Purpose |
|---|---|
| Spring Boot 3.3 | Application framework |
| Spring Data JPA | ORM (Entity → Repository) |
| Liquibase | Database migrations (DDL) |
| PostgreSQL 17 | Database |
| MapStruct 1.6 | Entity ↔ DTO mapping |
| Lombok | Getters, setters, constructors |
| DataFaker 2.4 | Test data generation |

## Features

| # | Demonstrates |
|---|---|
| 1 | **DB Design** — 13 tables, normalization, 1:1, 1:M, M:N relationships |
| 2 | **SQL Queries** — 15 files: JOIN, CASE WHEN, UNION, subqueries, window functions, CTE, VIEW |
| 3 | **REST API** — CRUD for all entities (Spring Boot + JPA + MapStruct) |
| 4 | **Data Generation** — DataFaker, realistic scenarios (email funnel, A/B tests, web analytics) |
| 5 | **DDL Migrations** — Liquibase, 13 files |
| 6 | **Infrastructure** — Docker Compose (PostgreSQL + pgAdmin + Spring Boot) |

## 📚 Documentation & Guides

| Document | Description |
|---|---|
| [Installation Guide](docs/installation.md) | Requirements, Docker setup, pgAdmin configuration |
| [Data Configuration](docs/data-generation.md) | `.env` variables, what DataSeeder creates on startup |
| [Database Schema](docs/database-schema.md) | ERD diagram, table descriptions, SQL queries |
| [Project Structure](docs/project-structure.md) | Project folders, Liquibase migrations |
| [Technical Plan](docs/plan.md) | Entity descriptions, DDL, Java Entity, execution order |

## Author

**Artem Makovskyy**
- Email: artem.makovskyi.jv@gmail.com
- [LinkedIn](https://www.linkedin.com/in/artem-makovskyi-557783304/)
- [GitHub](https://github.com/ArtemMakovskyy)

