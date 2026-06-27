package com.academic.service;

import com.academic.exception.StudentException;
import com.academic.model.Student;
import com.academic.repository.StudentRepository;
import com.academic.validation.StudentValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private final StudentService studentService =
            new StudentService(
                    StudentRepository.getInstance(),
                    new StudentValidator()
            );

    @Test
    void addStudent_shouldSuccess_whenDataIsValid() {
        Student student = new Student(
                "999001",
                "Test Student",
                "Informatika",
                1
        );

        assertDoesNotThrow(() -> studentService.addStudent(student));
    }

    @Test
    void addStudent_shouldThrowException_whenNimIsEmpty() {
        Student student = new Student(
                "",
                "Test Student",
                "Informatika",
                1
        );

        assertThrows(
                StudentException.class,
                () -> studentService.addStudent(student)
        );
    }

    @Test
    void getStudentByNim_shouldReturnStudent_whenStudentExists() {
        Student student = studentService.getStudentByNim("999001");

        assertNotNull(student);
        assertEquals("999001", student.getNim());
    }
}