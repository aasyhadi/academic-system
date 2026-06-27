package com.academic.service;

import com.academic.exception.StudentException;
import com.academic.model.Student;
import com.academic.repository.interfaces.IStudentRepository;
import com.academic.validation.StudentValidator;
import com.academic.service.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private IStudentRepository repository;
    private StudentService service;

    @BeforeEach
    void setup() {
        repository = mock(IStudentRepository.class);
        service = new StudentService(repository, new StudentValidator());
    }

    private Student sampleStudent(String nim, String name, String major, int semester) {
        return new Student(
                nim,
                name,
                "L",
                major,
                semester,
                "test@mail.com",
                "08123456789"
        );
    }

    @Test
    void addStudent_success() {
        Student student = sampleStudent("22001", "Budi", "Informatika", 3);

        when(repository.findByNim("22001")).thenReturn(null);

        assertDoesNotThrow(() -> service.addStudent(student));

        verify(repository, times(1)).save(student);
    }

    @Test
    void addStudent_duplicateNim() {
        Student student = sampleStudent("22001", "Budi", "Informatika", 3);

        when(repository.findByNim("22001")).thenReturn(student);

        assertThrows(StudentException.class, () -> service.addStudent(student));

        verify(repository, never()).save(any(Student.class));
    }

    @Test
    void addStudent_emptyNim() {
        Student student = sampleStudent("", "Budi", "Informatika", 3);

        assertThrows(StudentException.class, () -> service.addStudent(student));

        verify(repository, never()).save(any(Student.class));
    }

    @Test
    void getStudentByNim_success() {
        Student student = sampleStudent("22001", "Budi", "Informatika", 3);

        when(repository.findByNim("22001")).thenReturn(student);

        Student result = service.getStudentByNim("22001");

        assertNotNull(result);
        assertEquals("22001", result.getNim());
        assertEquals("Budi", result.getName());
    }

    @Test
    void getStudentByNim_notFound() {
        when(repository.findByNim("99999")).thenReturn(null);

        assertThrows(StudentException.class,
                () -> service.getStudentByNim("99999"));
    }

    @Test
    void updateStudent_success() {
        Student existingStudent =
                sampleStudent("22001", "Budi", "Informatika", 3);

        Student updatedStudent =
                sampleStudent("22001", "Budi Santoso", "Sistem Informasi", 4);

        when(repository.findByNim("22001")).thenReturn(existingStudent);
        when(repository.update("22001", updatedStudent)).thenReturn(true);

        assertDoesNotThrow(() ->
                service.updateStudent("22001", updatedStudent));

        verify(repository, times(1)).update("22001", updatedStudent);
    }

    @Test
    void deleteStudent_success() {
        when(repository.delete("22001")).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteStudent("22001"));

        verify(repository, times(1)).delete("22001");
    }

    @Test
    void deleteStudent_notFound() {
        when(repository.delete("99999")).thenReturn(false);

        assertThrows(StudentException.class,
                () -> service.deleteStudent("99999"));
    }

    @Test
    void searchStudentsByName_success() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(sampleStudent("22001", "Budi", "Informatika", 3));

        when(repository.searchByName("Bud")).thenReturn(students);

        ArrayList<Student> result = service.searchStudentsByName("Bud");

        assertEquals(1, result.size());
        assertEquals("Budi", result.get(0).getName());
    }

    @Test
    void searchStudentsByMajor_success() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(sampleStudent("22001", "Budi", "Informatika", 3));

        when(repository.searchByMajor("Informatika")).thenReturn(students);

        ArrayList<Student> result =
                service.searchStudentsByMajor("Informatika");

        assertEquals(1, result.size());
        assertEquals("Informatika", result.get(0).getMajor());
    }

    @Test
    void sortStudentsByNim_success() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(sampleStudent("22003", "Citra", "Informatika", 5));
        students.add(sampleStudent("22001", "Budi", "Informatika", 3));
        students.add(sampleStudent("22002", "Andi", "Sistem Informasi", 2));

        when(repository.findAll()).thenReturn(students);

        ArrayList<Student> result = service.sortStudentsByNim();

        assertEquals("22001", result.get(0).getNim());
        assertEquals("22002", result.get(1).getNim());
        assertEquals("22003", result.get(2).getNim());
    }

    @Test
    void getStudentsByPage_success() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(sampleStudent("22001", "Budi", "Informatika", 1));
        students.add(sampleStudent("22002", "Andi", "Informatika", 2));
        students.add(sampleStudent("22003", "Citra", "Informatika", 3));

        when(repository.findAll()).thenReturn(students);

        ArrayList<Student> result = service.getStudentsByPage(1, 2);

        assertEquals(2, result.size());
        assertEquals("22001", result.get(0).getNim());
        assertEquals("22002", result.get(1).getNim());
    }

    @Test
    void getTotalStudentPages_success() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(sampleStudent("22001", "Budi", "Informatika", 1));
        students.add(sampleStudent("22002", "Andi", "Informatika", 2));
        students.add(sampleStudent("22003", "Citra", "Informatika", 3));

        when(repository.findAll()).thenReturn(students);

        int totalPages = service.getTotalStudentPages(2);

        assertEquals(2, totalPages);
    }
}