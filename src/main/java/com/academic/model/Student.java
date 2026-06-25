package com.academic.model;

public class Student {

    // =========================
    // Fields
    // =========================

    private Long id;
    private String nim;
    private String name;
    private String gender;
    private String major;
    private Integer semester;
    private String email;
    private String phone;

    // =========================
    // Constructors
    // =========================

    public Student() {
    }

    public Student(String nim,
                   String name,
                   String gender,
                   String major,
                   Integer semester,
                   String email,
                   String phone) {

        this.nim = nim;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.semester = semester;
        this.email = email;
        this.phone = phone;
    }

    public Student(Long id,
                   String nim,
                   String name,
                   String gender,
                   String major,
                   Integer semester,
                   String email,
                   String phone) {

        this.id = id;
        this.nim = nim;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.semester = semester;
        this.email = email;
        this.phone = phone;
    }

    // =========================
    // Getter & Setter
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // =========================
    // Override
    // =========================

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", nim='" + nim + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", major='" + major + '\'' +
                ", semester=" + semester +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}