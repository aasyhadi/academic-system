package com.academic.model;

public class Enrollment {

    private Long id;
    private String studentNim;
    private String courseCode;

    public Enrollment() {
    }

    public Enrollment(String studentNim, String courseCode) {
        this.studentNim = studentNim;
        this.courseCode = courseCode;
    }

    public Long getId() {
        return id;
    }

    public String getStudentNim() {
        return studentNim;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentNim(String studentNim) {
        this.studentNim = studentNim;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}