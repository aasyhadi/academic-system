package com.academic.repository;

import com.academic.model.Course;
import com.academic.util.CourseFileStorage;
import com.academic.repository.interfaces.ICourseRepository;

import java.util.ArrayList;

public class CourseRepository
        implements ICourseRepository {

    private static CourseRepository instance;

    private final ArrayList<Course> courses = CourseFileStorage.load();
    private Long sequence = getNextSequence();

    private Long getNextSequence() {
        Long maxId = 0L;

        for (Course course : courses) {
            if (course.getId() > maxId) {
                maxId = course.getId();
            }
        }

        return maxId + 1;
    }

    private CourseRepository() {
    }

    public static CourseRepository getInstance() {
        if (instance == null) {
            instance = new CourseRepository();
        }

        return instance;
    }

    @Override
    public void save(Course course) {
        course.setId(sequence);
        sequence++;
        courses.add(course);
        CourseFileStorage.save(courses);
    }

    @Override
    public ArrayList<Course> findAll() {
        return courses;
    }

    @Override
    public Course findByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }

        return null;
    }

    @Override
    public boolean update(String code, Course updatedCourse) {
        Course existingCourse = findByCode(code);

        if (existingCourse == null) {
            return false;
        }

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setCredit(updatedCourse.getCredit());
        existingCourse.setSemester(updatedCourse.getSemester());

        CourseFileStorage.save(courses);
        return true;
    }

    @Override
    public boolean delete(String code) {
        Course course = findByCode(code);

        if (course == null) {
            return false;
        }

        courses.remove(course);
        CourseFileStorage.save(courses);
        return true;
    }
}