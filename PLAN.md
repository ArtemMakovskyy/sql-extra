# План: PostgreSQL Database для data-analytics-mate.DA

## Зміст

1. Опис проєкту
2. Сутності (Entities)
3. Таблиці та поля
4. Зв'язки між таблицями
5. DDL (PostgreSQL)
6. Java Entity (JPA)
7. Репозиторії
8. Черга виконання

---

## 1. Опис проєкту

База даних інтернет-магазину, що зберігає інформацію про:

- Реєстрації та замовлення користувачів
- Дії користувачів на сайті
- Надіслані повідомлення (email)
- A/B тести
- Витрати на платний трафік

**Важливо:**

- `ga_session_id` — ідентифікатор сесії, може повторюватись (1:N)
- Типи даних: PostgreSQL native types
- Імена таблиць: `session` → `sessions`, `order` → `orders`

---

## 2. Сутності (Entities)

| Сутність | Таблиця | Опис |
|----------|---------|------|
| AbTest | ab_test | A/B тести |
| Account | account | Підписники сайту |
| AccountSession | account_session | Сесії підписки |
| EmailSent | email_sent | Надіслані листи |
| EmailOpen | email_open | Відкриті листи |
| EmailVisit | email_visit | Кліки в листах |
| EventParams | event_params | Події користувача |
| OrderEntity | orders | Замовлення |
| PaidSearchCost | paid_search_cost | Витрати на трафік |
| Product | product | Товари |
| RevenuePredict | revenue_predict | Прогнози доходу |
| Session | sessions | Сесії користувачів |
| SessionParams | session_params | Метадані сесій |

---

## 3. Таблиці та поля

### 1) product
| Поле | Тип | Опис |
|------|-----|------|
| item_id | INTEGER | PK - Унікальний ідентифікатор |
| name | VARCHAR(255) | Назва товару |
| category | VARCHAR(100) | Категорія товару |
| price | DECIMAL(10,2) | Ціна, USD |
| short_description | TEXT | Короткий опис |

### 2) account
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| send_interval | INTEGER | Інтервал отримання листів |
| is_verified | INTEGER | Підтвердження email (0/1), NOT NULL, CHECK (0,1) |
| is_unsubscribed | INTEGER | Відписка (0/1), NOT NULL, CHECK (0,1) |

### 3) sessions
| Поле | Тип | Опис |
|------|-----|------|
| ga_session_id | VARCHAR(255) | PK - Унікальний ідентифікатор сесії |
| date | DATE | Дата сесії |

### 4) session_params
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| ga_session_id | VARCHAR(255) | UNIQUE - Посилання на сесію |
| device | VARCHAR(50) | Тип пристрою |
| mobile_model_name | VARCHAR(100) | Модель мобільного |
| operating_system | VARCHAR(50) | ОС |
| language | VARCHAR(10) | Мова браузера |
| browser | VARCHAR(50) | Браузер |
| continent | VARCHAR(20) | Континент |
| country | VARCHAR(50) | Країна |
| medium | VARCHAR(50) | Джерело трафіку |
| name | VARCHAR(100) | Додаткова інформація |
| channel | VARCHAR(50) | Канал трафіку |

### 5) account_session
| Поле | Тип | Опис |
|------|-----|------|
| account_id | BIGINT | PK часть 1 - Посилання на account |
| ga_session_id | VARCHAR(255) | PK часть 2 - Посилання на sessions |

### 6) ab_test
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| ga_session_id | VARCHAR(255) | NOT NULL - Посилання на sessions |
| test | INTEGER | Номер тесту |
| test_group | INTEGER | Група (1=A, 2=Б) |

### 7) event_params
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| ga_session_id | VARCHAR(255) | NOT NULL - Посилання на sessions |
| event_date | DATE | Дата події |
| event_timestamp | TIMESTAMP | Час події |
| event_name | VARCHAR(100) | Назва події |
| event_params | JSONB | Параметри події |

### 8) orders
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| ga_session_id | VARCHAR(255) | NOT NULL - Посилання на sessions |
| item_id | INTEGER | NOT NULL - Посилання на product |

