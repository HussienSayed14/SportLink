# SportLink


# Database Schema

This schema is designed for a booking application, including user registration, verification, bookings, and offer management for football and padel fields.

---

## Table: `User`

### Description:
Tracks user information, including failed login attempts, blocked, and activated status.

### Columns:

- **`user_id`**: `INT` (Primary Key)
  - Unique identifier for the user.

- **`name`**: `VARCHAR(255)` NOT NULL
  - Full name of the user.

- **`email`**: `VARCHAR(255)` NOT NULL UNIQUE
  - Email address of the user.

- **`phone_number`**: `VARCHAR(20)` NOT NULL UNIQUE
  - Contact number of the user.

- **`role`**: `VARCHAR(50)` NOT NULL
  - Role of the user (e.g., 'Player', 'Field Owner').

- **`password_hash`**: `VARCHAR(255)` NOT NULL
  - Hashed password for security.

- **`failed_attempts`**: `INT` DEFAULT `0`
  - Count of failed login attempts.

- **`is_blocked`**: `BOOLEAN` DEFAULT `FALSE`
  - `TRUE` if the user is blocked.

- **`is_activated`**: `BOOLEAN` DEFAULT `FALSE`
  - `TRUE` if the user is activated.

- **`created_at`**: `TIMESTAMP` DEFAULT `CURRENT_TIMESTAMP`
  - Timestamp when the user was registered.

- **`last_login`**: `TIMESTAMP`
  - Timestamp of the user's last login.

---

## Table: `UserVerification`

### Description:
This table tracks user verification attempts when they receive a verification code (via phone or email) during the registration process.

### Columns:

- **`verification_id`**: `INT` (Primary Key)
  - Unique identifier for each verification attempt.

- **`user_id`**: `INT` (Foreign Key to `User` table)
  - References the user who is attempting to verify their phone or email.

- **`verification_code`**: `VARCHAR(10)`
  - The code sent to the user for verification.

- **`code_type`**: `ENUM('Phone', 'Email')`
  - Specifies the type of verification: phone or email.

- **`expires_at`**: `TIMESTAMP`
  - The time when the verification code expires.

- **`is_verified`**: `BOOLEAN`
  - Whether the user successfully verified (`TRUE` if verified, `FALSE` otherwise).

- **`created_at`**: `TIMESTAMP` DEFAULT `CURRENT_TIMESTAMP`
  - The timestamp when the verification entry was created.

- **`attempt_count`**: `INT` DEFAULT `0`
  - The number of times the user has attempted to verify.

---

## Table: `LoginAudit`

### Description:
Tracks user logins, storing session ID and IP address for each session.

### Columns:

- **`login_audit_id`**: `INT` (Primary Key)
  - Unique identifier for each login audit record.

- **`user_id`**: `INT` (Foreign Key to `User` table)
  - References the user who logged in.

- **`session_id`**: `VARCHAR(255)` NOT NULL
  - Unique session identifier for the login session.

- **`ip_address`**: `VARCHAR(45)` NOT NULL
  - The IP address from which the user logged in.

- **`login_time`**: `TIMESTAMP` DEFAULT `CURRENT_TIMESTAMP`
  - Timestamp of the login.

- **`logout_time`**: `TIMESTAMP`
  - Timestamp of the logout.

---

## Table: `Field`

### Description:
Tracks fields and their availability for booking.

### Columns:

- **`field_id`**: `INT` (Primary Key)
  - Unique identifier for each field.

- **`field_owner_id`**: `INT` (Foreign Key to `User` table)
  - References the field owner.

- **`field_name`**: `VARCHAR(255)` NOT NULL
  - Name of the field.

- **`location`**: `VARCHAR(255)` NOT NULL
  - Physical location of the field.

- **`field_type`**: `ENUM('FOOTBALL', 'PADEL', 'BOTH')` NOT NULL
  - Type of field available.

