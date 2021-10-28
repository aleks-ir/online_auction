# Online Auction test project

This is sample 'Online Auction' web application.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```

## Rest server

### Local tests with H2 in-memory

This starts Tomcat and serves up your rest-app project on http://localhost:8088:
```
java -jar rest-app/target/rest-app-1.0-SNAPSHOT.jar
```

This starts Tomcat and serves up your web-app project on http://localhost:8080.
```
java -jar web-app/target/web-app-1.0-SNAPSHOT.jar 
```

## Available REST endpoints

### Products

#### products-dto

```
curl --request GET 'http://localhost:8088/products-dto' | json_pp
```

#### findById

```
curl --request GET 'http://localhost:8088/products/1' | json_pp
```

#### create

```
curl --request POST 'http://localhost:8088/products' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
	"productName": "Oil", "productPrice": "2.34", "productDate": "2022-01-12", "salesmanId": "1"
}'
```

#### update price and customer

```
curl --request PUT 'http://localhost:8088/products' \
--header 'Content-Type: application/json' \
--data-raw '{
   "productId": 1,
   "productPrice": "30",
   "productCustomer": "1"
}'
```

#### delete

```
curl --request DELETE 'http://localhost:8088/products/8'
```



### Users

#### users-dto
```
curl --request GET 'http://localhost:8088/users-dto' | json_pp
```

#### findById

```
curl --request GET 'http://localhost:8088/users/1' | json_pp
```

#### update

```
curl --request PUT 'http://localhost:8088/users' \
--header 'Content-Type: application/json' \
--data-raw '{
   "userId": "1",
   "userName": "Dart",
   "userEmail": "dart@dr.com",
   "userMoney": "200"
}'
```


### Date

#### get

```
curl --request GET 'http://localhost:8088/date'
```

#### update

```
curl --request PUT 'http://localhost:8088/date' \
--header 'Content-Type: text/plain' -d "2022-01-25"
```