### 9) email_sent
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| id_account | BIGINT | NOT NULL - Посилання на account |
| sent_date | INTEGER | Дні після створення акаунта |
| letter_type | INTEGER | Тип листа |
| id_message | VARCHAR(100) | Ідентифікатор повідомлення |

### 10) email_open
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| id_account | BIGINT | NOT NULL - Посилання на account |
| open_date | INTEGER | Дні після створення акаунта |
| letter_type | INTEGER | Тип листа |
| id_message | VARCHAR(100) | Ідентифікатор листа |

### 11) email_visit
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| id_account | BIGINT | NOT NULL - Посилання на account |
| visit_date | INTEGER | Дні після створення акаунта |
| letter_type | INTEGER | Тип листа |
| id_message | VARCHAR(100) | Ідентифікатор листа |

### 12) paid_search_cost
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| date | DATE | Дата витрати |
| cost | DECIMAL(12,2) | Сума витрат |

### 13) revenue_predict
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGSERIAL | PK - Унікальний ідентифікатор |
| date | DATE | Дата прогнозу |
| predict | DECIMAL(12,2) | Прогноз, USD |

---

## 4. Зв'язки між таблицями

```
┌─────────────────────────────────────────────────────────────────┐
│                          account                                │
│  id (PK)  ──────────────────────────────────────────────────┐  │
│  is_verified, is_unsubscribed, send_interval                   │  │
└───────────────────────────────────────────────────────────────┼──┘
                              │                                  │
            ┌─────────────────┼──────────────────────────────────┼─┐
            │                 │                                  │ │
            ▼                 ▼                                  │ │
    ┌───────────────┐ ┌───────────────┐                         │ │
    │  email_sent   │ │  email_open   │                         │ │
    │  email_visit  │ └───────────────┘                         │ │
    └───────────────┘                                           │ │
                                                                │ │
    ┌──────────────────────────────────────────────────────────┼─┤
    │                   account_session                        │
    │  (account_id, ga_session_id) - COMPOSITE PK              │
    └───────────────────────────────────────────────────────────┘
                              │                                  │
                              ▼                                  │
┌───────────────┐                                    ┌───────────────┐
│   sessions    │                                    │ session_params│
│───────────────│                                    │───────────────│
│ga_session_id  │◄─── 1:1 ───────────────────────────│ga_session_id  │ (UNIQUE)
│ date          │                                    │ device        │
└───────┬───────┘                                    │ country       │
        │                                            │ ...           │
        │    ┌───┬───┬───────────┐                   └───────────────┘
        │    │   │   │           │
        ▼    ▼   ▼   ▼           ▼
┌─────────┐ ┌─────────┐ ┌─────────────┐
│ ab_test │ │ orders  │ │ event_params│
│─────────│ │─────────│ │─────────────│
│ga_sess. │ │ga_sess. │ │ga_sess.     │
│ test    │ │ item_id │ │ event_*     │
│test_gr. │ └────┬────┘ └─────────────┘
└─────────┘      │
                 ▼
          ┌─────────────┐
          │   product   │
          │─────────────│
          │ item_id (PK)│
          │ name        │
          │ price       │
          └─────────────┘

┌────────────────────────────────────────────────────────────┐
│  paid_search_cost         revenue_predict                  │
│       date, cost              date, predict                │
└────────────────────────────────────────────────────────────┘
```

---

## 5. DDL (PostgreSQL)

