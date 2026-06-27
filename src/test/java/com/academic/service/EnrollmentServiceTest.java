package com.academic.service;

import com.academic.exception.EnrollmentException;
import com.academic.model.Course;
import com.academic.model.Enrollment;
import com.academic.model.Student;
import com.academic.repository.interfaces.ICourseRepository;
import com.academic.repository.interfaces.IEnrollmentRepository;
import com.academic.repository.interfaces.IStudentRepository;
import com.academic.validation.EnrollmentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    private IEnrollmentRepository enrollmentRepository;
    private IStudentRepository studentRepository;
    private ICourseRepository courseRepository;
    private EnrollmentService service;

    @BeforeEach
    void setup() {
        enrollmentRepository = mock(IEnrollmentRepository.class);
        studentRepository = mock(IStudentRepository.class);
        courseRepository = mock(ICourseRepository.class);

        service = new EnrollmentService(
                enrollmentRepository,
                studentRepository,
                courseRepository,
                new EnrollmentValidator()
        );
    }

    private Enrollment sampleEnrollment(
            String studentNim,
            String courseCode) {

        return new Enrollment(
                studentNim,
                courseCode
        );
    }

    private Student sampleStudent() {
        return new Student(
                "22001",
                "Budi Santoso",
                "Laki-laki",
                "Informatika",
                3,
                "budi@mail.com",
                "08123456789"
        );
    }

    private Course sampleCourse() {
        return new Course(
                "MK001",
                "Pemrograman Java",
                3,
                "3"
        );
    }

    @Test
    void addEnrollment_success() {
        Enrollment enrollment = sampleEnrollment(
                "22001",
                "MK001"
        );

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(courseRepository.findByCode("MK001"))
                .thenReturn(sampleCourse());

        when(enrollmentRepository.findByStudentNimAndCourseCode(
                "22001",
                "MK001"))
                .thenReturn(null);

        assertDoesNotThrow(() ->
                service.addEnrollment(enrollment));

        verify(enrollmentRepository, times(1))
                .save(enrollment);
    }

    @Test
    void addEnrollment_emptyStudentNim() {
        Enrollment enrollment = sampleEnrollment(
                "",
                "MK001"
        );

        assertThrows(EnrollmentException.class,
                () -> service.addEnrollment(enrollment));

        verify(enrollmentRepository, never())
                .save(any(Enrollment.class));
    }

    @Test
    void addEnrollment_emptyCourseCode() {
        Enrollment enrollment = sampleEnrollment(
                "22001",
                ""
        );

        assertThrows(EnrollmentException.class,
                () -> service.addEnrollment(enrollment));

        verify(enrollmentRepository, never())
                .save(any(Enrollment.class));
    }

    @Test
    void addEnrollment_studentNotFound() {
        Enrollment enrollment = sampleEnrollment(
                "22099",
                "MK001"
        );

        when(studentRepository.findByNim("22099"))
                .thenReturn(null);

        assertThrows(EnrollmentException.class,
                () -> service.addEnrollment(enrollment));

        verify(enrollmentRepository, never())
                .save(any(Enrollment.class));
    }

    @Test
    void addEnrollment_courseNotFound() {
        Enrollment enrollment = sampleEnrollment(
                "22001",
                "MK999"
        );

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(courseRepository.findByCode("MK999"))
                .thenReturn(null);

        assertThrows(EnrollmentException.class,
                () -> service.addEnrollment(enrollment));

        verify(enrollmentRepository, never())
                .save(any(Enrollment.class));
    }

    @Test
    void addEnrollment_duplicateEnrollment() {
        Enrollment enrollment = sampleEnrollment(
                "22001",
                "MK001"
        );

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(courseRepository.findByCode("MK001"))
                .thenReturn(sampleCourse());

        when(enrollmentRepository.findByStudentNimAndCourseCode(
                "22001",
                "MK001"))
                .thenReturn(enrollment);

        assertThrows(EnrollmentException.class,
                () -> service.addEnrollment(enrollment));

        verify(enrollmentRepository, never())
                .save(any(Enrollment.class));
    }

    @Test
    void getAllEnrollments_success() {
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        enrollments.add(sampleEnrollment("22001", "MK001"));
        enrollments.add(sampleEnrollment("22002", "MK002"));

        when(enrollmentRepository.findAll())
                .thenReturn(enrollments);

        ArrayList<Enrollment> result =
                service.getAllEnrollments();

        assertEquals(2, result.size());
        assertEquals("22001", result.get(0).getStudentNim());
        assertEquals("MK002", result.get(1).getCourseCode());
    }

    @Test
    void getEnrollmentsByStudentNim_success() {
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        enrollments.add(sampleEnrollment("22001", "MK001"));
        enrollments.add(sampleEnrollment("22001", "MK002"));

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(enrollmentRepository.findByStudentNim("22001"))
                .thenReturn(enrollments);

        ArrayList<Enrollment> result =
                service.getEnrollmentsByStudentNim("22001");

        assertEquals(2, result.size());
        assertEquals("22001", result.get(0).getStudentNim());
    }

    @Test
    void getEnrollmentsByStudentNim_studentNotFound() {
        when(studentRepository.findByNim("22099"))
                .thenReturn(null);

        assertThrows(EnrollmentException.class,
                () -> service.getEnrollmentsByStudentNim("22099"));
    }

    @Test
    void deleteEnrollment_success() {
        when(enrollmentRepository.delete("22001", "MK001"))
                .thenReturn(true);

        assertDoesNotThrow(() ->
                service.deleteEnrollment("22001", "MK001"));

        verify(enrollmentRepository, times(1))
                .delete("22001", "MK001");
    }

    @Test
    void deleteEnrollment_notFound() {
        when(enrollmentRepository.delete("22099", "MK999"))
                .thenReturn(false);

        assertThrows(EnrollmentException.class,
                () -> service.deleteEnrollment("22099", "MK999"));
    }
}