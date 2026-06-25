package com.academic.repository;

import com.academic.model.Enrollment;
import com.academic.util.EnrollmentFileStorage;
import java.util.ArrayList;

public class EnrollmentRepository {

    private static EnrollmentRepository instance;

    private final ArrayList<Enrollment> enrollments = EnrollmentFileStorage.load();
    private Long sequence = getNextSequence();

    private Long getNextSequence() {
        Long maxId = 0L;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() > maxId) {
                maxId = enrollment.getId();
            }
        }

        return maxId + 1;
    }

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
        EnrollmentFileStorage.save(enrollments);
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
        EnrollmentFileStorage.save(enrollments);
        return true;
    }
}