package com.academic.service;

import com.academic.constant.MessageConstant;
import com.academic.exception.StudentException;
import com.academic.model.Student;
import com.academic.repository.StudentRepository;
import com.academic.validation.StudentValidator;

import java.util.ArrayList;
import java.util.Comparator;

public class StudentService {

    private final StudentRepository repository;
    private final StudentValidator validator;

    public StudentService(StudentRepository repository,
                          StudentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void addStudent(Student student) {
        String validationMessage = validator.validateForCreate(student);

        if (validationMessage != null) {
            throw new StudentException(validationMessage);
        }

        if (repository.findByNim(student.getNim()) != null) {
            throw new StudentException("NIM sudah terdaftar.");
        }

        repository.save(student);
    }

    public ArrayList<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentByNim(String nim) {
        Student student = repository.findByNim(nim);

        if (student == null) {
            throw new StudentException(MessageConstant.STUDENT_NOT_FOUND);
        }

        return student;
    }

    public void updateStudent(String nim, Student updatedStudent) {
        String validationMessage = validator.validateForUpdate(updatedStudent);

        if (validationMessage != null) {
            throw new StudentException(validationMessage);
        }

        if (repository.findByNim(nim) == null) {
            throw new StudentException(MessageConstant.STUDENT_NOT_FOUND);
        }

        repository.update(nim, updatedStudent);
    }

    public void deleteStudent(String nim) {
        if (!repository.delete(nim)) {
            throw new StudentException(MessageConstant.STUDENT_NOT_FOUND);
        }
    }

    public ArrayList<Student> searchStudentsByName(String keyword) {
        return repository.searchByName(keyword);
    }

    public ArrayList<Student> sortStudentsByNim() {
        ArrayList<Student> result = new ArrayList<>(repository.findAll());
        result.sort(Comparator.comparing(Student::getNim));
        return result;
    }

    public ArrayList<Student> sortStudentsByName() {
        ArrayList<Student> result = new ArrayList<>(repository.findAll());
        result.sort(Comparator.comparing(Student::getName));
        return result;
    }

    public ArrayList<Student> sortStudentsBySemester() {
        ArrayList<Student> result = new ArrayList<>(repository.findAll());
        result.sort(Comparator.comparing(Student::getSemester));
        return result;
    }

    public ArrayList<Student> searchStudentsByMajor(String major) {
        return repository.searchByMajor(major);
    }

}