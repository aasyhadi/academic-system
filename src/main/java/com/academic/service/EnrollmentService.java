package com.academic.service;

import com.academic.exception.EnrollmentException;
import com.academic.model.Enrollment;
import com.academic.validation.EnrollmentValidator;
import com.academic.repository.interfaces.IEnrollmentRepository;
import com.academic.repository.interfaces.IStudentRepository;
import com.academic.repository.interfaces.ICourseRepository;
import com.academic.constant.MessageConstant;

import java.util.ArrayList;

public class EnrollmentService {

    private final IEnrollmentRepository enrollmentRepository;
    private final IStudentRepository studentRepository;
    private final ICourseRepository courseRepository;
    private final EnrollmentValidator validator;

    public EnrollmentService(
            IEnrollmentRepository enrollmentRepository,
            IStudentRepository studentRepository,
            ICourseRepository courseRepository,
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
            throw new EnrollmentException(MessageConstant.STUDENT_NOT_FOUND);
        }

        if (courseRepository.findByCode(enrollment.getCourseCode()) == null) {
            throw new EnrollmentException(MessageConstant.COURSE_NOT_FOUND);
        }

        if (enrollmentRepository.findByStudentNimAndCourseCode(
                enrollment.getStudentNim(),
                enrollment.getCourseCode()
        ) != null) {
            throw new EnrollmentException(MessageConstant.ENROLLMENT_ALREADY_EXISTS);
        }

        enrollmentRepository.save(enrollment);
    }

    public ArrayList<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public ArrayList<Enrollment> getEnrollmentsByStudentNim(String studentNim) {
        if (studentRepository.findByNim(studentNim) == null) {
            throw new EnrollmentException(MessageConstant.STUDENT_NOT_FOUND);
        }

        return enrollmentRepository.findByStudentNim(studentNim);
    }

    public void deleteEnrollment(String studentNim, String courseCode) {
        if (!enrollmentRepository.delete(studentNim, courseCode)) {
            throw new EnrollmentException(MessageConstant.ENROLLMENT_NOT_FOUND);
        }
    }
}