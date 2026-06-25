package com.academic.validation;

import com.academic.model.Grade;
import com.academic.util.ValidationUtil;

public class GradeValidator {

    public String validateForCreate(Grade grade) {
        if (ValidationUtil.isBlank(grade.getStudentNim())) {
            return "NIM mahasiswa tidak boleh kosong.";
        }

        if (ValidationUtil.isBlank(grade.getCourseCode())) {
            return "Kode mata kuliah tidak boleh kosong.";
        }

        if (grade.getScore() == null || grade.getScore() < 0 || grade.getScore() > 100) {
            return "Nilai harus antara 0 sampai 100.";
        }

        return null;
    }

    public String validateForUpdate(Grade grade) {
        return validateForCreate(grade);
    }
}