package com.academic.repository.interfaces;

import com.academic.model.Student;

import java.util.ArrayList;

public interface IStudentRepository {

    void save(Student student);

    ArrayList<Student> findAll();

    Student findByNim(String nim);

    boolean update(String nim, Student student);

    boolean delete(String nim);

    ArrayList<Student> searchByName(String keyword);

    ArrayList<Student> searchByMajor(String major);

}