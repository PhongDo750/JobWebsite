# 💼 Job Search Website

A modern web platform that connects **job seekers** and **recruiters**, with support for **real-time communication**, **role-based access control**, and an **admin dashboard**. Built with **Spring Boot**, this system enables smooth job applications, post management, and real-time notifications.

---

## ✨ Features

### 🔐 Authentication
- Sign up / Login using **username/password**
- **OAuth2 login** with Google account

### 👨‍💼 Job Seeker
- Browse and search jobs
- Apply to job postings
- Track applied and liked jobs
- Receive **real-time notifications**
- Manage personal profile

### 🏢 Recruiter
- Post, edit, and delete job listings
- View and manage applications
- Approve or reject candidates
- Track job views and applicants

### 🛡️ Admin
- Manage all users and roles
- Moderate job postings
- Block/approve accounts

---

## 🛠️ Tech Stack

| Technology          | Description                        |
|---------------------|------------------------------------|
| **Java 21**         | Backend language                   |
| **Spring Boot**     | Application framework              |
| **Spring Security** | Basic security + CORS config       |
| **WebSocket**       | Update real-time notifications     |
| **WebPush**         | Push real-time notifications       |
| **JWT**             | Token-based authentication         |
| **OAuth2**          | Google login                       |
| **Redis**           | User presence                      |
| **Cloudinary**      | Upload and host job-related media  |
| **Lombok**          | Boilerplate code reduction         |
| **Maven**           | Dependency management & build tool |

---

## 📁 Project Structure

```text
com.example.JobWebsite/
├── cloudinary/          # Cloudinary upload & image services
├── common/              # Common constants
├── config/              # WebSocket, Security, Redis, OAuth2 configs
├── controller/          # REST API endpoints (job, auth, user, etc.)
├── dto/                 # Data Transfer Objects
├── entity/              # JPA entities (User, Job, Application...)
├── exceptionhandler/    # Global exception handling
├── helper/              # Utility/helper methods
├── mapper/              # Entity <-> DTO mapping
├── redis/               # Redis presence config
├── repository/          # Spring Data JPA Repositories
├── security/            # Basic security + CORS config 
├── service/             # Business logic
├── token/               # JWT provider, token store
└── websocket/           # WebSocket configuration & handlers
```
## 👤 Author

**Đỗ Gia Phong**  
📧 Email: [dogiaphong213@gmail.com](mailto:dogiaphong213@gmaill.com)  
💻 GitHub: [@PhongDo750](https://github.com/PhongDo750)  