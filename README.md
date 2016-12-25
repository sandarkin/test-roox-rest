# test-roox-rest

### Исходное задание
Исходное задание расположено в файле [ASSIGNMENT.md](ASSIGNMENT.md)

### Дисклаймер
Немного изменил (сделал поинтереснее) условия задачи, а именно
 - введены роли пользователей – USER и ADMIN
 - в приложение внедрена автогенерируемая документация javadoc ([http://localhost:8080/javadoc/index.html](http://localhost:8080/javadoc/index.html))
 - в приложение внедрена автогенерируемая документация rest ([http://localhost:8080/restdoc.html](http://localhost:8080/restdoc.html))
 - также для удобства работы со встроенной базой даннх включена h2console ([http://localhost:8080/h2console](http://localhost:8080/h2console))  
 jdbc url = jdbc:h2:mem:testdb, username = sa, password пустой
 
### Предварительные требования
Java 8 и Gradle 3

## Функциональная картина
Приложение позволяет производить следующие операции над сущностью Customer:
 - Для роли роли USER
  - Просматривать список кастомеров (для этой роли он будет содержать только один элемент)
  - Просмотривать своего каcтомера по id
  - Просматривать своего кастомера по шоткату `@me`
 - Для роли роли ADMIN
  - Просматривать список всех кастомеров
  - Просмотривать любого каcтомера по id
  - Просматривать своего кастомера по шоткату `@me`

Приложение позволяет производить следующие операции над сущностью PartnerMapping:
 - Для роли роли USER
  - Просматривать список маппингов своего кастомера
  - Просматривать список маппингов (в списке буду только маппинги своего кастомера)
  - Создавать маппинг для своего кастомера
  - Изменять маппинг своего кастомера
  - Удалять маппинг своего кастомера
 
 - Для роли роли ADMIN
  - Просматривать список маппингов любого кастомера
  - Просматривать список всех маппингов
  - Создавать маппинг для любого кастомера
  - Изменять маппинг любого кастомера
  - Удалять маппинг любого кастомера

## Авторизация
Для авторизации приложение использует HTTP заголовок `Authorization`, значение которого имеет формат  
`Bearer somestring`, где `somestring` – представляет собой JWT токен, подписанный HS256 ключом `xCaLzuKMHUGy3ZkQxnVqhj8LWkxDg3qq`.

## Акторы
На момент старта в приложении имеются 3 пользователя – 2 с рольлю USER и 1 с ролью ADMIN. Для авторизации под этими пользователями нужно использовать следующие JWT токены.

|Username|Roles|JWT Token|
|---|---|---|
|kevin|USER|`eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5YmE1YzdiYi04MDQwLTQ0NTAtYTJmZS1lNjc3ZWI4MmZjNjMiLCJ1c2VybmFtZSI6ImtldmluIiwicm9sZXMiOlsidXNlciJdfQ.76ATYe4azE0Mle0Lx-5A2GHQxy4L2ctRfa1v-FJrfho`|
|fuller|USER|`eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJmNTU5M2QyMi0xZmM3LTQ2MjItOTI1Ny1kMTViMjYxZjE1YTkiLCJ1c2VybmFtZSI6ImZ1bGxlciIsInJvbGVzIjpbInVzZXIiXX0.ITAXjAv1VgXKCar8V7d5wVn0YPqLF7Fg6ErxHB2--hw`|
|marley|ADMIN|`eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNWQ2YmNlYy0yZWU0LTQ1NjUtOTc5Ny01N2YxMmM4OWE5NzMiLCJ1c2VybmFtZSI6Im1hcmxleSIsInJvbGVzIjpbInVzZXIiLCJhZG1pbiJdfQ.huE3ia0JsxdlwTMR57oPPrONXklAMBlP19mt4B7_KSI`|

## Как запустить
### Из дистрибутива
1. Скачать релиз [roox-1.0.0-RELEASE.jar](https://github.com/sandarkin/test-roox-rest/releases/download/1.0.0-RELEASE/roox-1.0.0-RELEASE.jar)
2. Выполнить `java -jar roox-1.0.0-RELEASE.jar`

### Из исходного кода
1. Склонировать репозиторий `git clone https://github.com/sandarkin/test-roox-rest.git`
2. Перейти в проектную директорию `cd test-roox-rest`
3. Выполнить `gradle bootRun`

## Как пользоваться
Основные операции REST API приложения
 - Просмотр (доступных) кастомеров  
 `GET http://localhost:8080/customers`
 - Просмотр (доступного) кастомера  
 `GET http://localhost:8080/customers/$uuid`
 - Просмотр собственного кастомера  
 `GET http://localhost:8080/customers/@me`
 - Просмотр маппингов конкретного катомера  
 `GET http://localhost:8080/customers/$uuid/partnerMappings`
 - Просмотр (доступных) маппингов  
 `GET http://localhost:8080/partnerMappings`
 - Просмотр (доступного) маппинга  
 `GET http://localhost:8080/partnerMappings/$uuid`
 - Создание нового маппинга  
 `POST http://localhost:8080/partnerMappings`
 - Изменение существующего маппинга  
 `PUT http://localhost:8080/partnerMappings/$uuid`
 - Удаление существующего маппинга  
 `DELETE http://localhost:8080/partnerMappings/$uuid`

Более подробно о существующих эндпоинтах, форматах запросов и ответов можно посмотреть в самом приложении по адресу [http://localhost:8080/restdoc.html](http://localhost:8080/restdoc.html)

Созданные REST-операции соответствую ролевой модели в той части, кто и что может смотреть, создавать, менять и удалять.



