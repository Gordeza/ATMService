# ATM Service

API for atm service that communicates with bank service. Bank service 
only accepts requests incoming from atm via token sent in header, stored in application.yml

## Endpoints
### GET - localhost:8080/validate
#### &emsp;&emsp; description - sends request to bank service and returns response about card validation
#### &emsp;&emsp; requested parameters - cardNumber

### POST - localhost:8080/auth
#### &emsp;&emsp; description - sends request to bank service and upon successful authentication returns information .
#### &emsp;&emsp; requested parameters - cardNumber, pin
&emsp;&emsp; return type example
```JSON
{
  "token": "JWT_token",
  "type": "Bearer",
  "id": 1,
  "cardNumber": "1234123412341234"
}
```
### POST - localhost:8080/deposit
#### &emsp;&emsp; description - sends request to bank service and to make a deposit on account from the card. JWT token also should be present in header for successful authentication, otherwise request will be rejected.
#### &emsp;&emsp; requested parameters - amount
#### &emsp;&emsp; header parameters - {Authorization : "Bearer JWT_token"}

### POST - localhost:8080/withdraw
#### &emsp;&emsp; description - sends request to bank service and to withdraw money from account via the card. JWT token also should be present in header for successful authentication, otherwise request will be rejected.
#### &emsp;&emsp; requested parameters - amount
#### &emsp;&emsp; header parameters - {Authorization : "Bearer JWT_token"}

### GET - localhost:8080/withdraw
#### &emsp;&emsp; description - sends request to bank service and check account balance. JWT token also should be present in header for successful authentication, otherwise request will be rejected.
#### &emsp;&emsp; header parameters - {Authorization : "Bearer JWT_token"}
