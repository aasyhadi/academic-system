package com.academic.service;

import com.academic.exception.LecturerException;
import com.academic.model.Lecturer;
import com.academic.repository.interfaces.ILecturerRepository;
import com.academic.validation.LecturerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LecturerServiceTest {

    private ILecturerRepository repository;
    private LecturerService service;

    @BeforeEach
    void setup() {
        repository = mock(ILecturerRepository.class);
        service = new LecturerService(repository, new LecturerValidator());
    }

    private Lecturer sampleLecturer(String nidn, String name, String department) {
        return new Lecturer(
                nidn,
                name,
                "L",
                department,
                "test@mail.com",
                "08123456789"
        );
    }

    @Test
    void addLecturer_success() {
        Lecturer lecturer = sampleLecturer("10001", "Pak Ahmad", "Informatika");

        when(repository.findByNidn("10001")).thenReturn(null);

        assertDoesNotThrow(() -> service.addLecturer(lecturer));

        verify(repository, times(1)).save(lecturer);
    }

    @Test
    void addLecturer_duplicateNidn() {
        Lecturer lecturer = sampleLecturer("10001", "Pak Ahmad", "Informatika");

        when(repository.findByNidn("10001")).thenReturn(lecturer);

        assertThrows(LecturerException.class,
                () -> service.addLecturer(lecturer));

        verify(repository, never()).save(any(Lecturer.class));
    }

    @Test
    void addLecturer_emptyNidn() {
        Lecturer lecturer = sampleLecturer("", "Pak Ahmad", "Informatika");

        assertThrows(LecturerException.class,
                () -> service.addLecturer(lecturer));

        verify(repository, never()).save(any(Lecturer.class));
    }

    @Test
    void getLecturerByNidn_success() {
        Lecturer lecturer = sampleLecturer("10001", "Pak Ahmad", "Informatika");

        when(repository.findByNidn("10001")).thenReturn(lecturer);

        Lecturer result = service.getLecturerByNidn("10001");

        assertNotNull(result);
        assertEquals("10001", result.getNidn());
        assertEquals("Pak Ahmad", result.getName());
    }

    @Test
    void getLecturerByNidn_notFound() {
        when(repository.findByNidn("99999")).thenReturn(null);

        assertThrows(LecturerException.class,
                () -> service.getLecturerByNidn("99999"));
    }

    @Test
    void updateLecturer_success() {
        Lecturer existingLecturer =
                sampleLecturer("10001", "Pak Ahmad", "Informatika");

        Lecturer updatedLecturer =
                sampleLecturer("10001", "Pak Ahmad Santoso", "Sistem Informasi");

        when(repository.findByNidn("10001")).thenReturn(existingLecturer);
        when(repository.update("10001", updatedLecturer)).thenReturn(true);

        assertDoesNotThrow(() ->
                service.updateLecturer("10001", updatedLecturer));

        verify(repository, times(1)).update("10001", updatedLecturer);
    }

    @Test
    void deleteLecturer_success() {
        when(repository.delete("10001")).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteLecturer("10001"));

        verify(repository, times(1)).delete("10001");
    }

    @Test
    void deleteLecturer_notFound() {
        when(repository.delete("99999")).thenReturn(false);

        assertThrows(LecturerException.class,
                () -> service.deleteLecturer("99999"));
    }

    @Test
    void getAllLecturers_success() {
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        lecturers.add(sampleLecturer("10001", "Pak Ahmad", "Informatika"));
        lecturers.add(sampleLecturer("10002", "Bu Siti", "Sistem Informasi"));

        when(repository.findAll()).thenReturn(lecturers);

        ArrayList<Lecturer> result = service.getAllLecturers();

        assertEquals(2, result.size());
        assertEquals("Pak Ahmad", result.get(0).getName());
    }
}