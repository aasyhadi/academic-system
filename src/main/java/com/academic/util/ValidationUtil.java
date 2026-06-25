package com.academic.util;
import com.academic.enums.Gender;

public class ValidationUtil {

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isInvalidSemester(Integer semester) {
        return semester == null || semester < 1 || semester > 14;
    }

    public static boolean isInvalidGender(String gender) {
        return gender == null || !Gender.isValid(gender);
    }

    public static boolean isInvalidEmail(String email) {
        return email == null || !email.contains("@");
    }
}