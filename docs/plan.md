# Plan: PostgreSQL Database for data-analytics-mate.DA

## Table of Contents

1. Project Description
2. Entities
3. Tables and Fields
4. Table Relationships
5. DDL (PostgreSQL)
6. Java Entity (JPA)
7. Repositories
8. Execution Order

---

## 1. Project Description

An e-commerce database that stores information about:

- User registrations and orders
- User actions on the site
- Sent emails
- A/B tests
- Paid traffic costs

**Important:**

- `ga_session_id` — session identifier, may repeat (1:N)
- Data types: PostgreSQL native types
- Table names: `session` → `sessions`, `order` → `orders`

---

## 2. Entities

| Entity | Table | Description |
|----------|---------|------|
| AbTest | ab_test | A/B tests |
| Account | account | Site subscribers |
| AccountSession | account_session | Subscription sessions |
| EmailSent | email_sent | Sent emails |
| EmailOpen | email_open | Opened emails |
| EmailVisit | email_visit | Email clicks |
| EventParams | event_params | User events |
| OrderEntity | orders | Orders |
| PaidSearchCost | paid_search_cost | Traffic costs |
| Product | products | Products |
| RevenuePredict | revenue_predict | Revenue forecasts |
| Session | sessions | User sessions |
| SessionParams | session_params | Session metadata |

---

## 3. Tables and Fields

### 1) products
| Field | Type | Description |
|------|-----|------|
| item_id | BIGINT | PK - Unique identifier |
| name | VARCHAR(255) | NOT NULL - Product name |
| category | VARCHAR(100) | Product category |
| price | DECIMAL(10,2) | NOT NULL - Price, USD |
| short_description | TEXT | Short description |

### 2) account
| Field | Type | Description |
|------|-----|------|
| id | BIGSERIAL | PK - Unique identifier |
| send_interval | INTEGER | Email sending interval |
| is_verified | INTEGER | Email verification (0/1), NOT NULL, CHECK (0,1) |
| is_unsubscribed | INTEGER | Unsubscribed (0/1), NOT NULL, CHECK (0,1) |

### 3) sessions
| Field | Type | Description |
|------|-----|------|
| ga_session_id | VARCHAR(255) | PK - Unique session identifier |
| date | DATE | NOT NULL - Session date |

### 4) session_params
| Field | Type | Description |
|------|-----|------|
| ga_session_id | VARCHAR(255) | PK - Unique session identifier |
| device | VARCHAR(50) | Device type |
| mobile_model_name | VARCHAR(100) | Mobile model |
| operating_system | VARCHAR(50) | Operating system |
| language | VARCHAR(50) | Browser language |
| browser | VARCHAR(50) | Browser |
| continent | VARCHAR(20) | Continent |
| country | VARCHAR(50) | Country |
| medium | VARCHAR(50) | Traffic source |
| name | VARCHAR(100) | Additional info |
| channel | VARCHAR(50) | Traffic channel |

### 5) account_session
| Field | Type | Description |
|------|-----|------|
| account_id | BIGINT | PK part 1 - Reference to account |
| ga_session_id | VARCHAR(255) | PK part 2 - Reference to sessions |

### 6) ab_test
| Field | Type | Description |
|------|-----|------|
| ga_session_id | VARCHAR(255) | PK part 1 - Reference to sessions |
| test | INTEGER | PK part 2 - Test number |
| test_group | INTEGER | Group (1=A, 2=B) |

### 7) event_params
| Field | Type | Description |
|------|-----|------|
| ga_session_id | VARCHAR(255) | PK part 1 - Reference to sessions |
| event_date | DATE | Event date |
| event_timestamp | TIMESTAMP | PK part 2 - Event time |
| event_name | VARCHAR(100) | Event name |
| event_params | JSONB | Event parameters |

### 8) orders
| Field | Type | Description |
|------|-----|------|
| id | BIGSERIAL | PK - Unique identifier |
| ga_session_id | VARCHAR(255) | NOT NULL - Reference to sessions |
| item_id | BIGINT | NOT NULL - Reference to products |

