package com.academic.validation;

import com.academic.model.Lecturer;
import com.academic.util.ValidationUtil;

public class LecturerValidator {

    public String validateForCreate(Lecturer lecturer) {
        if (ValidationUtil.isBlank(lecturer.getNidn())) {
            return "NIDN tidak boleh kosong.";
        }

        if (ValidationUtil.isBlank(lecturer.getName())) {
            return "Nama dosen tidak boleh kosong.";
        }

        if (ValidationUtil.isInvalidGender(lecturer.getGender())) {
            return "Gender harus L atau P.";
        }

        if (ValidationUtil.isBlank(lecturer.getDepartment())) {
            return "Departemen tidak boleh kosong.";
        }

        if (ValidationUtil.isInvalidEmail(lecturer.getEmail())) {
            return "Email tidak valid.";
        }

        return null;
    }

    public String validateForUpdate(Lecturer lecturer) {
        return validateForCreate(lecturer);
    }
}