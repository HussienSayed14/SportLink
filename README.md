# SportLink, ##Football & Padel Field Booking Application



## **Project Overview**

This is a comprehensive **Football and Padel Field Booking Application** designed to streamline the process of booking fields and managing reservations. The platform caters to both players and field owners, offering a seamless user experience for booking, payments, and tracking available fields and pitches.

The backend is written in **Java**, utilizing robust frameworks like **Spring Boot** for RESTful APIs and **Hibernate** for ORM to ensure efficient data handling and scalability. The database is built to handle user authentication, bookings, payments, offers, and promo codes, with secure verification mechanisms such as phone or email OTP verification for user registration.

### **Key Features:**
- **User Registration and Authentication**:
  - Players and field owners can register on the platform and manage their profiles.
  - Secure authentication, password hashing, and failed login tracking.
  - OTP verification via phone or email during registration.

- **Field and Pitch Management**:
  - Field owners can register football or padel fields, define availability, and set prices.
  - Each field can have multiple pitches with configurable options like surface type, player capacity, and pitch type.

- **Dynamic Booking System**:
  - Users can view available fields and pitches in real-time and book them for specific hourly slots.
  - Bookings include start and end times, price calculations, and automatic status updates (`Booked`, `Available`, `Blocked`).

- **Promo Code and Offer System**:
  - Users can apply promo codes for discounts on bookings.
  - Field owners can create special offers on fields or pitches with configurable discount types (percentage or flat rate).

- **Secure Payments**:
  - Integrated payment system where users can pay for bookings using various methods like credit cards or cash.
  - Payments are tracked and linked to bookings for easy management.

- **User Verification**:
  - OTP-based user verification for secure registration.
  - Failed login attempts are tracked and can lead to account blocking after multiple unsuccessful tries.

### **Technology Stack**:
- **Backend**:
  - **Java**: Core language for the backend.
  - **Spring Boot**: Used for building RESTful APIs.
  - **Hibernate**: ORM framework for database operations.
  - **Spring Security**: For user authentication and authorization.

- **Database**:
  - **MySQL/PostgreSQL**: Relational database for managing user data, bookings, and payments.
  - Structured with tables for Users, Fields, Pitches, Bookings, Payments, Offers, and Promo Codes.

- **Frontend (optional)**:
  - **React or Angular**: Could be used to create a responsive user interface for users to interact with the platform (to be implemented).








## Database Schema

This schema includes tables for user registration, verification, bookings, payments, and offer management.

---

## Table: `User`

| Column          | Data Type       | Constraints         | Description                                          |
|-----------------|-----------------|---------------------|------------------------------------------------------|
| `user_id`       | `INT`           | Primary Key (PK)    | Unique identifier for the user.                      |
| `name`          | `VARCHAR(255)`  | NOT NULL            | Full name of the user.                               |
| `email`         | `VARCHAR(255)`  | NOT NULL, UNIQUE    | Email address of the user.                           |
| `phone_number`  | `VARCHAR(20)`   | NOT NULL, UNIQUE    | Contact number of the user.                          |
| `role`          | `VARCHAR(50)`   | NOT NULL            | Role of the user (e.g., 'Player', 'Field Owner').    |
| `password_hash` | `VARCHAR(255)`  | NOT NULL            | Hashed password for security.                        |
| `failed_attempts`| `INT`          | DEFAULT 0           | Count of failed login attempts.                      |
| `is_blocked`    | `BOOLEAN`       | DEFAULT FALSE       | `TRUE` if the user is blocked.                       |
| `is_activated`  | `BOOLEAN`       | DEFAULT FALSE       | `TRUE` if the user is activated.                     |
| `created_at`    | `TIMESTAMP`     | DEFAULT CURRENT_TIMESTAMP | When the user was registered.                 |
| `last_login`    | `TIMESTAMP`     |                     | Timestamp of the user's last login.                  |

---

## Table: `UserVerification`

| Column              | Data Type      | Constraints       | Description                                             |
|---------------------|----------------|-------------------|---------------------------------------------------------|
| `verification_id`    | `INT`          | Primary Key (PK)  | Unique identifier for each verification attempt.         |
| `user_id`           | `INT`          | Foreign Key (FK)  | References the user attempting verification.             |
| `verification_code`  | `VARCHAR(10)`  | NOT NULL          | Code sent to the user for verification.                  |
| `code_type`         | `ENUM('Phone', 'Email')` | NOT NULL | Type of verification (Phone/Email).                     |
| `expires_at`        | `TIMESTAMP`    | NOT NULL          | Time when the verification code expires.                 |
| `is_verified`       | `BOOLEAN`      | DEFAULT FALSE     | `TRUE` if verified.                                     |
| `created_at`        | `TIMESTAMP`    | DEFAULT CURRENT_TIMESTAMP | Time when verification entry was created.        |
| `attempt_count`     | `INT`          | DEFAULT 0         | Number of verification attempts.                        |

---

## Table: `LoginAudit`

