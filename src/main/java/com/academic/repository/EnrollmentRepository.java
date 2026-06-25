package com.academic.repository;

import com.academic.model.Enrollment;

import java.util.ArrayList;

public class EnrollmentRepository {

    private static EnrollmentRepository instance;

    private final ArrayList<Enrollment> enrollments = new ArrayList<>();
    private Long sequence = 1L;

    private EnrollmentRepository() {
    }

    public static EnrollmentRepository getInstance() {
        if (instance == null) {
            instance = new EnrollmentRepository();
        }

        return instance;
    }

    public void save(Enrollment enrollment) {
        enrollment.setId(sequence);
        sequence++;
        enrollments.add(enrollment);
    }

    public ArrayList<Enrollment> findAll() {
        return enrollments;
    }

    public Enrollment findByStudentNimAndCourseCode(String studentNim, String courseCode) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentNim().equalsIgnoreCase(studentNim)
                    && enrollment.getCourseCode().equalsIgnoreCase(courseCode)) {
                return enrollment;
            }
        }

        return null;
    }

    public ArrayList<Enrollment> findByStudentNim(String studentNim) {
        ArrayList<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentNim().equalsIgnoreCase(studentNim)) {
                result.add(enrollment);
            }
        }

        return result;
    }

    public boolean delete(String studentNim, String courseCode) {
        Enrollment enrollment = findByStudentNimAndCourseCode(studentNim, courseCode);

        if (enrollment == null) {
            return false;
        }

        enrollments.remove(enrollment);
        return true;
    }
}