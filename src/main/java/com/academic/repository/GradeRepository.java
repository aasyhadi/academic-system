package com.academic.repository;

import com.academic.model.Grade;
import com.academic.util.GradeFileStorage;
import java.util.ArrayList;
import com.academic.repository.interfaces.IGradeRepository;

public class GradeRepository implements IGradeRepository {

    private static GradeRepository instance;

    private final ArrayList<Grade> grades = GradeFileStorage.load();
    private Long sequence = getNextSequence();

    private Long getNextSequence() {
        Long maxId = 0L;

        for (Grade grade : grades) {
            if (grade.getId() > maxId) {
                maxId = grade.getId();
            }
        }

        return maxId + 1;
    }

    private GradeRepository() {
    }

    public static GradeRepository getInstance() {
        if (instance == null) {
            instance = new GradeRepository();
        }

        return instance;
    }

    @Override
    public void save(Grade grade) {
        grade.setId(sequence);
        sequence++;
        grades.add(grade);
        GradeFileStorage.save(grades);
    }

    @Override
    public ArrayList<Grade> findAll() {
        return grades;
    }

    @Override
    public Grade findByStudentNimAndCourseCode(String nim, String courseCode) {
        for (Grade grade : grades) {
            if (grade.getStudentNim().equalsIgnoreCase(nim)
                    && grade.getCourseCode().equalsIgnoreCase(courseCode)) {
                return grade;
            }
        }

        return null;
    }

    @Override
    public boolean update(String nim, String courseCode, Grade updatedGrade) {
        Grade existingGrade = findByStudentNimAndCourseCode(nim, courseCode);

        if (existingGrade == null) {
            return false;
        }

        existingGrade.setScore(updatedGrade.getScore());
        existingGrade.setLetter(updatedGrade.getLetter());
        GradeFileStorage.save(grades);
        return true;
    }

    @Override
    public boolean delete(String nim, String courseCode) {
        Grade grade = findByStudentNimAndCourseCode(nim, courseCode);

        if (grade == null) {
            return false;
        }

        grades.remove(grade);
        GradeFileStorage.save(grades);
        return true;
    }

    @Override
    public ArrayList<Grade> findByStudentNim(String nim) {
        ArrayList<Grade> result = new ArrayList<>();

        for (Grade grade : grades) {
            if (grade.getStudentNim().equalsIgnoreCase(nim)) {
                result.add(grade);
            }
        }

        return result;
    }

    @Override
    public ArrayList<Grade> findByCourseCode(String courseCode) {
        ArrayList<Grade> result = new ArrayList<>();

        for (Grade grade : grades) {
            if (grade.getCourseCode().equalsIgnoreCase(courseCode)) {
                result.add(grade);
            }
        }

        return result;
    }


}