| Column             | Data Type       | Constraints          | Description                                                 |
|--------------------|-----------------|----------------------|-------------------------------------------------------------|
| `login_audit_id`    | `INT`           | Primary Key (PK)     | Unique identifier for each login audit.                      |
| `user_id`          | `INT`           | Foreign Key (FK)     | References the user who logged in.                           |
| `session_id`       | `VARCHAR(255)`  | NOT NULL             | Unique session identifier for the login session.             |
| `ip_address`       | `VARCHAR(45)`   | NOT NULL             | The IP address from which the user logged in.                |
| `login_time`       | `TIMESTAMP`     | DEFAULT CURRENT_TIMESTAMP | Timestamp of the login.                                   |
| `logout_time`      | `TIMESTAMP`     |                      | Timestamp of the logout.                                     |

---

## Table: `Field`

| Column              | Data Type               | Constraints           | Description                                                 |
|---------------------|-------------------------|-----------------------|-------------------------------------------------------------|
| `field_id`          | `INT`                   | Primary Key (PK)      | Unique identifier for each field.                            |
| `field_owner_id`    | `INT`                   | Foreign Key (FK)      | References the field owner (User).                           |
| `field_name`        | `VARCHAR(255)`          | NOT NULL              | Name of the field.                                           |
| `location`          | `VARCHAR(255)`          | NOT NULL              | Physical location of the field.                              |
| `field_type`        | `ENUM('FOOTBALL', 'PADEL', 'BOTH')` | NOT NULL | Type of field available.                                     |
| `default_hour_price`| `DECIMAL(10,2)`         | NOT NULL              | Standard hourly price for bookings.                          |
| `is_active`         | `BOOLEAN`               | DEFAULT TRUE          | `TRUE` if the field is active.                               |
| `is_blocked`        | `BOOLEAN`               | DEFAULT FALSE         | `TRUE` if the field is blocked.                              |

---

## Table: `Pitch`

| Column               | Data Type              | Constraints          | Description                                                    |
|----------------------|------------------------|----------------------|----------------------------------------------------------------|
| `pitch_id`           | `INT`                  | Primary Key (PK)     | Unique identifier for each pitch.                               |
| `field_id`           | `INT`                  | Foreign Key (FK)     | References the field this pitch belongs to.                     |
| `pitch_number`       | `INT`                  | NOT NULL             | Unique number for each pitch within a field.                    |
| `pitch_type`         | `ENUM('Football', 'Padel')` | NOT NULL          | Specifies the type of the pitch.                                |
| `number_of_players`  | `INT`                  | NOT NULL             | Number of players the pitch accommodates (e.g., 5v5, 7v7).      |
| `surface_type`       | `VARCHAR(50)`          |                      | Type of surface (e.g., grass, turf).                            |
| `is_active`          | `BOOLEAN`              | DEFAULT TRUE         | `TRUE` if the pitch is active.                                  |
| `is_blocked`         | `BOOLEAN`              | DEFAULT FALSE        | `TRUE` if the pitch is blocked.                                 |

---

## Table: `HourlySlot`

| Column               | Data Type              | Constraints          | Description                                                    |
|----------------------|------------------------|----------------------|----------------------------------------------------------------|
| `hour_slot_id`       | `INT`                  | Primary Key (PK)     | Unique identifier for each hourly slot.                         |
| `field_id`           | `INT`                  | Foreign Key (FK)     | References the field.                                           |
| `pitch_id`           | `INT`                  | Foreign Key (FK)     | References the pitch (nullable).                                |
| `date`               | `DATE`                 | NOT NULL             | Date for this hourly slot.                                      |
| `start_time`         | `TIME`                 | NOT NULL             | Start time of this hourly slot.                                 |
| `end_time`           | `TIME`                 | NOT NULL             | End time of this hourly slot.                                   |
| `hour_price`         | `DECIMAL(10,2)`        | NOT NULL             | Price for this specific hourly slot.                            |
| `status`             | `ENUM('Available', 'Booked', 'Blocked')` | DEFAULT 'Available' | Status of the slot.               |

---

## Table: `Booking`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `booking_id`        | `INT`                   | Primary Key (PK)      | Unique identifier for each booking.                             |
| `user_id`           | `INT`                   | Foreign Key (FK)      | References the user making the booking.                         |
| `field_id`          | `INT`                   | Foreign Key (FK)      | References the field being booked.                              |
| `pitch_id`          | `INT`                   | Foreign Key (FK)      | References the pitch being booked (nullable).                   |
| `start_hour_id`     | `INT`                   | Foreign Key (FK)      | References the start time of the booking.                       |
| `end_hour_id`       | `INT`                   | Foreign Key (FK)      | References the end time of the booking.                         |
| `total_price`       | `DECIMAL(10,2)`         | NOT NULL              | Total cost of the booking.                                      |
| `status`            | `ENUM('Confirmed', 'Cancelled', 'Pending')` | DEFAULT 'Pending' | Status of the booking.        |

---

