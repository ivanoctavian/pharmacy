
# Pharmacy - inventory management tool

SpringBoot RESTful api that supports CRUD operations on a database in order to manage items in a pharmacy inventory.



## API Reference
Below you cand find the authorization and the some of the available endpoints.

### Authorization
All requests require username and password using BasicAuth method.

### Responses
```
{
    "timeStamp": "2024-12-16 22:48:51",
    "status": "NOT_FOUND,
    "message": "No medicines found."
    "data": Obj type.
}
```
#### Statuses


| status | Description                |
| :-------- | :------------------------- |
| `SUCCESS`| All good|
| `FAIL`| Request ok, but something went wrong.|
| `ERROR`| Exception encountered.|
| `NOT_FOUND`| Data not found.|

#### 1. Medicines

```http
  GET /medicines
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `medicineId` | `Long` | **Optional**. If not present, return all medicines|


#### 2. Medicines - search by substance
```http
  GET /medicines/by-substance
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `substanceName` | `String` | **Required**.|

#### 3. Medicines Increase stock

```http
  POST /medicines/increase-stock
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `medicineId`      | `Long` | **Required**. |
| `increaseBy`      | `Long` | **Required**. |

#### 4. Medicines - Decrease stock
```http
  POST /medicines/decrease-stock
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `medicineId`      | `Long` | **Required**. |
| `decreaseBy`      | `Long` | **Required**. |

#### 5. Medicines - change price
```http
  POST /medicines/change-price
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `medicineId`      | `Long` | **Required**. |
| `newPrice`      | `Long` | **Required**. |

#### 6. Medicines - add new medicine
```http
  POST /medicines/change-price
```
RequestBody:
```
{
  "name": "aspirin", 
  "expirationDate": "2024-12-31",
  "stock": 23,
  "supplierId": 1388,
  "categoryId": 1,
  "producer": "Local producer SRL",
  "price": 294.99
}
```




