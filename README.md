# Noti-Smart: Secure Student Marks Management System

## Overview

**Noti-Smart** is a secure web application designed to revolutionize how universities distribute and manage student marks. It replaces insecure shared folder systems with a modern, privacy-focused platform that ensures data security while providing an intuitive experience for administrators and students.

### Problem Statement

Many universities currently distribute student marks through shared folders, which presents significant security and privacy risks. This approach:

- Lacks proper access control and authentication
- Violates student data privacy
- Provides no audit trail for mark distribution
- Makes it difficult to manage and visualize academic performance

### Solution

Noti-Smart provides a secure, centralized platform where:

- **Administrators** can easily upload student marks via drag-and-drop Excel files
- **Administrators** can visualize comprehensive dashboards with filtering and analytics
- **Students** have secure, personalized dashboards to view only their own marks
- **Students** receive instant notifications when marks are released
- All communications are encrypted and access-controlled

---

## Key Features

### Administrator Features

**Drag & Drop File Upload** - Upload Excel files containing student marks effortlessly  
 **Bulk Mark Distribution** - Process multiple student records in one action  
 **Advanced Dashboards** - Visualize academic performance with charts and statistics  
 **Filtering & Search** - Filter data by class, subject, evaluation type, and more  
 **User Management** - Manage admin and student accounts  
 **Activity Logs** - Track all mark distribution activities for accountability

### Student Features

**Secure Personal Dashboard** - View only your own marks  
 **Real-time Notifications** - Get notified instantly when marks are released  
 **Performance Analytics** - Visualize your academic performance with interactive charts  
 **Subject-wise Breakdown** - Analyze performance by subject and evaluation type  
 **Mark History** - Track all your marks over time

### Security Features

**Role-Based Access Control (RBAC)** - Different permissions for admins and students  
 **Authentication & Authorization** - Secure login with OTP verification  
 **Data Encryption** - All sensitive data is protected  
 **Privacy-First Design** - Students can only access their own data

---

## Tech Stack

### Backend

- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Authentication**: Spring Security
- **Notifications**: Firebase Cloud Messaging (FCM)
- **Email**: Brevo Email Service
- **Build Tool**: Maven

### Frontend

- **Framework**: Next.js (React)
- **Language**: TypeScript
- **Styling**: CSS with PostCSS
- **UI Components**: Custom shadcn/ui components
- **HTTP Client**: Axios
- **State Management**: React Context API

### DevOps

- **Containerization**: Docker & Docker Compose

---

## Project Structure

```
noti-smart/
├── backend/                          # Spring Boot Backend
│   ├── src/main/java/com/isimm/suivi_note/
│   │   ├── controllers/             # REST API endpoints
│   │   ├── services/                # Business logic
│   │   ├── models/                  # JPA entities
│   │   ├── repositories/            # Data access layer
│   │   ├── dto/                     # Data Transfer Objects
│   │   ├── config/                  # Configuration classes
│   │   ├── exceptions/              # Custom exceptions
│   │   ├── utils/                   # Utility classes
│   │   └── SuiviNoteApplication.java # Main application class
│   ├── src/main/resources/
│   │   ├── application.properties   # Application configuration
│   │   └── keys/                    # Firebase service account key
│   ├── pom.xml                      # Maven dependencies
│   └── docker-compose.yml           # Docker compose for local development
│
├── frontend/                         # Next.js Frontend
│   ├── src/app/
│   │   ├── (auth)/                  # Authentication pages
│   │   ├── admin/                   # Admin dashboard
│   │   ├── student/                 # Student dashboard
│   │   ├── layout.tsx               # Root layout
│   │   └── page.tsx                 # Home page
│   ├── src/components/              # Reusable components
│   ├── src/contexts/                # React contexts
│   ├── src/hooks/                   # Custom hooks
│   ├── src/lib/                     # Utilities and helpers
│   ├── next.config.ts               # Next.js configuration
│   ├── tsconfig.json                # TypeScript configuration
│   ├── package.json                 # NPM dependencies
│   └── postcss.config.mjs           # PostCSS configuration
│
└── docs/                            # Documentation

```

---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

**For Backend:**

- Java 17 or higher
- Maven 3.8.1 or higher
- PostgreSQL 12 or higher
- Docker & Docker Compose (optional, for containerized setup)

**For Frontend:**

