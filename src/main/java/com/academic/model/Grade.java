package com.academic.model;

public class Grade {

    private Long id;
    private String studentNim;
    private String courseCode;
    private Double score;

    public Grade() {
    }

    public Grade(String studentNim, String courseCode, Double score) {
        this.studentNim = studentNim;
        this.courseCode = courseCode;
        this.score = score;
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

    public Double getScore() {
        return score;
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

    public void setScore(Double score) {
        this.score = score;
    }
}