- **`default_hour_price`**: `DECIMAL(10,2)` NOT NULL
  - Standard hourly price for bookings at this field.

- **`is_active`**: `BOOLEAN` DEFAULT `TRUE`
  - `TRUE` if the field is active.

- **`is_blocked`**: `BOOLEAN` DEFAULT `FALSE`
  - `TRUE` if the field is blocked.

---

## Table: `Pitch`

### Description:
Tracks individual pitches associated with fields.

### Columns:

- **`pitch_id`**: `INT` (Primary Key)
  - Unique identifier for each pitch.

- **`field_id`**: `INT` (Foreign Key to `Field` table)
  - References the field this pitch belongs to.

- **`pitch_number`**: `INT` NOT NULL
  - Unique number for each pitch within a field.

- **`pitch_type`**: `ENUM('Football', 'Padel')` NOT NULL
  - Specifies the type of pitch.

- **`number_of_players`**: `INT` NOT NULL
  - Number of players the pitch accommodates (e.g., 5, 7, 11).

- **`surface_type`**: `VARCHAR(50)`
  - Type of surface (e.g., grass, turf).

- **`is_active`**: `BOOLEAN` DEFAULT `TRUE`
  - `TRUE` if the pitch is active.

- **`is_blocked`**: `BOOLEAN` DEFAULT `FALSE`
  - `TRUE` if the pitch is blocked.

---

## Table: `HourlySlot`

### Description:
Represents individual hourly slots available for booking.

### Columns:

- **`hour_slot_id`**: `INT` (Primary Key)
  - Unique identifier for each hourly slot.

- **`field_id`**: `INT` (Foreign Key to `Field` table)
  - References the field.

- **`pitch_id`**: `INT` (Foreign Key to `Pitch` table, nullable)
  - References the pitch (nullable).

- **`date`**: `DATE` NOT NULL
  - Date for this hourly slot.

- **`start_time`**: `TIME` NOT NULL
  - Start time of this hourly slot.

- **`end_time`**: `TIME` NOT NULL
  - End time of this hourly slot.

- **`hour_price`**: `DECIMAL(10,2)` NOT NULL
  - Price for this specific hourly slot.

- **`status`**: `ENUM('Available', 'Booked', 'Blocked')` DEFAULT 'Available'
  - Status of the slot.

---

## Table: `Booking`

### Description:
Tracks bookings made by users for fields and pitches.

### Columns:

- **`booking_id`**: `INT` (Primary Key)
  - Unique identifier for each booking.

- **`user_id`**: `INT` (Foreign Key to `User` table)
  - References the user making the booking.

- **`field_id`**: `INT` (Foreign Key to `Field` table)
  - References the field being booked.

- **`pitch_id`**: `INT` (Foreign Key to `Pitch` table, nullable)
  - References the pitch being booked.

- **`start_hour_id`**: `INT` (Foreign Key to `HourlySlot` table)
  - References the start time of the booking.

- **`end_hour_id`**: `INT` (Foreign Key to `HourlySlot` table)
  - References the end time of the booking.

- **`total_price`**: `DECIMAL(10,2)` NOT NULL
  - Total cost of the booking.

- **`status`**: `ENUM('Confirmed', 'Cancelled', 'Pending')` DEFAULT 'Pending'
  - Status of the booking.

---

## Table: `Payment`

### Description:
Tracks payments made by users for their bookings.

### Columns:

- **`payment_id`**: `INT` (Primary Key)
  - Unique identifier for each payment.

- **`user_id`**: `INT` (Foreign Key to `User` table)
  - References the user making the payment.

- **`booking_id`**: `INT` (Foreign Key to `Booking` table)
  - References the associated booking.

- **`amount`**: `DECIMAL(10,2)` NOT NULL
  - Payment amount.

- **`payment_method`**: `VARCHAR(50)` NOT NULL
  - Method of payment (e.g., 'Cash', 'Credit Card').

