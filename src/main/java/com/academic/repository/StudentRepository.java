package com.academic.repository;

import com.academic.model.Student;
import com.academic.util.StudentFileStorage;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.academic.repository.interfaces.IStudentRepository;

public class StudentRepository implements IStudentRepository {

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

    @Override
    public void save(Student student) {
        student.setId(sequence);
        sequence++;
        students.add(student);
        StudentFileStorage.save(students);
    }

    @Override
    public ArrayList<Student> findAll() {
        return students;
    }

    @Override
    public Student findByNim(String nim) {
        for (Student student : students) {
            if (student.getNim().equals(nim)) {
                return student;
            }
        }

        return null;
    }

    @Override
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

    @Override
    public ArrayList<Student> searchByName(String keyword) {
        ArrayList<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(student);
            }
        }

        return result;
    }

    @Override
    public ArrayList<Student> searchByMajor(String major) {
        return students.stream()
                .filter(student ->
                        student.getMajor()
                                .equalsIgnoreCase(major))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
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