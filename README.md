Appointment Management System
Technologies Used
Technology	Version	Purpose
Java	17	Programming language used for the backend
Spring Boot	3.5.9	Main framework for building the application
Spring Data JPA	3.5.9	ORM and database access layer
Spring Security	3.5.9	Authentication and authorization
Spring Web	3.5.9	REST API support
Spring Validation	3.5.9	Request and entity validation
Spring AOP	3.5.9	Logging and cross-cutting concerns
Spring WebSocket	3.5.9	Real-time notifications
H2 Database	Runtime	In-memory database for development
Lombok	Provided	Reduces boilerplate code
JWT (jjwt)	0.11.5	Token-based authentication
Bouncy Castle	1.78.1	Cryptographic operations and SSL support
Maven	�	Build and dependency management tool

How to Run the Project
Option 1: Using IntelliJ IDEA (Recommended)
1.Install Java 17.
2.Open IntelliJ IDEA.
3.Choose Open and select the demo project folder.
4.IntelliJ will automatically detect the Maven project and download dependencies.
5.Open DemoApplication.java.
6.Click Run ??.
7.The application will start on:
8.http://localhost:8080

Option 2: Without IntelliJ (Command Line)
1.Install Java 17.
2.Make sure Git Bash / Terminal is available.
3.Navigate to the project directory:
4.cd demo
5.Run the project using Maven Wrapper:
6../mvnw spring-boot:run
On Windows:
mvnw.cmd spring-boot:run
7.Wait until Spring Boot finishes startup.
8.Access the application at:
9.http://localhost:8080

System Architecture
The project follows Layered Architecture:
?Controller Layer: Handles HTTP and WebSocket requests.
?Service Layer: Contains business logic.
?Repository Layer: Database access using JPA.
?Security Layer: JWT authentication and authorization.
?DTO Layer: Data transfer between layers.
?Entity Layer: Database entities.
?Aspect & Handler Layer: Logging and exception handling.

Package Structure and Purpose
com.appointment_management.demo
?DemoApplication.java � Application entry point.
?SslBootstrap.java � Initializes SSL key at startup.
aspectandhandler
?GlobalExceptionHandler � Centralized exception handling.
?LoggingAspect � Logs method execution using AOP.
controller
?AuthController � Authentication and login endpoints.
?AppointmentController � Appointment management APIs.
?ServiceEntityController � Service management APIs.
?WorkingScheduleController � Working schedule APIs.
?HolidayController � Holiday management APIs.
service
?UserService � User-related business logic.
?AppointmentService � Appointment business logic.
?ServiceEntityService � Service business logic.
?WorkingScheduleService � Schedule business logic.
?HolidayService � Holiday business logic.
repository
?UserRepository
?AppointmentRepository
?ServiceEntityRepository
?WorkingScheduleRepository
?HolidayRepository
Handles database operations using JPA.
entity
?User
?Appointment
?ServiceEntity
?WorkingSchedule
?Holiday
Represents database tables.
dto
?AuthDto
?CreateAppointmentRequest
?CreateServiceRequest
?UpdateServiceRequest
?AddScheduleRequest
?UpdateScheduleRequest
?AddHolidayRequest
?ReasonRequest
?TimeRange
Used for transferring data between layers.
security
?SecurityConfig � Spring Security configuration.
?JwtAuthenticationFilter � JWT request filter.
?JwtService � Token generation and validation.
?JwtProperties � JWT configuration properties.
?PasswordConfig � Password encoding configuration.
websocket
?WebSocketConfig � WebSocket configuration.
?NotificationService � Sends notifications.
?AppointmentNotification � Appointment notification model.

API Reference
All endpoints requir a Barer Token after login> tha base URL is https://localhost:8080.
1.Authentication :
?POST /auth/register : Register a new user (Roles:ADMIN,STAFF,CUSTOMER).
?POST /auth/login : Authenticate and receive a JWT.
2.Appointment Management :
?POST /appointments : Book a new appointment.
?PUT /appointments/{id}/update : Modify an existing appointment.
?GET /appointments/{id}/approve : Approve a pending appointment.
?GET /appointments/{id}/reject : Reject an appointment.
?GET /appointments/{id}/cancel : Cancel an appointment.
?GET /appointment/{id}/finish : Finish Appointment.
?GET /appointments/provider : View appointments assigned to the logged-in provider.
?GET /appointments/customer : View appointments booked by the logged-in customer.
3.Services :
?POST /services : Add a new service.
?GET /services : List all services with pagination.
?GET /services/times : Get available time slots for a specific service and date.
?PUT /services/{id} : Update service details
?GET /services/provider/{id} : Get Provider Service.
?DELETE /service/{id} : Delete Service.
4.Schedules & Holidays :
?POST /schedules : Add Working Schedule.
?POST /holidays : Add Holiday.
?GET /schedules/provider/{id} : Get Schedules for provider.
?PUT /schedules/{id} :  Update Schedules.
?DELETE /schedules/{id} : Delete Schedules.
?DELETE /holidays /{id} : Delete holidays .

Project Work Distibution
Tala alrayes (???? ???? ?????) :
Appointment Logic Developer
?Controller : AppointmentController.
?Service : AppointmentService.
?Repository : AppointmentRepository.
?Entity : Appointment.
?DTOs : CreateAppointmentRequest and ReasonRequest
Maysaa alhussein (????? ???? ??????) :
Scheduling & Holiday Developer
?Controllers : WorkingSchedulController and HolidayController.
?Services : WorkingSchedulService and HolidayServices.
?Repositories : WorkingSchedulRepository and HolidayRepository.
?Entities : WorkingSchedul and Holiday.
?DTOs : AddSchedulRequest and UodateSchedulRequest , AddHolidayRequest and TimeRange.
Ghufran Atmeh (????? ???? ????) :
Security & Infrastructure Developer
?Security Package : Implement SecurityConfig, JwtAuthenticationFilter , JwtService, and PasswordConfig.
?Aspect & Handler Package : Develop GlobalExceptionHandler for error management and LogginAspect for AOP-based logging>
?Bootstrap : Manage SslBootstrap.java to inialize SSL keys.

Solaf haider (???? ???? ????) :
User & Service Developer
?Controllers : AuthController and ServiceEntityController.
?Services : UserService and ServiceEntityService.
?Repositories : UserRepository and ServiceEntityRepository.
?Entities : User and ServiceEntity.
?DTOs : AuthDto , CreateServiceRequest , and UpdateServiceRequest.
Muhammad aizez (???? ???? ?????) :
Notifications Devloper
?Websocket Package : Implement webSocketConfig to enable real-time communication.
?Notification Logic : Develop NotifiactionService to push live alerts.
?Models : create the AppointmentNotification model for data payloads.
?Validation : Assist with Spring Validation across all layes to ensure data integrity.
