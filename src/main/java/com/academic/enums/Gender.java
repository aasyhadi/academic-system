package com.academic.enums;

public enum Gender {

    L("L", "Laki-laki"),
    P("P", "Perempuan");

    private final String code;
    private final String label;

    Gender(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValid(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.getCode().equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}