- Node.js 18 or higher
- npm or yarn

**Additional:**

- Firebase project with service account key
- Brevo API key (for email notifications)

### Installation & Setup

#### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/noti-smart.git
cd noti-smart
```

#### 2. Backend Setup

**a) Database Configuration:**

Open `backend/src/main/resources/application.properties` and configure your PostgreSQL connection:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/noti_smart
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

**b) Firebase Configuration:**

Place your Firebase service account JSON key in:

```
backend/src/main/resources/keys/noti-smart-firebase-adminsdk-fbsvc-5c30ae0edd.json
```

**c) Build the Backend:**

```bash
cd backend
mvn clean install
```

**d) Run the Backend:**

```bash
mvn spring-boot:run
```

The backend API will be available at `http://localhost:8080`

#### 3. Frontend Setup

**a) Install Dependencies:**

```bash
cd frontend
npm install
# or
yarn install
```

**b) Configure Environment Variables:**

Create a `.env.local` file in the frontend directory:

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080
NEXT_PUBLIC_APP_NAME=Noti-Smart
```

**c) Run the Development Server:**

```bash
npm run dev
# or
yarn dev
```

The frontend will be available at `http://localhost:3000`

---

## Running with Docker Compose

For a complete local development environment with PostgreSQL and all services:

```bash
cd backend
docker-compose up -d
```

This will start:

- PostgreSQL database on port 5432
- Backend Spring Boot application on port 8080

---

## API Endpoints

### Authentication

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login with OTP
- `POST /api/auth/verify-otp` - Verify OTP

### Admin Endpoints

- `POST /api/admin/upload-marks` - Upload marks via Excel file
- `GET /api/admin/dashboard` - Get admin dashboard data
- `GET /api/admin/students` - List all students
- `GET /api/admin/stats` - Get statistics and analytics

### Student Endpoints

- `GET /api/student/marks` - Get student's marks
- `GET /api/student/dashboard` - Get student dashboard data
- `GET /api/student/stats` - Get student performance stats

### Notifications

- `GET /api/notifications` - Get user notifications
- `PUT /api/notifications/{id}/read` - Mark notification as read

---

## Security Features

- **Spring Security**: Implements role-based access control
- **OTP Authentication**: Two-factor authentication for enhanced security
- **JWT Tokens**: Stateless authentication
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Secured cross-origin requests
- **Input Validation**: Server-side validation for all inputs

---

## Dashboard Features

### Admin Dashboard

- Total students and marks uploaded count
- Subject-wise performance analysis
- Class/Filière-wise statistics
- Mark distribution charts
- Recent activity logs

### Student Dashboard

- Personal marks for all subjects
- Performance trend analysis
- Comparison with class average
- Subject-wise performance radar chart
- Evaluation breakdown pie charts

---

## Development

### Running Tests

**Backend:**

```bash
cd backend
mvn test
```

**Frontend:**

```bash
cd frontend
npm run test
```

### Building for Production

**Backend:**

```bash
cd backend
mvn clean package -DskipTests
```

**Frontend:**

```bash
cd frontend
npm run build
npm start
```

---

## Environment Variables

### Backend (`application.properties`)

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/noti_smart
spring.datasource.username=postgres
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Firebase
firebase.credentials.path=classpath:keys/noti-smart-firebase-adminsdk-fbsvc-5c30ae0edd.json

# Brevo
brevo.api.key=your_brevo_api_key
brevo.sender.email=noreply@youruniversity.com

# JWT
app.jwt.secret=your_jwt_secret_key
app.jwt.expiration=86400000

# CORS
app.cors.allowed-origins=http://localhost:3000
```

### Frontend (`.env.local`)

```
NEXT_PUBLIC_API_URL=http://localhost:8080
NEXT_PUBLIC_APP_NAME=Noti-Smart
```

---

## Contributing

This project was developed during **Act4ISIMM Hackathon** organized by **ISIMM (Institut Supérieur d'Informatique et de Mathématiques Appliquées)**.

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## License

This project is developed for educational purposes and is part of the Act4ISIMM hackathon.

---

## Hackathon Information

**Event**: Act4ISIMM Hackathon  
**Institution**: ISIMM (Institut Supérieur d'Informatique et de Mathématiques Appliquées)  
**Year**: 2025-2026  
**Theme**: Secure Digital Solutions for University Management
