package com.academic.service;

import com.academic.exception.GradeException;
import com.academic.model.Course;
import com.academic.model.Grade;
import com.academic.model.Student;
import com.academic.repository.interfaces.ICourseRepository;
import com.academic.repository.interfaces.IGradeRepository;
import com.academic.repository.interfaces.IStudentRepository;
import com.academic.validation.GradeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeServiceTest {

    private IGradeRepository gradeRepository;
    private IStudentRepository studentRepository;
    private ICourseRepository courseRepository;
    private GradeService service;

    @BeforeEach
    void setup() {
        gradeRepository = mock(IGradeRepository.class);
        studentRepository = mock(IStudentRepository.class);
        courseRepository = mock(ICourseRepository.class);

        service = new GradeService(
                gradeRepository,
                studentRepository,
                courseRepository,
                new GradeValidator()
        );
    }

    private Grade sampleGrade(
            String nim,
            String courseCode,
            double score) {

        return new Grade(
                nim,
                courseCode,
                score
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
    void addGrade_success() {
        Grade grade = sampleGrade(
                "22001",
                "MK001",
                85.0
        );

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(courseRepository.findByCode("MK001"))
                .thenReturn(sampleCourse());

        when(gradeRepository.findByStudentNimAndCourseCode(
                "22001",
                "MK001"))
                .thenReturn(null);

        assertDoesNotThrow(() -> service.addGrade(grade));

        assertNotNull(grade.getLetter());

        verify(gradeRepository, times(1))
                .save(grade);
    }

    @Test
    void addGrade_studentNotFound() {
        Grade grade = sampleGrade(
                "22099",
                "MK001",
                85.0
        );

        when(studentRepository.findByNim("22099"))
                .thenReturn(null);

        assertThrows(GradeException.class,
                () -> service.addGrade(grade));

        verify(gradeRepository, never())
                .save(any(Grade.class));
    }

    @Test
    void addGrade_courseNotFound() {
        Grade grade = sampleGrade(
                "22001",
                "MK999",
                85.0
        );

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(courseRepository.findByCode("MK999"))
                .thenReturn(null);

        assertThrows(GradeException.class,
                () -> service.addGrade(grade));

        verify(gradeRepository, never())
                .save(any(Grade.class));
    }

    @Test
    void addGrade_duplicateGrade() {
        Grade grade = sampleGrade(
                "22001",
                "MK001",
                85.0
        );

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(courseRepository.findByCode("MK001"))
                .thenReturn(sampleCourse());

        when(gradeRepository.findByStudentNimAndCourseCode(
                "22001",
                "MK001"))
                .thenReturn(grade);

        assertThrows(GradeException.class,
                () -> service.addGrade(grade));

        verify(gradeRepository, never())
                .save(any(Grade.class));
    }

    @Test
    void addGrade_emptyStudentNim() {
        Grade grade = sampleGrade(
                "",
                "MK001",
                85.0
        );

        assertThrows(GradeException.class,
                () -> service.addGrade(grade));

        verify(gradeRepository, never())
                .save(any(Grade.class));
    }

    @Test
    void getAllGrades_success() {
        ArrayList<Grade> grades = new ArrayList<>();

        grades.add(sampleGrade("22001", "MK001", 85.0));
        grades.add(sampleGrade("22002", "MK002", 90.0));

        when(gradeRepository.findAll())
                .thenReturn(grades);

        ArrayList<Grade> result = service.getAllGrades();

        assertEquals(2, result.size());
        assertEquals("22001", result.get(0).getStudentNim());
        assertEquals("MK002", result.get(1).getCourseCode());
    }

    @Test
    void getGrade_success() {
        Grade grade = sampleGrade(
                "22001",
                "MK001",
                85.0
        );

        when(gradeRepository.findByStudentNimAndCourseCode(
                "22001",
                "MK001"))
                .thenReturn(grade);

        Grade result = service.getGrade(
                "22001",
                "MK001"
        );

        assertNotNull(result);
        assertEquals("22001", result.getStudentNim());
        assertEquals("MK001", result.getCourseCode());
        assertEquals(85.0, result.getScore());
    }

    @Test
    void getGrade_notFound() {
        when(gradeRepository.findByStudentNimAndCourseCode(
                "22099",
                "MK999"))
                .thenReturn(null);

        assertThrows(GradeException.class,
                () -> service.getGrade(
                        "22099",
                        "MK999"
                ));
    }

    @Test
    void updateGrade_success() {
        Grade updatedGrade = sampleGrade(
                "22001",
                "MK001",
                95.0
        );

        when(gradeRepository.update(
                "22001",
                "MK001",
                updatedGrade))
                .thenReturn(true);

        assertDoesNotThrow(() ->
                service.updateGrade(
                        "22001",
                        "MK001",
                        updatedGrade
                ));

        assertNotNull(updatedGrade.getLetter());

        verify(gradeRepository, times(1))
                .update(
                        "22001",
                        "MK001",
                        updatedGrade
                );
    }

    @Test
    void updateGrade_notFound() {
        Grade updatedGrade = sampleGrade(
                "22099",
                "MK999",
                95.0
        );

        when(gradeRepository.update(
                "22099",
                "MK999",
                updatedGrade))
                .thenReturn(false);

        assertThrows(GradeException.class,
                () -> service.updateGrade(
                        "22099",
                        "MK999",
                        updatedGrade
                ));

        verify(gradeRepository, times(1))
                .update(
                        "22099",
                        "MK999",
                        updatedGrade
                );
    }

    @Test
    void deleteGrade_success() {
        when(gradeRepository.delete(
                "22001",
                "MK001"))
                .thenReturn(true);

        assertDoesNotThrow(() ->
                service.deleteGrade(
                        "22001",
                        "MK001"
                ));

        verify(gradeRepository, times(1))
                .delete(
                        "22001",
                        "MK001"
                );
    }

    @Test
    void deleteGrade_notFound() {
        when(gradeRepository.delete(
                "22099",
                "MK999"))
                .thenReturn(false);

        assertThrows(GradeException.class,
                () -> service.deleteGrade(
                        "22099",
                        "MK999"
                ));
    }

    @Test
    void getGradesByStudentNim_success() {
        ArrayList<Grade> grades = new ArrayList<>();

        grades.add(sampleGrade("22001", "MK001", 85.0));
        grades.add(sampleGrade("22001", "MK002", 90.0));

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(gradeRepository.findByStudentNim("22001"))
                .thenReturn(grades);

        ArrayList<Grade> result =
                service.getGradesByStudentNim("22001");

        assertEquals(2, result.size());
        assertEquals("22001", result.get(0).getStudentNim());
    }

    @Test
    void getGradesByStudentNim_studentNotFound() {
        when(studentRepository.findByNim("22099"))
                .thenReturn(null);

        assertThrows(GradeException.class,
                () -> service.getGradesByStudentNim("22099"));
    }

    @Test
    void calculateGpaByStudentNim_success() {
        ArrayList<Grade> grades = new ArrayList<>();

        grades.add(sampleGrade("22001", "MK001", 90.0));
        grades.add(sampleGrade("22001", "MK002", 80.0));

        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(gradeRepository.findByStudentNim("22001"))
                .thenReturn(grades);

        double result =
                service.calculateGpaByStudentNim("22001");

        assertTrue(result > 0.0);
    }

    @Test
    void calculateGpaByStudentNim_emptyGrades() {
        when(studentRepository.findByNim("22001"))
                .thenReturn(sampleStudent());

        when(gradeRepository.findByStudentNim("22001"))
                .thenReturn(new ArrayList<>());

        double result =
                service.calculateGpaByStudentNim("22001");

        assertEquals(0.0, result);
    }
}