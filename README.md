# QuickRide_Application

# Technologies Used

**Framework**: Spring Boot

**Database**: PostgreSQL, PostGIS

**API Integration**: OSRM API (Open Source Routing Machine)

**ORM**: Hibernate JPA

**Security**: Spring Security with JWT

**API Documentation**: Swagger UI


**Project Overview :**

QuickRide is a scalable backend service for a ride-booking application. It includes core functionalities for user authentication, ride management, payment processing, and driver allocation with robust support for route optimization and surge pricing.

# Key Features
**1. Backend Development :** 

Developed a modular backend architecture to support various core functionalities for the ride-booking application.
Ensured scalability and optimized performance across services.

**2. Authentication & Security :**

Implemented JWT-based authentication with Spring Security to ensure secure user sessions and data integrity.

**3. Modular Architecture :**

Adopted a microservices-like structure using modular entities for efficient data management and flexibility. Key entities include:
User,
Ride,
Payment,
Wallet,
Rating,

**4. Route Optimization and Pricing Strategy :**

Integrated OSRM API to optimize routes based on real-time traffic and distance.
Applied the Strategy Design Pattern to support surge pricing and flexible fare calculation strategies.

**5. Additional Functionalities :**

OTP and Email Notifications: Enhanced user security and communication.
Wallet Management: Added wallet features for seamless in-app transactions.
Rating System: Enabled users to rate their ride experience.
API Documentation: Used Swagger UI for clear and interactive API documentation.
Comprehensive Testing: Thoroughly tested each service to ensure reliability and performance.

# Low-Level Design

![Low level design of the Quick ride](https://github.com/user-attachments/assets/a83ad3b3-a9e1-43d7-a627-452381d96bfb)

# DataBase Schema

![Screenshot 2024-11-04 000901](https://github.com/user-attachments/assets/e0e80561-5734-425a-ac50-02ed1d5c1f17)

# PostGIS(Drivers Location)

![Screenshot 2024-11-07 232702](https://github.com/user-attachments/assets/d188fc41-d947-40f4-9c59-3425be3b9d44)
