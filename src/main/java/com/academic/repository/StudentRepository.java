package com.academic.repository;

import com.academic.model.Student;
import com.academic.util.StudentFileStorage;
import java.util.ArrayList;

public class StudentRepository {

    private static StudentRepository instance;

    private final ArrayList<Student> students = StudentFileStorage.load();
    private Long sequence = getNextSequence();

    private Long getNextSequence() {
        Long maxId = 0L;

        for (Student student : students) {
            if (student.getId() > maxId) {
                maxId = student.getId();
            }
        }

        return maxId + 1;
    }

    private StudentRepository() {
    }

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }

        return instance;
    }

    public void save(Student student) {
        student.setId(sequence);
        sequence++;
        students.add(student);
        StudentFileStorage.save(students);
    }

    public ArrayList<Student> findAll() {
        return students;
    }

    public Student findByNim(String nim) {
        for (Student student : students) {
            if (student.getNim().equals(nim)) {
                return student;
            }
        }

        return null;
    }

    public boolean update(String nim, Student updatedStudent) {
        Student existingStudent = findByNim(nim);

        if (existingStudent == null) {
            return false;
        }

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setGender(updatedStudent.getGender());
        existingStudent.setMajor(updatedStudent.getMajor());
        existingStudent.setSemester(updatedStudent.getSemester());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhone(updatedStudent.getPhone());

        StudentFileStorage.save(students);
        return true;
    }

    public boolean delete(String nim) {
        Student student = findByNim(nim);

        if (student == null) {
            return false;
        }

        students.remove(student);
        StudentFileStorage.save(students);
        return true;
    }
}