package com.academic.repository.interfaces;

import com.academic.model.Enrollment;

import java.util.ArrayList;

public interface IEnrollmentRepository {

    void save(Enrollment enrollment);

    ArrayList<Enrollment> findAll();

    Enrollment findByStudentNimAndCourseCode(
            String nim,
            String courseCode
    );

    ArrayList<Enrollment> findByStudentNim(String nim);

    boolean delete(String nim, String courseCode);
}