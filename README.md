# Bankrupt Bank

Models a bank. This is a project made for learning and trying out new things with a concrete use case (a bank in this case). This bank is bankrupt because of aforementioned reasons. 🤷‍♂️.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. Being a hobby project, this cannot be deployed on a live system for reasons relating to security, quality and scalability.

### Prerequisites

This project uses a multi-module Gradle build. Spring Tools is recommended to be installed in your IDE. Docker compose is required to fire up other infrastructure like MySQL, Kafka, etc.

### Installing

To get the standalone Spring JARs, simply run the plain old Gradle build command.

```sh
gradle build
```

Running this project involves using Spring Tools in your IDE to fire up application services (`dev` profile) and Docker for MySQL and Kafka other services.

```sh
docker-compose up
```

After MySQL is up and running, get its IP by running the following command:

```sh
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' bankrupt-bank_db_1
```

Next is to initialize the database wih some dummy data. Use the IP address to connect to the MySQL database from your favourite client, or just use Adminer web interface which is fired up along with MySQL at `http://localhost:18080`

Provide the SQL script in [here](sql-init.sql) to setup the database for testing.

Fire up your favourite REST client and try out a payment. The payment ID should be picked up by the payment worker process from Kafka topic and displayed in the IDE's output console.

`POST http://localhost:58080/bankrupt/payments`

```json
{
  "amount": 10,
  "creditorName": "Beneficiary",
  "debtorIban": "NL80ABNA0419499482",
  "creditorIban": "NL01ABNA0123456789"
}
```

## Running the tests

```sh
gradle test
```

## Built With

* [Spring](https://spring.io) - The web framework used
* [Maven](https://gradle.org) - Dependency Management
* [MySQL](https://www.mysql.com) - Relational Database
* [Kafka](https://kafka.apache.org) - Message Broker

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/vivek6522/bankrupt-bank/tags).

## Authors

* **Vivek Prajapati** - *Initial work* - [vivek6522](https://github.com/vivek6522)

See also the list of [contributors](https://github.com/vivek6522/bankrupt-bank/graphs/contributors) who participated in this project.

## License

This project is licensed under the Unlicense License - see the [LICENSE.md](LICENSE.md) file for details
