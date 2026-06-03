# Інструкція із запуску

## Вимоги

- **Git** — клонувати репозиторій
- **Docker** та **Docker Compose** — запустити проєкт

Команди виконуються в терміналі (PowerShell, cmd або bash):

```bash
git clone https://github.com/ArtemMakovskyy/sql-extra.git
cd sql-extra
```

Встановити Docker:
- [Windows](https://docs.docker.com/desktop/setup/install/windows-install/)
- [macOS](https://docs.docker.com/desktop/setup/install/mac-install/)
- [Linux](https://docs.docker.com/desktop/setup/install/linux/)

## Запуск

```bash
docker compose up -d
```

Після запуску:
- PostgreSQL доступний на порту `5434`
- Додаток доступний на `http://localhost:8080`

## Як виконувати SQL запити через pgAdmin

Відкрити **pgAdmin** у браузері: `http://localhost:5050`

**Логін:** `admin@admin.com`
**Пароль:** `admin`

### Підключення сервера

![RegisterServer.png](../images/RegisterServer.png)

Заповнити поля:
- **Host:** `db-postgres`
- **Port:** `5432`
- **Database:** `app_db`
- **Username:** `postgres`
- **Password:** `postgres`
- 
![connection.png](../images/connection.png)

### Вибір бази даних

Після підключення знайдіть базу `app_db` у списку:
![findDbs.png](../images/findDbs.png)

### Виконання запитів

Відкрити **Query Tool** (правою кнопкою на `app_db` → Query Tool) і виконувати запити з папки [`../sql/`](../sql/).

## Зупинка

```bash
docker compose down
```

Ця команда зупиняє та видаляє контейнери. Якщо потрібно також видалити volumes (всі дані БД):

```bash
docker compose down -v
```

## Troubleshooting

| Проблема | Рішення |
|---|---|
| `port is already allocated` | Змінити порт в `docker-compose.yaml` або зупинити процес, який займає порт |
| `app` контейнер падає | Перевірити, чи `db-postgres` запустився: `docker compose logs db-postgres` |
| pgAdmin не підключається | Вказати Host = `db-postgres` (не `localhost`), бо контейнери в одній мережі |
| Дані не згенерувались | Перевірити `.env` змінні; при першому запуску DataSeeder спрацьовує автоматично |

## Що відбувається при старті

1. **Liquibase** створює таблиці в БД
2. **DataSeeder** наповнює таблиці тестовими даними
