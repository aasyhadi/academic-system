package com.academic.repository;

import com.academic.model.Grade;

import java.util.ArrayList;

public class GradeRepository {

    private static GradeRepository instance;

    private final ArrayList<Grade> grades = new ArrayList<>();
    private Long sequence = 1L;

    private GradeRepository() {
    }

    public static GradeRepository getInstance() {
        if (instance == null) {
            instance = new GradeRepository();
        }

        return instance;
    }

    public void save(Grade grade) {
        grade.setId(sequence);
        sequence++;
        grades.add(grade);
    }

    public ArrayList<Grade> findAll() {
        return grades;
    }

    public Grade findByStudentNimAndCourseCode(String nim, String courseCode) {
        for (Grade grade : grades) {
            if (grade.getStudentNim().equalsIgnoreCase(nim)
                    && grade.getCourseCode().equalsIgnoreCase(courseCode)) {
                return grade;
            }
        }

        return null;
    }

    public boolean update(String nim, String courseCode, Grade updatedGrade) {
        Grade existingGrade = findByStudentNimAndCourseCode(nim, courseCode);

        if (existingGrade == null) {
            return false;
        }

        existingGrade.setScore(updatedGrade.getScore());
        return true;
    }

    public boolean delete(String nim, String courseCode) {
        Grade grade = findByStudentNimAndCourseCode(nim, courseCode);

        if (grade == null) {
            return false;
        }

        grades.remove(grade);
        return true;
    }
}