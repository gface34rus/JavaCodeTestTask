# JavaCodeTestTask

## Описание

JavaCodeTestTask — это REST API сервис на Spring Boot для управления кошельками. Сервис позволяет выполнять операции пополнения и снятия средств, а также получать текущий баланс кошелька. В качестве СУБД используется PostgreSQL, миграции управляются Liquibase. Для удобства запуска используется Docker Compose.

## Стек технологий
- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- PostgreSQL
- Liquibase
- Docker, Docker Compose
- Swagger (Springdoc OpenAPI)

## Быстрый старт

### Запуск через Docker Compose
1. Убедитесь, что установлены Docker и Docker Compose.
2. Скопируйте файл `my.env` в `.env` (или используйте как есть):
   ```
   DB_USERNAME=admin
   DB_PASSWORD=admin
   ```
3. Запустите сервис:
   ```sh
   docker-compose up --build
   ```
4. Приложение будет доступно на [http://localhost:8080](http://localhost:8080)

### Ручной запуск (без Docker)
1. Установите PostgreSQL и создайте базу данных `wallet_db` на порту 5433.
2. Установите переменные окружения или пропишите их в `src/main/resources/application.properties`:
   - `spring.datasource.url=jdbc:postgresql://localhost:5433/wallet_db`
   - `spring.datasource.username=admin`
   - `spring.datasource.password=admin`
3. Выполните миграции Liquibase (автоматически при запуске Spring Boot).
4. Соберите и запустите приложение:
   ```sh
   ./mvnw spring-boot:run
   ```

## Переменные окружения
- `DB_USERNAME` — имя пользователя БД (по умолчанию: admin)
- `DB_PASSWORD` — пароль пользователя БД (по умолчанию: admin)

## Структура БД
Создаётся таблица `wallets`:
- `wallet_id` (UUID, PK)
- `balance` (decimal(10,2))

## API

### 1. Получить баланс кошелька
- **GET** `/api/v1/wallet/{walletId}`
- **Ответ:** число (BigDecimal) — текущий баланс

### 2. Выполнить операцию (пополнение/снятие)
- **POST** `/api/v1/wallet`
- **Тело запроса (JSON):**
  ```json
  {
    "walletId": "UUID",
    "operationType": "DEPOSIT" | "WITHDRAW",
    "amount": 100.00
  }
  ```
- **Ответ:** 200 OK (без тела)
- **Ошибки:** 400 Bad Request (например, недостаточно средств)

## Swagger UI
Документация API доступна по адресу: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Лицензия
Проект предоставлен в учебных целях. 