What is this?
This is site to observe banners.
Admin is create banners and visitors observe them.


Which params current application use?
JDBC driver: MySQL
Host: localhost
Port: 3306
database: banners
user: root
pass: root
also see src\main\resources\application.properties


in MySQL console:
create database banners;

if problem "Time Zone Problems" in MySQL
SET GLOBAL time_zone = '+8:00';


for visitor:
http://host:port/bid?category=<REQ_NAME>

for admin:
login u
password 1
http://localhost:8080/login
http://localhost:8080/banner
http://localhost:8080/category
