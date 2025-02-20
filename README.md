# Itemservice for found_it_backend

All micro services related to lost and found item

## Funcationalities
- Insert item
- Get Item
- Delete item
- Update item

### Pre requisites

- Oracle Java SDK 23
- Postman

This project defaults Postgre SQL in `superbasee` therefore no need to set up a db server, but always can change that in settings.

### Deployment steps

1. Clone the project
2. build the project - navigate to `build.gradle` and build using gradle plugin
3. Run the project using main file `FounditApplication`

### Endpoints

POST `/item/insertitems`
Inserts a new item.

GET `/item/getitems/{itemId}`
Retrieves an item by its ID.

PUT `/item/updateitem/{id}`
Updates an existing item.

DELETE `/item/deleteitem/{id}`
Deletes an item by its ID.

GET `/item/getimagebyid/{imageID}`
Retrieves an item image by its ID

If the backend is running locally server will be running in `localhost:8081` by default
