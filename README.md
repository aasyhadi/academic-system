# Academic Management System

Console Based Academic Information System built with Java, Maven, OOP, Repository Pattern, Service Layer, Validation Layer, Factory Pattern, and Unit Testing.

## Features

### Student Management
- Add Student
- Update Student
- Delete Student
- Search Student
- Sort Student
- Pagination

### Lecturer Management
- Add Lecturer
- Update Lecturer
- Delete Lecturer

### Course Management
- Add Course
- Update Course
- Delete Course

### Enrollment Management
- Student Course Enrollment
- Enrollment Validation

### Grade Management
- Add Grade
- Update Grade
- Delete Grade
- GPA Calculation
- Grade Letter Conversion

## Architecture

- OOP
- Repository Pattern
- Service Layer
- Validation Layer
- Dependency Injection
- Factory Pattern
- Singleton Repository
- Custom Exception
- Logging Utility

## Technologies

- Java 21
- Maven
- JUnit 5
- Mockito

## Unit Testing

| Module | Status |
|----------|----------|
| StudentServiceTest | ✅ PASS |
| LecturerServiceTest | ✅ PASS |
| CourseServiceTest | ✅ PASS |
| EnrollmentServiceTest | ✅ PASS |
| GradeServiceTest | ✅ PASS |

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.academic
│   │       ├── config
│   │       ├── constant
│   │       ├── enums
│   │       ├── exception
│   │       ├── factory
│   │       ├── menu
│   │       ├── model
│   │       ├── repository
│   │       ├── service
│   │       ├── util
│   │       └── validation
│
└── test
    └── java
        └── com.academic.service
```

## Author

Aasyahadi