# SQL Extra

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791)
![Docker](https://img.shields.io/badge/Docker-compose-2496ED)
![License](https://img.shields.io/badge/License-MIT-yellow)

Практичний проєкт для демонстрації навичок роботи з SQL, PostgreSQL та Spring Boot. Симулює дані веб-аналітики: сесії користувачів, події, A/B тести, email-воронку, замовлення. Містить 15+ SQL файлів, REST API, генерацію тестових даних та Docker-інфраструктуру.

Дані автоматично генеруються при першому запуску, щоб одразу почати писати запити.

[Всі SQL запити проєкта тут](docs/database-schema.md)

## Технологічний стек

| Технологія | Призначення |
|---|---|
| Spring Boot 3.3 | Каркас додатку |
| Spring Data JPA | ORM (Entity → Repository) |
| Liquibase | Міграції бази даних (DDL) |
| PostgreSQL 17 | База даних |
| MapStruct 1.6 | Мапінг Entity ↔ DTO |
| Lombok | Гетери, сетери, конструктори |
| DataFaker 2.4 | Генерація тестових даних |

## Можливості (Features)

| # | Що демонструє |
|---|---|
| 1 | **Проєктування БД** — 13 таблиць, нормалізація, зв'язки 1:1, 1:M, M:N |
| 2 | **SQL запити** — 15 файлів: JOIN, CASE WHEN, UNION, підзапити, віконні функції, CTE, VIEW |
| 3 | **REST API** — CRUD для всіх сутностей (Spring Boot + JPA + MapStruct) |
| 4 | **Генерація даних** — DataFaker, реалістичні сценарії (email-воронка, A/B тести, веб-аналітика) |
| 5 | **DDL міграції** — Liquibase, 13 файлів |
| 6 | **Інфраструктура** — Docker Compose (PostgreSQL + pgAdmin + Spring Boot) |

## 📚 Документація та інструкції

| Документ | Опис |
|---|---|
| [Інструкція із запуску](docs/installation.md) | Вимоги, запуск через Docker, налаштування pgAdmin |
| [Налаштування даних](docs/data-generation.md) | Змінні `.env`, що створює DataSeeder при старті |
| [Схема бази даних](docs/database-schema.md) | ERD діаграма, опис таблиць, SQL запити для виконання |
| [Структура проєкту](docs/project-structure.md) | Папки проєкту, Liquibase міграції |
| [Технічний план](docs/plan.md) | Опис сутностей, DDL, Java Entity, черга виконання |

## Автор

**Artem Makovskyy**
- Email: artem.makovskyi.jv@gmail.com
- [LinkedIn](https://www.linkedin.com/in/artem-makovskyi-557783304/)
- [GitHub](https://github.com/ArtemMakovskyy)

