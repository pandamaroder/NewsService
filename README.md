# О проекте:

MVC сервис для приложения «Контакты 2.0»

**Технологии:** Java, Spring Boot, Postgres

# Первичная установка

1. Скачать проект с гитхаба https://github.com/<>
2. Обновить gradle зависимости
3. Добавить в конфигурацию идеи environment variables DB_PORT=;DB_HOST=localhost;DB_USER=postgres
4. Установить докер(десктоп версию под Win or Mac)
5. запустить локальный файл компоуз для поднятия бд либо через idea либо в терминале `docker-compose -f docker-compose-env-only.yml up`
6. запустить Application
7. Прогнать скрипт в бд R__init_users.sql 


#Тестирование эндпоинтов:

IN_PROGRESS

# Локальный запуск:

##  окружение в докере

1. Выполнить в корне 
```shell
docker-compose -f docker-compose-env-only.yml up
```

2. Установить переменные окружения (env vars): `DB_PORT=5400;DB_HOST=localhost;DB_USER=postgres;DB_PASS=123;DB_NAME=FMH_DB;SWAGGER_HOST=localhost:8080;DOCUMENTS_STATIC_PATH=/var;STATIC_HOST=test.vhospice.org;APP_MAIL_USERNAME=test@vhospice.org;APP_MAIL_PASSWORD=G3ttdHGrgjqtfjkjpHeF`
3. Запустить backend сервис