```sql
-- product
CREATE TABLE product (
    item_id INTEGER PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(100),
    price DECIMAL(10,2),
    short_description TEXT
);

-- account
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    send_interval INTEGER,
    is_verified INTEGER NOT NULL CHECK (is_verified IN (0, 1)),
    is_unsubscribed INTEGER NOT NULL CHECK (is_unsubscribed IN (0, 1))
);

-- sessions
CREATE TABLE sessions (
    ga_session_id VARCHAR(255) PRIMARY KEY,
    date DATE
);

-- session_params
CREATE TABLE session_params (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL UNIQUE,
    device VARCHAR(50),
    mobile_model_name VARCHAR(100),
    operating_system VARCHAR(50),
    language VARCHAR(10),
    browser VARCHAR(50),
    continent VARCHAR(20),
    country VARCHAR(50),
    medium VARCHAR(50),
    name VARCHAR(100),
    channel VARCHAR(50)
);

-- account_session
CREATE TABLE account_session (
    account_id BIGINT NOT NULL,
    ga_session_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (account_id, ga_session_id)
);

-- ab_test
CREATE TABLE ab_test (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    test INTEGER,
    test_group INTEGER
);

-- event_params
CREATE TABLE event_params (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    event_date DATE,
    event_timestamp TIMESTAMP,
    event_name VARCHAR(100),
    event_params JSONB
);

-- orders
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    item_id INTEGER NOT NULL
);

-- email_sent
CREATE TABLE email_sent (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    sent_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

-- email_open
CREATE TABLE email_open (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    open_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

-- email_visit
CREATE TABLE email_visit (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    visit_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

-- paid_search_cost
CREATE TABLE paid_search_cost (
    id BIGSERIAL PRIMARY KEY,
    date DATE,
    cost DECIMAL(12,2)
);

-- revenue_predict
CREATE TABLE revenue_predict (
    id BIGSERIAL PRIMARY KEY,
    date DATE,
    predict DECIMAL(12,2)
);

-- Foreign Keys
ALTER TABLE session_params ADD CONSTRAINT fk_session_params_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

ALTER TABLE account_session ADD CONSTRAINT fk_account_session_account
    FOREIGN KEY (account_id) REFERENCES account(id);

ALTER TABLE account_session ADD CONSTRAINT fk_account_session_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

ALTER TABLE ab_test ADD CONSTRAINT fk_ab_test_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

ALTER TABLE event_params ADD CONSTRAINT fk_event_params_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_product
    FOREIGN KEY (item_id) REFERENCES product(item_id);

ALTER TABLE email_sent ADD CONSTRAINT fk_email_sent_account
    FOREIGN KEY (id_account) REFERENCES account(id);

ALTER TABLE email_open ADD CONSTRAINT fk_email_open_account
    FOREIGN KEY (id_account) REFERENCES account(id);

ALTER TABLE email_visit ADD CONSTRAINT fk_email_visit_account
    FOREIGN KEY (id_account) REFERENCES account(id);

-- Індекси
CREATE INDEX idx_sessions_date ON sessions(date);
CREATE INDEX idx_session_params_session ON session_params(ga_session_id);
CREATE INDEX idx_account_session_account ON account_session(account_id);
CREATE INDEX idx_account_session_session ON account_session(ga_session_id);
CREATE INDEX idx_ab_test_session ON ab_test(ga_session_id);
CREATE INDEX idx_event_params_session ON event_params(ga_session_id);
CREATE INDEX idx_event_params_date ON event_params(event_date);
CREATE INDEX idx_orders_session ON orders(ga_session_id);
CREATE INDEX idx_orders_item ON orders(item_id);
CREATE INDEX idx_email_sent_account ON email_sent(id_account);
CREATE INDEX idx_email_open_account ON email_open(id_account);
CREATE INDEX idx_email_visit_account ON email_visit(id_account);
```

---

## 6. Java Entity (JPA)

### Product.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "item_id")
    private Integer itemId;

    private String name;
    private String category;
    private BigDecimal price;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;
}
```

### Account.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "send_interval")
    private Integer sendInterval;

    @Column(name = "is_verified", nullable = false)
    private Integer isVerified;

    @Column(name = "is_unsubscribed", nullable = false)
    private Integer isUnsubscribed;
}
```

### Session.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
public class Session {

    @Id
    @Column(name = "ga_session_id")
    private String gaSessionId;

    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return gaSessionId != null && gaSessionId.equals(session.gaSessionId);
    }

    @Override
    public int hashCode() {
        return gaSessionId != null ? gaSessionId.hashCode() : 0;
    }
}
```

### SessionParams.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "session_params")
@Getter
@Setter
@NoArgsConstructor
public class SessionParams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ga_session_id", nullable = false, unique = true)
    private String gaSessionId;

    private String device;

    @Column(name = "mobile_model_name")
    private String mobileModelName;

    @Column(name = "operating_system")
    private String operatingSystem;

    private String language;
    private String browser;
    private String continent;
    private String country;
    private String medium;
    private String name;
    private String channel;
}
```

