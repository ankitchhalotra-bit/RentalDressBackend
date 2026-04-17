# Frontend Development Guide - Custom Dress Microservice

This document outlines the API specifications, data models, and necessary features required to build the frontend for the Custom Dress Management System. You can use this guide as a direct context to build the UI with your preferred framework (e.g., React, Next.js, Vue, Angular).

## Authentication & Authorization
- **Type**: JWT (JSON Web Token)
- **Header**: All protected routes require the header `Authorization: Bearer <your_jwt_token>`
- **Roles**: Based on `role` field in the User object (`ROLE_USER` or `ROLE_ADMIN`). The UI should have different views for Admin and User.

---

## Data Models

### 1. User
```typescript
interface User {
  id?: string;
  name: string;      // Required
  email: string;     // Required, Email format
  password?: string; // Required for registration
  role: string;      // 'ROLE_USER' or 'ROLE_ADMIN'
}
```

### 2. Dress (AddDressInfoDTO)
```typescript
interface Dress {
  id: string;          // MongoDB generic ID
  dressID: string;     // UUID assigned by system
  name: string;        
  type: string;        
  price: number;
  description: string;
  file: string;        // URL string (hosted on Cloudinary)
  publicId: string;    // Cloudinary public ID
  favorite: boolean;
  addCart: boolean;
}
```

### 3. Category
```typescript
interface Category {
  id?: string;
  name: string;
  description: string;
}
```

### 4. Cart
```typescript
interface Cart {
  id: string;
  userId: string;
  dressIds: string[]; // Array of strings (Dress IDs)
}
```

---

## API Endpoints

### 1. Authentication (`/api/auth`)

#### Register User
- **Endpoint**: `POST /api/auth/register`
- **Request Body**: `User` object
- **Response**: `{ "message": "User registered successfully" }`

#### Login User
- **Endpoint**: `POST /api/auth/login`
- **Request Body**: `{ "email": "user@example.com", "password": "password123" }`
- **Response**: `{ "token": "<jwt_string>" }`
- **Note**: The API only returns the token. The frontend should decode the JWT (or manage app state) to extract the email and identify the user.

---

### 2. User Shopping Cart (`/api/cart`)

#### Get Cart
- **Endpoint**: `GET /api/cart?userId={userId}`
- **Response**: `Cart` object

#### Add Dress to Cart
- **Endpoint**: `POST /api/cart/add?userId={userId}&dressId={dressId}`
- **Response**: Updated `Cart` object

#### Remove Dress from Cart
- **Endpoint**: `DELETE /api/cart/remove/{dressId}?userId={userId}`
- **Response**: Updated `Cart` object

---

### 3. Categories (`/api/categories`)
Fully RESTful endpoints.

- **Get All**: `GET /api/categories` -> Returns `Category[]`
- **Get One**: `GET /api/categories/{id}` -> Returns `Category`
- **Create**: `POST /api/categories` (Body: `Category`) -> Returns created `Category`
- **Update**: `PUT /api/categories/{id}` (Body: `Category`) -> Returns updated `Category`
- **Delete**: `DELETE /api/categories/{id}` -> Returns literal string message `"Category deleted successfully"`

---

### 4. Admin - Manage Dresses 
*Note: Make sure to check the paths as they vary slightly.*

#### Add a Dress (Multipart File Upload)
- **Endpoint**: `POST /api/admin/dress`
- **Headers**: `Content-Type: multipart/form-data`
- **Form Data Fields**:
  - `name` (String)
  - `type` (String)
  - `price` (Double/Number)
  - `description` (String)
  - `file` (File Blob)
- **Response**: 
```json
{
  "message": "Dress added successfully",
  "data": { /* Dress Object */ }
}
```

#### Update a Dress
- **Endpoint**: `PUT /admin/api/update/{id}` (note the `/admin/api` path)
- **Headers**: `Content-Type: multipart/form-data`
- **Form Data Fields**: Send any updated text fields (`name`, `type`, `price`, `description`) PLUS an optional `file` part to update the image.
- **Response**: Updated `Dress` object

#### Delete a Dress
- **Endpoint**: `DELETE /admin/api/delete/{id}` 
- **Response**: String message `"Dress deleted successfully"`

---

## Recommended Frontend Architecture / Pages to Build

**1. Public Pages:**
- **Home/Landing Page**: Showcasing categories and featured dresses.
- **Login / Register Page**: Forms consuming the `/api/auth` endpoints.

**2. User Protected Pages (Requires `ROLE_USER`):**
- **Catalog / Shop Page**: Viewing all dresses. Let the user filter by Category.
- **Cart Page**: Retrieve user's cart by `userId`. Show items mapped to Dress details. Consume `/api/cart/remove` for deletion.

**3. Admin Protected Pages (Requires `ROLE_ADMIN`):**
- **Admin Dashboard**: Navigation hub.
- **Manage Categories**: CRUD table for Categories (`/api/categories`).
- **Manage Dresses**: 
  - Table of all dresses.
  - Form to Create Dress (must support image file payload).
  - Form to Edit Dress.
  - Delete functionality.

## Important Note to the AI Generating the Frontend
- **File Uploads**: Admin dress creation and update endpoints expect `multipart/form-data`, **NOT** `application/json`.
- **Axios / Fetch Config**: Automatically inject the `Authorization: Bearer <token>` into outgoing requests to protected routes.
- **Environment**: Keep the backend API Base URL dynamic via an environment variable (e.g., `VITE_API_BASE_URL` or `NEXT_PUBLIC_API_URL`) currently defaulting to `http://localhost:8080`.
