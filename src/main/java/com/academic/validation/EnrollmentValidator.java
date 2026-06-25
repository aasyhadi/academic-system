package com.academic.validation;

import com.academic.model.Enrollment;
import com.academic.util.ValidationUtil;

public class EnrollmentValidator {

    public String validateForCreate(Enrollment enrollment) {
        if (ValidationUtil.isBlank(enrollment.getStudentNim())) {
            return "NIM mahasiswa tidak boleh kosong.";
        }

        if (ValidationUtil.isBlank(enrollment.getCourseCode())) {
            return "Kode mata kuliah tidak boleh kosong.";
        }

        return null;
    }
}