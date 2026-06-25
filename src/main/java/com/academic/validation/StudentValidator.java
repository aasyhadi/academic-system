package com.academic.validation;

import com.academic.model.Student;
import com.academic.util.ValidationUtil;

public class StudentValidator {

    public String validateForCreate(Student student) {
        if (ValidationUtil.isBlank(student.getNim())) {
            return "NIM tidak boleh kosong.";
        }

        if (ValidationUtil.isBlank(student.getName())) {
            return "Nama tidak boleh kosong.";
        }

        if (ValidationUtil.isInvalidGender(student.getGender())) {
            return "Gender harus L atau P.";
        }

        if (ValidationUtil.isBlank(student.getMajor())) {
            return "Jurusan tidak boleh kosong.";
        }

        if (ValidationUtil.isInvalidSemester(student.getSemester())) {
            return "Semester harus antara 1 sampai 14.";
        }

        if (ValidationUtil.isInvalidEmail(student.getEmail())) {
            return "Email tidak valid.";
        }

        return null;
    }

    public String validateForUpdate(Student student) {
        return validateForCreate(student);
    }
}