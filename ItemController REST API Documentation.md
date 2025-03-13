# ItemController REST API Documentation

## Overview
The `ItemController` class provides RESTful endpoints for managing items and their associated images within the `FoundIt` application. This document outlines the available endpoints, their descriptions, and usage.

---

## Endpoints

### 1. Insert Item

**Endpoint:** `POST /item/insertitems`

**Description:**
Inserts a new item into the system.

**Request Body:**
```json
{
  "itemName": "Backpack",
  "description": "Brown backpack with laptop and books",
  "categoryId": 1,
  "locationFound": "Mall",
  "dateTimeFound": "2025-02-15T10:00:00",
  "reportedBy": "Johnson Johnson",
  "contactInfo": "Johnson.johnson@email.com",
  "status": "LOST",
  "images": [
    {
      "description": "Image of the lost brown backpack",
      "image": "data:image/jpeg;base64",
      "locationFound": "Mall",
      "dateTime": "10:00:00",
      "status": "LOST"
    }
  ]
}
```

**Response:**
- `200 OK`: Returns the saved item.

---

### 2. Get Item by ID

**Endpoint:** `GET /item/getitems/{itemId}`

**Description:**
Fetches an item by its ID.

**Path Parameter:**
- `itemId` (int): ID of the item to retrieve.

**Response:**
- `200 OK`: Returns the item.
- `404 NOT FOUND`: If the item does not exist.

---

### 3. Get Item Image by ID

**Endpoint:** `GET /item/getitemimages/{imageId}`

**Description:**
Fetches an image associated with an item by image ID.

**Path Parameter:**
- `imageId` (int): ID of the image to retrieve.

**Response:**
- `200 OK`: Returns the item's associated image.
- `404 NOT FOUND`: If the image does not exist.

---

### 4. Update Item

**Endpoint:** `PUT /item/updateitem/{id}`

**Description:**
Updates an existing item.

**Path Parameter:**
- `id` (int): ID of the item to update.

**Request Body:**
```json
{
  "name": "string",
  "description": "string",
  "location": "string"
}
```

**Response:**
- `200 OK`: Returns the updated item.
- `404 NOT FOUND`: If the item does not exist.

---

### 5. Delete Item

**Endpoint:** `DELETE /item/deleteitem/{id}`

**Description:**
Deletes an item by ID.

**Path Parameter:**
- `id` (int): ID of the item to delete.

**Response:**
- `200 OK`: Item successfully deleted.
- `404 NOT FOUND`: If the item does not exist.

---

### 6. Get Items by User

**Endpoint:** `GET /item/getitemsbyuser/{userId}`

**Description:**
Retrieves items associated with a specific user.

**Path Parameter:**
- `userId` (int): ID of the user.

**Response:**
- `200 OK`: Returns the user's items and placeholder user details.
- `404 NOT FOUND`: If no items are found for the user.

---

### 7. Search Items

**Endpoint:** `GET /item/search`

**Description:**
Searches for items based on optional parameters.

**Query Parameters:**
- `itemName` (string, optional): Name of the item.
- `locationFound` (string, optional): Location where the item was found.
- `description` (string, optional): Description of the item.

**Response:**
- `200 OK`: Returns a list of items matching the search criteria.

---

### 8. Get Token

**Endpoint:** `GET /item/getToken`

**Description:**
Placeholder endpoint for retrieving and validating a user token. Currently not implemented.

**Path Parameters:**
- `userId` (int): ID of the user.
- `token` (string): User's token.

**Response:**
- `501 Not Implemented`: Placeholder endpoint.

---

## Error Handling
- `404 NOT FOUND`: Returned when an item or image is not found.
- `500 INTERNAL SERVER ERROR`: Returned for unexpected server errors.

---

## Future Improvements
- Implement the `/item/getToken` endpoint.
- Improve search feature
- Complete user details retrieval in `getitemsbyuser`.
- Improve exception handling for better error reporting.

## Diagram View
![item service diagram](https://github.com/user-attachments/assets/ed541d84-562d-4ef2-b631-a94c55ea3c4e)