### 9) email_sent
| Field | Type | Description |
|------|-----|------|
| id | BIGSERIAL | PK - Unique identifier |
| id_account | BIGINT | NOT NULL - Reference to account |
| sent_date | INTEGER | Days after account creation |
| letter_type | INTEGER | Letter type |
| id_message | VARCHAR(100) | Message identifier |

### 10) email_open
| Field | Type | Description |
|------|-----|------|
| id | BIGSERIAL | PK - Unique identifier |
| id_account | BIGINT | NOT NULL - Reference to account |
| open_date | INTEGER | Days after account creation |
| letter_type | INTEGER | Letter type |
| id_message | VARCHAR(100) | Message identifier |

### 11) email_visit
| Field | Type | Description |
|------|-----|------|
| id | BIGSERIAL | PK - Unique identifier |
| id_account | BIGINT | NOT NULL - Reference to account |
| visit_date | INTEGER | Days after account creation |
| letter_type | INTEGER | Letter type |
| id_message | VARCHAR(100) | Message identifier |

### 12) paid_search_cost
| Field | Type | Description |
|------|-----|------|
| date | DATE | PK - NOT NULL - Expense date |
| cost | DECIMAL(12,2) | NOT NULL - Cost amount |

### 13) revenue_predict
| Field | Type | Description |
|------|-----|------|
| date | DATE | PK - NOT NULL - Forecast date |
| predict | DECIMAL(12,2) | NOT NULL - Forecast, USD |

---

## 4. Table Relationships

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
│ga_session_id  │◄─── 1:1 ───────────────────────────│ga_session_id  │
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
           │   products  │
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
-- products
CREATE TABLE products (
    item_id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(100),
    price DECIMAL(10,2),
    short_description TEXT
);

CREATE INDEX idx_products_category ON products(category);

-- account
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    send_interval INTEGER,
    is_verified INTEGER NOT NULL CHECK (is_verified IN (0, 1)),
    is_unsubscribed INTEGER NOT NULL CHECK (is_unsubscribed IN (0, 1))
);

CREATE INDEX idx_account_is_verified ON account(is_verified);
CREATE INDEX idx_account_is_unsubscribed ON account(is_unsubscribed);

-- sessions
CREATE TABLE sessions (
    ga_session_id VARCHAR(255) PRIMARY KEY,
    date DATE NOT NULL
);

CREATE INDEX idx_sessions_date ON sessions(date);

-- session_params
CREATE TABLE session_params (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL UNIQUE,
    device VARCHAR(50),
    mobile_model_name VARCHAR(100),
    operating_system VARCHAR(50),
    language VARCHAR(50),
    browser VARCHAR(50),
    continent VARCHAR(20),
    country VARCHAR(50),
    medium VARCHAR(50),
    name VARCHAR(100),
    channel VARCHAR(50)
);

ALTER TABLE session_params ADD CONSTRAINT fk_session_params_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

CREATE INDEX idx_session_params_ga_session_id ON session_params(ga_session_id);
CREATE INDEX idx_session_params_country ON session_params(country);
CREATE INDEX idx_session_params_device ON session_params(device);

-- account_session
CREATE TABLE account_session (
    account_id BIGINT NOT NULL,
    ga_session_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (account_id, ga_session_id)
);

ALTER TABLE account_session ADD CONSTRAINT fk_account_session_account
    FOREIGN KEY (account_id) REFERENCES account(id);

ALTER TABLE account_session ADD CONSTRAINT fk_account_session_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

CREATE INDEX idx_account_session_account_id ON account_session(account_id);
CREATE INDEX idx_account_session_ga_session_id ON account_session(ga_session_id);

-- ab_test
CREATE TABLE ab_test (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    test INTEGER,
    test_group INTEGER
);

ALTER TABLE ab_test ADD CONSTRAINT fk_ab_test_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

CREATE INDEX idx_ab_test_ga_session_id ON ab_test(ga_session_id);

