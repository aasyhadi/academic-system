package com.academic.service;

import com.academic.exception.CourseException;
import com.academic.model.Course;
import com.academic.repository.interfaces.ICourseRepository;
import com.academic.validation.CourseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    private ICourseRepository repository;
    private CourseService service;

    @BeforeEach
    void setup() {
        repository = mock(ICourseRepository.class);
        service = new CourseService(repository, new CourseValidator());
    }

    private Course sampleCourse(
            String code,
            String name,
            int credit,
            String semester) {

        return new Course(
                code,
                name,
                credit,
                semester
        );
    }

    @Test
    void addCourse_success() {
        Course course = sampleCourse(
                "MK001",
                "Pemrograman Java",
                3,
                "3"
        );

        when(repository.findByCode("MK001")).thenReturn(null);

        assertDoesNotThrow(() -> service.addCourse(course));

        verify(repository, times(1)).save(course);
    }

    @Test
    void addCourse_duplicateCode() {
        Course course = sampleCourse(
                "MK001",
                "Pemrograman Java",
                3,
                "3"
        );

        when(repository.findByCode("MK001")).thenReturn(course);

        assertThrows(CourseException.class,
                () -> service.addCourse(course));

        verify(repository, never()).save(any(Course.class));
    }

    @Test
    void addCourse_emptyCode() {
        Course course = sampleCourse(
                "",
                "Pemrograman Java",
                3,
                "3"
        );

        assertThrows(CourseException.class,
                () -> service.addCourse(course));

        verify(repository, never()).save(any(Course.class));
    }

    @Test
    void getCourseByCode_success() {
        Course course = sampleCourse(
                "MK001",
                "Pemrograman Java",
                3,
                "3"
        );

        when(repository.findByCode("MK001")).thenReturn(course);

        Course result = service.getCourseByCode("MK001");

        assertNotNull(result);
        assertEquals("MK001", result.getCode());
        assertEquals("Pemrograman Java", result.getName());
    }

    @Test
    void getCourseByCode_notFound() {
        when(repository.findByCode("MK999")).thenReturn(null);

        assertThrows(CourseException.class,
                () -> service.getCourseByCode("MK999"));
    }

    @Test
    void updateCourse_success() {
        Course existingCourse = sampleCourse(
                "MK001",
                "Pemrograman Java",
                3,
                "3"
        );

        Course updatedCourse = sampleCourse(
                "MK001",
                "Advanced Java",
                4,
                "4"
        );

        when(repository.findByCode("MK001")).thenReturn(existingCourse);
        when(repository.update("MK001", updatedCourse)).thenReturn(true);

        assertDoesNotThrow(() ->
                service.updateCourse("MK001", updatedCourse));

        verify(repository, times(1)).update("MK001", updatedCourse);
    }

    @Test
    void updateCourse_notFound() {
        Course updatedCourse = sampleCourse(
                "MK999",
                "Advanced Java",
                4,
                "4"
        );

        when(repository.update(
                "MK999",
                updatedCourse))
                .thenReturn(false);

        assertThrows(
                CourseException.class,
                () -> service.updateCourse(
                        "MK999",
                        updatedCourse
                )
        );

        verify(repository, times(1))
                .update("MK999", updatedCourse);
    }

    @Test
    void deleteCourse_success() {
        when(repository.delete("MK001")).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteCourse("MK001"));

        verify(repository, times(1)).delete("MK001");
    }

    @Test
    void deleteCourse_notFound() {
        when(repository.delete("MK999")).thenReturn(false);

        assertThrows(CourseException.class,
                () -> service.deleteCourse("MK999"));

        verify(repository, times(1)).delete("MK999");
    }

    @Test
    void getAllCourses_success() {
        ArrayList<Course> courses = new ArrayList<>();

        courses.add(sampleCourse(
                "MK001",
                "Pemrograman Java",
                3,
                "3"
        ));

        courses.add(sampleCourse(
                "MK002",
                "Database",
                3,
                "3"
        ));

        when(repository.findAll()).thenReturn(courses);

        ArrayList<Course> result = service.getAllCourses();

        assertEquals(2, result.size());
        assertEquals("Pemrograman Java", result.get(0).getName());
        assertEquals("Database", result.get(1).getName());
    }
}