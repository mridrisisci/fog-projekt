Dette er et semesterprojekt for 2. semester datamatiker på Copenhagen Business Academy Lyngby.

Du finder rapporten for projektet i /report/report.pdf
Video af systemet i aktion: 

Vi er hold Dat2F24 gruppe E.

## Installation
For at køre dette projekt skal du have en Postgres Database, og du skal angive url, port og brugerinformationer i Main klassen.
Der ligger et sql script i /src/main/resources/sql som skal køres for at oprette de nødvendige tabeller og konti. 
Scriptet opretter to konti:

####  1. Sælgerkonto
Brugernavn: Martin, mail: sales.person.fog@gmail.com med koden: 1234
####  2. Admin konto
Brugernavn: admin, mail: admin@cph.dk med koden: 1234

## Techstak
* Java 22: Hovedsprog for applikationens backend.
* Javalin (version 6.3.0): Webframework til at håndtere HTTP-forespørgsler og responses.
* Thymeleaf (version 3.0.15): Template engine til at generere dynamiske HTML-sider.
* PostgreSQL (version 14): Database, der håndterer lagring af data om kunder, ordrer og produkter.
* JDBC: Anvendes til at etablere forbindelse mellem Java-applikationen og PostgreSQL-databasen.
* HTML5 og CSS3: Bruges til at designe og strukturere webapplikationens brugergrænseflade.
* Bootstrap (version 5.3.3): Responsivt CSS (og JavaScript) framework, som giver en grundlæggende styling og funktionalitet der kan forventes af et moderne website.
* IntelliJ IDEA 2024.2.4 (Ultimate): IDE til udvikling og debugging af projektet.
* SendGrid Version 4.10.1
* SVG 
* JDK Amazon Corretto 22 (version 22.0.2)
* Build tool Maven (compiler version 3.13.0)


