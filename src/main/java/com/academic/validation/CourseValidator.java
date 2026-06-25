package com.academic.validation;

import com.academic.model.Course;
import com.academic.util.ValidationUtil;

public class CourseValidator {

    public String validateForCreate(Course course) {
        if (ValidationUtil.isBlank(course.getCode())) {
            return "Kode mata kuliah tidak boleh kosong.";
        }

        if (ValidationUtil.isBlank(course.getName())) {
            return "Nama mata kuliah tidak boleh kosong.";
        }

        if (course.getCredit() == null || course.getCredit() < 1 || course.getCredit() > 6) {
            return "SKS harus antara 1 sampai 6.";
        }

        if (ValidationUtil.isBlank(course.getSemester())) {
            return "Semester tidak boleh kosong.";
        }

        return null;
    }

    public String validateForUpdate(Course course) {
        return validateForCreate(course);
    }
}