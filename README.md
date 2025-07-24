# DocPilot 📝

DocPilot is a backend system for a collaborative document editing platform, similar to Google Docs. Built with Spring Boot, it allows users to create documents, share them with specific users, and control permissions (read/write).

## Features ✅
- User Registration & Login (JWT planned)
- Create, edit, delete personal documents
- Share documents with other users
- Fine-grained permission control (read or write)
- In-memory H2 database for development
- Built using Spring Boot, Spring Security, JPA

## Tech Stack 🛠️
- Java 17
- Spring Boot
- Spring Security (basic setup for now)
- Spring Data JPA
- H2 (for dev, easily switchable to MySQL)

## Getting Started 🚀
1. Clone the repo:
   ```bash
   git clone https://github.com/yourusername/docpilot.git
   cd docpilot

2. Run the app (from IntelliJ or via terminal):
   ```bash
   ./mvnw spring-boot:run

3. Access H2 Console:
   URL: http://localhost:8080/h2-console
   JDBC URL: jdbc:h2:mem:docpilotdb
   User: sa, Password: (leave empty)

4. Roadmap 🛤️
   ✅Basic CRUD for documents
   ✅User and permission model
   ❌JWT Authentication
   ❌Version history
   ❌Real-time editing with WebSockets

License
This project is open-source under the MIT License.
---
Let me know if you want the README to include sample API endpoints or database diagrams too.
