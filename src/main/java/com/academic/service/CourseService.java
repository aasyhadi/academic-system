package com.academic.service;

import com.academic.exception.CourseException;
import com.academic.model.Course;
import com.academic.repository.CourseRepository;
import com.academic.validation.CourseValidator;
import com.academic.constant.MessageConstant;

import java.util.ArrayList;

public class CourseService {

    private final CourseRepository repository;
    private final CourseValidator validator;

    public CourseService(CourseRepository repository,
                         CourseValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void addCourse(Course course) {
        String validationMessage = validator.validateForCreate(course);

        if (validationMessage != null) {
            throw new CourseException(validationMessage);
        }

        if (repository.findByCode(course.getCode()) != null) {
            throw new CourseException("Kode mata kuliah sudah terdaftar.");
        }

        repository.save(course);
    }

    public ArrayList<Course> getAllCourses() {
        return repository.findAll();
    }

    public Course getCourseByCode(String code) {
        Course course = repository.findByCode(code);

        if (course == null) {
            throw new CourseException(MessageConstant.COURSE_NOT_FOUND);
        }

        return course;
    }

    public void updateCourse(String code, Course updatedCourse) {
        String validationMessage = validator.validateForUpdate(updatedCourse);

        if (validationMessage != null) {
            throw new CourseException(validationMessage);
        }

        if (!repository.update(code, updatedCourse)) {
            throw new CourseException(MessageConstant.COURSE_NOT_FOUND);
        }
    }

    public void deleteCourse(String code) {
        if (!repository.delete(code)) {
            throw new CourseException(MessageConstant.COURSE_NOT_FOUND);
        }
    }
}