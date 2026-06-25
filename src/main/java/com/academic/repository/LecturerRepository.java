package com.academic.repository;

import com.academic.model.Lecturer;
import com.academic.util.LecturerFileStorage;

import java.util.ArrayList;

public class LecturerRepository {

    private static LecturerRepository instance;

    private final ArrayList<Lecturer> lecturers = LecturerFileStorage.load();
    private Long sequence = getNextSequence();

    private Long getNextSequence() {
        Long maxId = 0L;

        for (Lecturer lecturer : lecturers) {
            if (lecturer.getId() > maxId) {
                maxId = lecturer.getId();
            }
        }

        return maxId + 1;
    }

    private LecturerRepository() {
    }

    public static LecturerRepository getInstance() {
        if (instance == null) {
            instance = new LecturerRepository();
        }

        return instance;
    }

    public void save(Lecturer lecturer) {
        lecturer.setId(sequence);
        sequence++;
        lecturers.add(lecturer);
        LecturerFileStorage.save(lecturers);
    }

    public ArrayList<Lecturer> findAll() {
        return lecturers;
    }

    public Lecturer findByNidn(String nidn) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getNidn().equals(nidn)) {
                return lecturer;
            }
        }

        return null;
    }

    public boolean update(String nidn, Lecturer updatedLecturer) {
        Lecturer existingLecturer = findByNidn(nidn);

        if (existingLecturer == null) {
            return false;
        }

        existingLecturer.setName(updatedLecturer.getName());
        existingLecturer.setGender(updatedLecturer.getGender());
        existingLecturer.setDepartment(updatedLecturer.getDepartment());
        existingLecturer.setEmail(updatedLecturer.getEmail());
        existingLecturer.setPhone(updatedLecturer.getPhone());

        LecturerFileStorage.save(lecturers);
        return true;
    }

    public boolean delete(String nidn) {
        Lecturer lecturer = findByNidn(nidn);

        if (lecturer == null) {
            return false;
        }

        lecturers.remove(lecturer);
        LecturerFileStorage.save(lecturers);
        return true;
    }
}