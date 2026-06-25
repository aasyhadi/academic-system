package com.academic.model;

public class Lecturer {

    private Long id;
    private String nidn;
    private String name;
    private String gender;
    private String department;
    private String email;
    private String phone;

    public Lecturer() {
    }

    public Lecturer(String nidn, String name, String gender,
                    String department, String email, String phone) {
        this.nidn = nidn;
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getNidn() {
        return nidn;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}