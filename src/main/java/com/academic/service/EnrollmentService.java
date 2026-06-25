package com.academic.service;

import com.academic.exception.EnrollmentException;
import com.academic.model.Enrollment;
import com.academic.repository.CourseRepository;
import com.academic.repository.EnrollmentRepository;
import com.academic.repository.StudentRepository;
import com.academic.validation.EnrollmentValidator;

import java.util.ArrayList;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentValidator validator;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository,
                             EnrollmentValidator validator) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.validator = validator;
    }

    public void addEnrollment(Enrollment enrollment) {
        String validationMessage = validator.validateForCreate(enrollment);

        if (validationMessage != null) {
            throw new EnrollmentException(validationMessage);
        }

        if (studentRepository.findByNim(enrollment.getStudentNim()) == null) {
            throw new EnrollmentException("Mahasiswa tidak ditemukan.");
        }

        if (courseRepository.findByCode(enrollment.getCourseCode()) == null) {
            throw new EnrollmentException("Mata kuliah tidak ditemukan.");
        }

        if (enrollmentRepository.findByStudentNimAndCourseCode(
                enrollment.getStudentNim(),
                enrollment.getCourseCode()
        ) != null) {
            throw new EnrollmentException("Mahasiswa sudah mengambil mata kuliah ini.");
        }

        enrollmentRepository.save(enrollment);
    }

    public ArrayList<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public ArrayList<Enrollment> getEnrollmentsByStudentNim(String studentNim) {
        if (studentRepository.findByNim(studentNim) == null) {
            throw new EnrollmentException("Mahasiswa tidak ditemukan.");
        }

        return enrollmentRepository.findByStudentNim(studentNim);
    }

    public void deleteEnrollment(String studentNim, String courseCode) {
        if (!enrollmentRepository.delete(studentNim, courseCode)) {
            throw new EnrollmentException("Data KRS tidak ditemukan.");
        }
    }
}