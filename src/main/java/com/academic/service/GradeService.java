package com.academic.service;

import com.academic.exception.GradeException;
import com.academic.model.Grade;
import com.academic.validation.GradeValidator;
import com.academic.constant.MessageConstant;
import com.academic.util.GradeUtil;
import com.academic.util.GpaUtil;
import com.academic.repository.interfaces.IGradeRepository;
import com.academic.repository.interfaces.IStudentRepository;
import com.academic.repository.interfaces.ICourseRepository;

import java.util.ArrayList;

public class GradeService {

    private final IGradeRepository gradeRepository;
    private final IStudentRepository studentRepository;
    private final ICourseRepository courseRepository;
    private final GradeValidator validator;

    public GradeService(
            IGradeRepository gradeRepository,
            IStudentRepository studentRepository,
            ICourseRepository courseRepository,
            GradeValidator validator) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.validator = validator;
    }

    public void addGrade(Grade grade) {
        String validationMessage = validator.validateForCreate(grade);

        if (validationMessage != null) {
            throw new GradeException(validationMessage);
        }

        if (studentRepository.findByNim(grade.getStudentNim()) == null) {
            throw new GradeException(MessageConstant.STUDENT_NOT_FOUND);
        }

        if (courseRepository.findByCode(grade.getCourseCode()) == null) {
            throw new GradeException(MessageConstant.COURSE_NOT_FOUND);
        }

        if (gradeRepository.findByStudentNimAndCourseCode(
                grade.getStudentNim(),
                grade.getCourseCode()
        ) != null) {
            throw new GradeException("Nilai untuk mahasiswa dan mata kuliah ini sudah ada.");
        }

        grade.setLetter(GradeUtil.toLetter(grade.getScore()));

        gradeRepository.save(grade);
    }

    public ArrayList<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGrade(String nim, String courseCode) {
        Grade grade = gradeRepository.findByStudentNimAndCourseCode(nim, courseCode);

        if (grade == null) {
            throw new GradeException(MessageConstant.GRADE_NOT_FOUND);
        }

        return grade;
    }

    public void updateGrade(String nim, String courseCode, Grade updatedGrade) {
        String validationMessage = validator.validateForUpdate(updatedGrade);

        if (validationMessage != null) {
            throw new GradeException(validationMessage);
        }

        updatedGrade.setLetter(GradeUtil.toLetter(updatedGrade.getScore()));

        if (!gradeRepository.update(nim, courseCode, updatedGrade)) {
            throw new GradeException(MessageConstant.GRADE_NOT_FOUND);
        }
    }

    public void deleteGrade(String nim, String courseCode) {
        if (!gradeRepository.delete(nim, courseCode)) {
            throw new GradeException(MessageConstant.GRADE_NOT_FOUND);
        }
    }

    public ArrayList<Grade> getGradesByStudentNim(String nim) {
        if (studentRepository.findByNim(nim) == null) {
            throw new GradeException(MessageConstant.STUDENT_NOT_FOUND);
        }

        return gradeRepository.findByStudentNim(nim);
    }

    public double calculateGpaByStudentNim(String nim) {
        ArrayList<Grade> studentGrades = getGradesByStudentNim(nim);

        if (studentGrades.isEmpty()) {
            return 0.0;
        }

        double totalPoint = 0.0;

        for (Grade grade : studentGrades) {
            totalPoint += GpaUtil.scoreToPoint(grade.getScore());
        }

        return totalPoint / studentGrades.size();
    }
}