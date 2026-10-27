# Employee Management REST API

A Spring Boot REST API to manage employees, built from scratch with in-memory
storage, DTO validation, dependency injection and centralised exception handling.

**Tech:** Java 21, Spring Boot 3.3, Maven, springdoc-openapi (Swagger UI), JUnit 5

## How to run
1. Open this folder (the one containing `pom.xml`) in IntelliJ IDEA and let Maven import it.
2. Run `EmployeeApiApplication.java` (green arrow next to `main`).
   Or from a terminal: `mvn spring-boot:run`
3. Open **Swagger UI**: http://localhost:8080/swagger-ui.html

Run the tests with `mvn test`.

## Endpoints
| Method | URL | What it does | Success |
|---|---|---|---|
| POST | `/api/employees` | Add an employee | 201 Created |
| GET | `/api/employees` | Get all employees | 200 OK |
| GET | `/api/employees/{id}` | Get one employee | 200 OK |
| PUT | `/api/employees/{id}` | Update an employee | 200 OK |
| DELETE | `/api/employees/{id}` | Delete an employee | 204 No Content |

Sample request body (POST / PUT):
```json
{
  "name": "Gaurav Pratap",
  "email": "gaurav@example.com",
  "department": "Engineering",
  "salary": 55000
}
```

## Error responses
Every error has the same JSON shape (produced by `GlobalExceptionHandler`):
```json
{
  "timestamp": "2026-10-20T10:15:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/employees",
  "fieldErrors": { "email": "Email must be a valid email address" }
}
```
| Situation | Status |
|---|---|
| Invalid request body (blank name, bad email, salary <= 0) | 400 |
| Malformed JSON or non-numeric id | 400 |
| Employee id does not exist | 404 |
| Email already used by another employee | 409 |
| Anything unexpected | 500 |

## Project structure
```
controller/   EmployeeController          HTTP layer: paths, status codes, @Valid
service/      EmployeeService (+Impl)     business rules (duplicate email, not found)
repository/   EmployeeRepository (+InMemory)  storage in a ConcurrentHashMap
dto/          EmployeeRequest / Response  what goes in and out of the API
mapper/       EmployeeMapper              DTO <-> model conversion
model/        Employee                    the stored object
exception/    GlobalExceptionHandler ...  @RestControllerAdvice + custom exceptions
config/       OpenApiConfig               Swagger title/description
```
Request flow: **Client -> Controller -> Service -> Repository**, and the
response travels back as a DTO.

## Key concepts used
- **Dependency Injection / IoC:** classes declare what they need in their constructor
  (`EmployeeController(EmployeeService)`, `EmployeeServiceImpl(EmployeeRepository, EmployeeMapper)`);
  Spring creates the beans (`@RestController`, `@Service`, `@Repository`, `@Component`)
  and wires them. No `new` for collaborators.
- **DTOs:** the API never exposes the internal `Employee` class. `EmployeeRequest`
  carries validation rules; `EmployeeResponse` shapes the output.
- **Validation:** `@Valid` + `@NotBlank`, `@Email`, `@Positive` on the request DTO.
- **@RestControllerAdvice:** one class converts exceptions into consistent JSON errors,
  so controllers have no try/catch.
- **Interfaces for service and repository:** the service depends on the
  `EmployeeRepository` interface, so in-memory storage could be swapped for a database.

## Testing with Swagger UI
1. Open http://localhost:8080/swagger-ui.html
2. Expand **POST /api/employees** -> *Try it out* -> *Execute* (sample body is pre-filled).
3. Use the returned `id` with GET by id, PUT and DELETE.
4. Try bad input (empty name, invalid email, negative salary) to see the 400 response,
   and an unknown id to see the 404 response.
