package com.academic.repository;

import com.academic.model.Enrollment;
import com.academic.repository.interfaces.IEnrollmentRepository;
import com.academic.util.EnrollmentFileStorage;

import java.util.ArrayList;

public class EnrollmentRepository implements IEnrollmentRepository {

    private static EnrollmentRepository instance;

    private final ArrayList<Enrollment> enrollments =
            EnrollmentFileStorage.load();

    private Long sequence = getNextSequence();

    private EnrollmentRepository() {
    }

    public static EnrollmentRepository getInstance() {
        if (instance == null) {
            instance = new EnrollmentRepository();
        }

        return instance;
    }

    private Long getNextSequence() {
        Long maxId = 0L;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() > maxId) {
                maxId = enrollment.getId();
            }
        }

        return maxId + 1;
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollment.setId(sequence);
        sequence++;
        enrollments.add(enrollment);
        EnrollmentFileStorage.save(enrollments);
    }

    @Override
    public ArrayList<Enrollment> findAll() {
        return enrollments;
    }

    @Override
    public Enrollment findByStudentNimAndCourseCode(
            String nim,
            String courseCode
    ) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentNim().equalsIgnoreCase(nim)
                    && enrollment.getCourseCode().equalsIgnoreCase(courseCode)) {
                return enrollment;
            }
        }

        return null;
    }

    @Override
    public ArrayList<Enrollment> findByStudentNim(String nim) {
        ArrayList<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentNim().equalsIgnoreCase(nim)) {
                result.add(enrollment);
            }
        }

        return result;
    }

    @Override
    public boolean delete(String nim, String courseCode) {
        Enrollment enrollment =
                findByStudentNimAndCourseCode(nim, courseCode);

        if (enrollment == null) {
            return false;
        }

        enrollments.remove(enrollment);
        EnrollmentFileStorage.save(enrollments);
        return true;
    }
}