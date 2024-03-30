# Transactions Routine

### Problem Statement
Each cardholder (customer) has an account with their data.
For each operation done by the customer a transaction is created and associated with their
respective account.
Each transaction has a specific type (normal purchase, withdrawal, credit voucher or
purchase with installments)
Transactions of type purchase and withdrawal are registered with negative amounts, while
transactions of credit voucher are registered with positive value.

## Endpoints
Create the endpoints below according to the use cases previously mentioned.

POST /accounts (create an account)
Request Body:
```json
{
"document_number": "12345678900"
}
```

GET /accounts/:accountId (retrieve the account information)
Response Body:
```json
{
"account_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
"document_number": "12345678900"
}
```

POST /transactions (create a transaction)
Request Body:
```json
{
  "account_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "type": 4,
  "amount": 123.45
}
```

### Requirements
* Docker
* Java17

### Run and clean

To Run The application please use below commands

#### Run The application
```agsl
chmod +x run.sh
./run.sh
```

#### Clean all created Resources
```agsl
chmod +x clean.sh
./clean.sh
```

### Swagger Documentation

* [Swagger Documentation](http://localhost:8080/swagger-ui.html)