- **`payment_date`**: `TIMESTAMP` DEFAULT `CURRENT_TIMESTAMP`
  - Date and time of payment.

---

## Table: `Offer`

### Description:
Tracks special offers that can apply to fields, pitches, or hourly slots.

### Columns:

- **`offer_id`**: `INT` (Primary Key)
  - Unique identifier for each offer.

- **`offer_name`**: `VARCHAR(255)` NOT NULL
  - Descriptive name of the offer.

- **`discount_type`**: `ENUM('Percentage', 'Flat')` NOT NULL
  - Type of discount (percentage or flat rate).

- **`discount_value`**: `DECIMAL(10,2)` NOT NULL
  - Value of the discount.

- **`start_date`**: `DATE` NOT NULL
  - Date when the offer becomes active.

- **`end_date`**: `DATE`
  - Date when the offer expires.

---

## Table: `Offer_Field`

### Description:
Junction table to map offers to fields (Many-to-Many relationship).

### Columns:

- **`offer_id`**: `INT` (Foreign Key to `Offer` table)
  - References the offer.

- **`field_id`**: `INT` (Foreign Key to `Field` table)
  - References the field.

**Primary Key:** (`offer_id`, `field_id`)

---

## Table: `Offer_Pitch`

### Description:
Junction table to map offers to pitches (Many-to-Many relationship).

### Columns:

- **`offer_id`**: `INT` (Foreign Key to `Offer` table)
  - References the offer.

- **`pitch_id`**: `INT` (Foreign Key to `Pitch` table)
  - References the pitch.

**Primary Key:** (`offer_id`, `pitch_id`)

---

## Table: `Offer_HourlySlot`

### Description:
Junction table to map offers to hourly slots (Many-to-Many relationship).

### Columns:

- **`offer_id`**: `INT` (Foreign Key to `Offer` table)
  - References the offer.

- **`hour_slot_id`**: `INT` (Foreign Key to `HourlySlot` table)
  - References the hourly slot.

**Primary Key:** (`offer_id`, `hour_slot_id`)

---

## Table: `PromoCode`

### Description:
Tracks promo codes that users can apply to their bookings.

### Columns:

- **`promo_code_id`**: `INT` (Primary Key)
  - Unique identifier for each promo code.

- **`code`**: `VARCHAR(50)` NOT NULL UNIQUE
  - The promo code string.

- **`discount_type`**: `ENUM('Percentage', 'Flat')` NOT NULL
  - Type of discount.

- **`discount_value`**: `DECIMAL(10,2)` NOT NULL
  - Value of the discount.

- **`expiration_date`**: `DATE`
  - Expiration date of the promo code.

---

## Table: `PromoCodeUsage`

### Description:
Tracks the usage of promo codes by users.

### Columns:

- **`promo_code_usage_id`**: `INT` (Primary Key)
  - Unique identifier for each usage record.

- **`promo_code_id`**: `INT` (Foreign Key to `PromoCode` table)
  - References the promo code used.

- **`user_id`**: `INT` (Foreign Key to `User` table)
  - References the user who applied the promo code.

- **`booking_id`**: `INT` (Foreign Key to `Booking` table)
  - References the booking where the promo code was applied.

- **`used_on`**: `TIMESTAMP` DEFAULT `CURRENT_TIMESTAMP`
  - Date and time when the promo code was used.

---

## Table: `BookingOffer`

### Description:
Tracks which offers have been applied to bookings.

### Columns:

- **`booking_offer_id`**: `INT` (Primary Key)
  - Unique identifier for each record.

- **`booking_id`**: `INT` (Foreign Key to `Booking` table)
  - References the booking.

- **`offer_id`**: `INT` (Foreign Key to `Offer` table)
  - References the offer.

- **`discount_amount`**: `DECIMAL(10,2)` NOT NULL
  - Amount discounted from the total price.

---
