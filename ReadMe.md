What is this?
- This is site to observe banners. Admin is create banners and visitors observe them.

Which params current application use?
- JDBC driver: MySQL
- Host: localhost
- Port: 3306
- database: banners
- user: root
- pass: root
- also see src\main\resources\application.properties

How to start?

1 start database in MySQL console:
- create database banners;

if problem "Time Zone Problems" in MySQL console:
- SET GLOBAL time_zone = '+8:00';

2 in project folder open console and input:
- mvn package
- cd target
- java -jar bannerviewer-1.0-SNAPSHOT.jar

How to use?
0 Use Google Chrome only. No Internet Explorer

1 for visitor:
- http://host:port/bid?category=<REQ_NAME>

2 for admin:
- login: u password: secret123
- http://localhost:8080/login
- http://localhost:8080/banner
- http://localhost:8080/category
