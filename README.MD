# Spring Boot application to handle bank transactions

### Tech stack
* Spring Boot 2.3.1, Maven, Docker, Swagger
* Arquitecture: DDD and hexagonal arquitecture
* Code language: Java 1.8
* Testing: Junit5 & Mockito, Cucumber
* Database: Spring Data JPA, PostgreSQL, Flyway

### Important information before run the application
1. **The database model has two tables: Account and Transaction.** 

2. **The service has 4 endpoints:**
* **Create account (/account)**-> If the iban account provided is already stored in the system, an exception will be thrown.

* **Create transaction (/transaction)**-> the date in the payload is provided with the following format *yyyy-MM-dd'T'HH:mm:ss.SSS'Z'*. If the iban account provided is related with an account that is not stored in the system, an exception will be thrown. If transaction leaves the total account balance below 0, it is not allowed and an exception will be thrown.

* **Get transaction (/transaction)**-> Possibility to get all transactions from database (/transaction), all transactions from database sorting ascending or descending (/transaction?sorting=descending), all transactions from database for specific iban account (/transaction?accountIban=ES3930294948393) or all transactions from database for specific iban account sorting ascending or descending (/transaction?accountIban=ES3930294948393&sorting=descending)

* **Search transaction status (/transaction-status)**-> Possibility to get transaction status from database searching by reference and channel (/transaction-status?reference=85b37cac-e8c2-46f4-8c77-e11f0cff16b1&channel=CLIENT). If the reference of the transaction is not stored in the system, and exception will be thrown. If the channel provided is not any of the values CLIENT, ATM or INTERNAL, an exception will be thrown. If the channel is not provided, the response will depend on the date of the transaction:
    - Transaction of today: status PENDING, and the amount and the fee.
    - Transaction before today: status SETTLED, and the amount and the fee.
    - Transaction after today: status FUTURE, and the amount and the fee.

### How to run the microservice:
1. **Build the app**
```
mvn clean install -Dmaven.test.skip=true
```

2. **Run docker-compose to start PostgreSQL and the Spring Boot application**
```
docker-compose up
```

### Where can I see information about the microservice?:

In the following URL: http://localhost:8080/swagger-ui.html


### How to run tests
```
mvn test
```

### How to run Cucumber tests
```
mvn test -Dtest=CucumberTestsRunner
```

### Example to test the application:
1. **Open Swagger**

2. **POST call to endpoint http://localhost:8080/account with the following body:**

{
    "iban": "ES3930294948393",
    "balance": 4035.80 
}

**Note:** iban and balance are mandatories.

3. **POST call to endpoint http://localhost:8080/transaction with the following body:**

{
    "reference": "85b37cac-e8c2-46f4-8c77-e11f0cff16b1",
    "account_iban": "ES3930294948393",
    "date": "2019-07-16T16:55:42.000Z",
    "amount": 42.33,
    "fee": 3.07,
    "description": "Payment in restaurant" 
}

**Note:** account_iban and amount are mandatories.

4. **GET call to endpoint http://localhost:8080/transaction?accountIban=ES3930294948393&sorting=descending**

**Note:** request parameters accountIban and sorting are optionals.

5. **GET call to endpoint http://localhost:8080/transaction-status?reference=85b37cac-e8c2-46f4-8c77-e11f0cff16b1&channel=CLIENT:**

**Note:** reference is mandatory.
