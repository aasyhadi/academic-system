package com.academic.model;

public class Course {

    private Long id;
    private String code;
    private String name;
    private Integer credit;
    private String semester;

    public Course() {
    }

    public Course(String code, String name, Integer credit, String semester) {
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getCredit() {
        return credit;
    }

    public String getSemester() {
        return semester;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}