## Table: `Payment`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `payment_id`        | `INT`                   | Primary Key (PK)      | Unique identifier for each payment.                             |
| `user_id`           | `INT`                   | Foreign Key (FK)      | References the user making the payment.                         |
| `booking_id`        | `INT`                   | Foreign Key (FK)      | References the associated booking.                              |
| `amount`            | `DECIMAL(10,2)`         | NOT NULL              | Payment amount.                                                 |
| `payment_method`    | `VARCHAR(50)`           | NOT NULL              | Method of payment (e.g., 'Cash', 'Credit Card').                |
| `payment_date`      | `TIMESTAMP`             | DEFAULT CURRENT_TIMESTAMP | Date and time of payment.                                 |

---

## Table: `Offer`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `offer_id`          | `INT`                   | Primary Key (PK)      | Unique identifier for each offer.                               |
| `offer_name`        | `VARCHAR(255)`          | NOT NULL              | Descriptive name of the offer.                                  |
| `discount_type`     | `ENUM('Percentage', 'Flat')` | NOT NULL           | Type of discount.                                               |
| `discount_value`    | `DECIMAL(10,2)`         | NOT NULL              | Value of the discount.                                          |
| `start_date`        | `DATE`                  | NOT NULL              | Date when the offer becomes active.                             |
| `end_date`          | `DATE`                  |                       | Date when the offer expires.                                    |

---

## Table: `Offer_Field`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `offer_id`          | `INT`                   | Foreign Key (FK)      | References the offer.                                           |
| `field_id`          | `INT`                   | Foreign Key (FK)      | References the field.                                           |
**Primary Key**: (`offer_id`, `field_id`)

---

## Table: `Offer_Pitch`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `offer_id`          | `INT`                   | Foreign Key (FK)      | References the offer.                                           |
| `pitch_id`          | `INT`                   | Foreign Key (FK)      | References the pitch.                                           |
**Primary Key**: (`offer_id`, `pitch_id`)

---

## Table: `Offer_HourlySlot`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `offer_id`          | `INT`                   | Foreign Key (FK)      | References the offer.                                           |
| `hour_slot_id`      | `INT`                   | Foreign Key (FK)      | References the hourly slot.                                     |
**Primary Key**: (`offer_id`, `hour_slot_id`)

---

## Table: `PromoCode`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `promo_code_id`     | `INT`                   | Primary Key (PK)      | Unique identifier for each promo code.                          |
| `code`              | `VARCHAR(50)`           | NOT NULL, UNIQUE      | The promo code string.                                          |
| `discount_type`     | `ENUM('Percentage', 'Flat')` | NOT NULL           | Type of discount.                                               |
| `discount_value`    | `DECIMAL(10,2)`         | NOT NULL              | Value of the discount.                                          |
| `expiration_date`   | `DATE`                  |                       | Expiration date of the promo code.                              |

---

## Table: `PromoCodeUsage`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `promo_code_usage_id`| `INT`                  | Primary Key (PK)      | Unique identifier for each promo code usage.                    |
| `promo_code_id`     | `INT`                   | Foreign Key (FK)      | References the promo code used.                                 |
| `user_id`           | `INT`                   | Foreign Key (FK)      | References the user who applied the promo code.                 |
| `booking_id`        | `INT`                   | Foreign Key (FK)      | References the booking where the promo code was applied.        |
| `used_on`           | `TIMESTAMP`             | DEFAULT CURRENT_TIMESTAMP | Date and time when the promo code was used.               |

---

## Table: `BookingOffer`

| Column              | Data Type               | Constraints           | Description                                                    |
|---------------------|-------------------------|-----------------------|----------------------------------------------------------------|
| `booking_offer_id`  | `INT`                   | Primary Key (PK)      | Unique identifier for each booking offer.                       |
| `booking_id`        | `INT`                   | Foreign Key (FK)      | References the booking.                                         |
| `offer_id`          | `INT`                   | Foreign Key (FK)      | References the offer.                                           |
| `discount_amount`   | `DECIMAL(10,2)`         | NOT NULL              | Amount discounted from the total price.                         |

---

## Table: `UserVerification`

| Column              | Data Type               | Constraints           | Description                                             |
|---------------------|-------------------------|-----------------------|---------------------------------------------------------|
| `verification_id`    | `INT`                   | Primary Key (PK)      | Unique identifier for each verification attempt.         |
| `user_id`           | `INT`                   | Foreign Key (FK)      | References the user attempting verification.             |
| `verification_code`  | `VARCHAR(10)`           | NOT NULL              | Code sent to the user for verification.                  |
| `code_type`         | `ENUM('Phone', 'Email')` | NOT NULL              | Type of verification (Phone/Email).                      |
| `expires_at`        | `TIMESTAMP`             | NOT NULL              | Time when the verification code expires.                 |
| `is_verified`       | `BOOLEAN`               | DEFAULT FALSE         | `TRUE` if the user is verified.                          |
| `created_at`        | `TIMESTAMP`             | DEFAULT CURRENT_TIMESTAMP | Time when verification entry was created.            |
| `attempt_count`     | `INT`                   | DEFAULT 0             | Number of verification attempts.                        |