### AccountSession.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountSessionId implements Serializable {
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "ga_session_id")
    private String gaSessionId;
}

@Entity
@Table(name = "account_session")
@Getter
@Setter
@NoArgsConstructor
public class AccountSession {
    @EmbeddedId
    private AccountSessionId id;
}
```

### AbTest.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ab_test")
@Getter
@Setter
@NoArgsConstructor
public class AbTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ga_session_id", nullable = false)
    private String gaSessionId;

    private Integer test;
    private Integer testGroup;
}
```

### EventParams.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_params")
@Getter
@Setter
@NoArgsConstructor
public class EventParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ga_session_id", nullable = false)
    private String gaSessionId;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_timestamp")
    private LocalDateTime eventTimestamp;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_params", columnDefinition = "jsonb")
    private String eventParams;
}
```

### OrderEntity.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ga_session_id", nullable = false)
    private String gaSessionId;

    @Column(name = "item_id", nullable = false)
    private Integer itemId;
}
```

### EmailSent.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email_sent")
@Getter
@Setter
@NoArgsConstructor
public class EmailSent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_account", nullable = false)
    private Long idAccount;

    @Column(name = "sent_date")
    private Integer sentDate;

    @Column(name = "letter_type")
    private Integer letterType;

    @Column(name = "id_message")
    private String idMessage;
}
```

### EmailOpen.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email_open")
@Getter
@Setter
@NoArgsConstructor
public class EmailOpen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_account", nullable = false)
    private Long idAccount;

    @Column(name = "open_date")
    private Integer openDate;

    @Column(name = "letter_type")
    private Integer letterType;

    @Column(name = "id_message")
    private String idMessage;
}
```

### EmailVisit.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email_visit")
@Getter
@Setter
@NoArgsConstructor
public class EmailVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_account", nullable = false)
    private Long idAccount;

    @Column(name = "visit_date")
    private Integer visitDate;

    @Column(name = "letter_type")
    private Integer letterType;

    @Column(name = "id_message")
    private String idMessage;
}
```

### PaidSearchCost.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paid_search_cost")
@Getter
@Setter
@NoArgsConstructor
public class PaidSearchCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private BigDecimal cost;
}
```

### RevenuePredict.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "revenue_predict")
@Getter
@Setter
@NoArgsConstructor
public class RevenuePredict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private BigDecimal predict;
}
```

---

## 7. Репозиторії

| Repository | Entity |
|------------|--------|
| ProductRepository | Product |
| AccountRepository | Account |
| SessionRepository | Session |
| SessionParamsRepository | SessionParams |
| AccountSessionRepository | AccountSession |
| AbTestRepository | AbTest |
| EventParamsRepository | EventParams |
| OrderRepository | OrderEntity |
| EmailSentRepository | EmailSent |
| EmailOpenRepository | EmailOpen |
| EmailVisitRepository | EmailVisit |
| PaidSearchCostRepository | PaidSearchCost |
| RevenuePredictRepository | RevenuePredict |

---

## 8. Черга виконання

### Черга 1 (без залежностей)

1. product ✅
2. account ✅
3. session ✅

### Черга 2 (залежить від черги 1)

4. session_params ✅
5. account_session ✅
6. ab_test ✅
7. event_params ✅
8. order ✅
9. email_sent ✅
10. email_open ✅
11. email_visit ✅
12. paid_search_cost ✅
13. revenue_predict ✅

---

## Важливо

- Таблиці `"session"` і `"order"` — в лапках (ключові слова SQL)
- Колонки НЕ перейменовувати: `id_account`, `ga_session_id`, `item_id`
- `ga_session_id` — TEXT (відповідає BigQuery STRING)
- Для таблиць без PK в BigQuery: використовувати @EmbeddedId або JdbcTemplate