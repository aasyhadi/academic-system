package com.academic.repository.interfaces;

import com.academic.model.Course;

import java.util.ArrayList;

public interface ICourseRepository {

    void save(Course course);

    ArrayList<Course> findAll();

    Course findByCode(String courseCode);

    boolean update(String courseCode, Course course);

    boolean delete(String courseCode);
}