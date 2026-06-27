package com.academic.repository.interfaces;

import com.academic.model.Grade;

import java.util.ArrayList;

public interface IGradeRepository {

    void save(Grade grade);

    ArrayList<Grade> findAll();

    Grade findByStudentNimAndCourseCode(
            String nim,
            String courseCode
    );

    ArrayList<Grade> findByStudentNim(String nim);

    ArrayList<Grade> findByCourseCode(String courseCode);

    boolean update(
            String nim,
            String courseCode,
            Grade grade
    );

    boolean delete(
            String nim,
            String courseCode
    );
}