-- event_params
CREATE TABLE event_params (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    event_date DATE,
    event_timestamp TIMESTAMP,
    event_name VARCHAR(100),
    event_params JSONB
);

ALTER TABLE event_params ADD CONSTRAINT fk_event_params_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

CREATE INDEX idx_event_params_ga_session_id ON event_params(ga_session_id);
CREATE INDEX idx_event_params_event_date ON event_params(event_date);

-- orders
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    item_id BIGINT NOT NULL
);

ALTER TABLE orders ADD CONSTRAINT fk_orders_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

ALTER TABLE orders ADD CONSTRAINT fk_orders_product
    FOREIGN KEY (item_id) REFERENCES products(item_id);

CREATE INDEX idx_orders_ga_session_id ON orders(ga_session_id);
CREATE INDEX idx_orders_item_id ON orders(item_id);

-- email_sent
CREATE TABLE email_sent (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    sent_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

ALTER TABLE email_sent ADD CONSTRAINT fk_email_sent_account
    FOREIGN KEY (id_account) REFERENCES account(id);

CREATE INDEX idx_email_sent_id_account ON email_sent(id_account);

-- email_open
CREATE TABLE email_open (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    open_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

ALTER TABLE email_open ADD CONSTRAINT fk_email_open_account
    FOREIGN KEY (id_account) REFERENCES account(id);

CREATE INDEX idx_email_open_id_account ON email_open(id_account);

-- email_visit
CREATE TABLE email_visit (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    visit_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

ALTER TABLE email_visit ADD CONSTRAINT fk_email_visit_account
    FOREIGN KEY (id_account) REFERENCES account(id);

CREATE INDEX idx_email_visit_id_account ON email_visit(id_account);

-- paid_search_cost
CREATE TABLE paid_search_cost (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    cost DECIMAL(12,2) NOT NULL
);

CREATE UNIQUE INDEX idx_paid_search_cost_date ON paid_search_cost(date);

-- revenue_predict
CREATE TABLE revenue_predict (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    predict DECIMAL(12,2) NOT NULL
);

CREATE UNIQUE INDEX idx_revenue_predict_date ON revenue_predict(date);
```

---

## 6. Java Entity (JPA)

### Product.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(nullable = false)
    private String name;

    private String category;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "short_description")
    private String shortDescription;

    public Product(Long itemId, String name, String category, BigDecimal price, String shortDescription) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.shortDescription = shortDescription;
    }
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
    @Column(name = "ga_session_id")
    private String gaSessionId;

    private String device;

    @Column(name = "mobile_model_name")
    private String mobileModelName;

    @Column(name = "operating_system")
    private String operatingSystem;

    @Column(length = 50)
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
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AbTestId implements Serializable {
    @Column(name = "ga_session_id")
    private String gaSessionId;

    private Integer test;
}

@Entity
@Table(name = "ab_test")
@Getter
@Setter
@NoArgsConstructor
public class AbTest {
    @EmbeddedId
    private AbTestId id;

    @Column(name = "test_group")
    private Integer testGroup;
}
```

### EventParams.java
```java
package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventParamsId implements Serializable {
    @Column(name = "ga_session_id")
    private String gaSessionId;

    @Column(name = "event_timestamp")
    private LocalDateTime eventTimestamp;
}

@Entity
@Table(name = "event_params")
@Getter
@Setter
@NoArgsConstructor
public class EventParams {
    @EmbeddedId
    private EventParamsId id;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_params", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
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
    private Long itemId;
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
    private LocalDate date;

    @Column(nullable = false)
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
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal predict;
}
```

---

## 7. Repositories

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

## 8. Execution Order

### Queue 1 (no dependencies)

1. products ✅
2. account ✅
3. session ✅

### Queue 2 (depends on Queue 1)

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

## Important

- Tables use plural names: `sessions`, `orders`, `products`, `accounts` (avoiding SQL keywords)
- Do NOT rename columns: `id_account`, `ga_session_id`, `item_id`
- `ga_session_id` — VARCHAR (matches BigQuery STRING)