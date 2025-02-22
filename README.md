
# DummyStore
Simple product catalog Android app, which uses dummyjson.com

# Features

- Catalog
- Search by product name

- View product details

# Dependencies

- Jetpack Compose for UI
- Coil for images
- Hilt for DI
- Navigation Compose for navigation
- Retrofit to receive data from [dummyjson.com](https://dummyjson.com/products)
- Paging 3 to optimize products loading
- Room for ofline caching

# Screenshots

<img src="https://i.imgur.com/ixm0GQs.png" alt="Catalog" width="25%" height="25%"> <img src="https://i.imgur.com/tE8fDfY.png" alt="Search" width="25%" height="25%"> <img src="https://i.imgur.com/H0gX3B9.png" alt="Search" width="50%" height="50%"> 





## API Reference

#### Get all Products

```http
  'https://dummyjson.com/products'
```



#### Get Product with Id

```http
  GET 'https://dummyjson.com/products/1'
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |




## Secure Data Handling 
Chosen Encryption Method:
## AES-256 (Android Keystore)
We use AES-256 with CBC mode for encrypting sensitive data at rest.
Why?
- Industry-standard for secure encryption
- Fast and efficient on mobile devices
- Ensures confidentiality by preventing unauthorized access

## How We Store Encrypted Data

- The Android Keystore System is used to generate and store the AES key securely.
- The encryption key never leaves the Keystore, ensuring it cannot be extracted.

- Encrypted data is stored in Room Database or SharedPreferences, depending on use case.
## Data Decrption

When the encrypted data is received by the backend, the server follows these steps to decrypt it:

- Decode the Base64-encoded data received in the request.
- Extract the IV (Initialization Vector) from the first 16 bytes of the decoded data.
- Use AES-256 in CBC mode with the securely stored encryption key to decrypt the remaining content.
-  Remove padding from the decrypted data to get the original plaintext, the keuy used to encrpt must be used to decrypt
## How to run the app
- Clone the repository and open it in Android Studio using this link  https://github.com/peter6053/products.git
- Connect a device or start an emulator.
- Click Run to build and launch the app