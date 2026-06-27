package com.academic.service;

import com.academic.exception.LecturerException;
import com.academic.model.Lecturer;
import com.academic.repository.LecturerRepository;
import com.academic.validation.LecturerValidator;
import com.academic.constant.MessageConstant;
import com.academic.repository.interfaces.ILecturerRepository;

import java.util.ArrayList;

public class LecturerService {

    private final ILecturerRepository repository;
    private final LecturerValidator validator;

    public LecturerService(ILecturerRepository repository,
                           LecturerValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void addLecturer(Lecturer lecturer) {
        String validationMessage = validator.validateForCreate(lecturer);

        if (validationMessage != null) {
            throw new LecturerException(validationMessage);
        }

        if (repository.findByNidn(lecturer.getNidn()) != null) {
            throw new LecturerException("NIDN sudah terdaftar.");
        }

        repository.save(lecturer);
    }

    public ArrayList<Lecturer> getAllLecturers() {
        return repository.findAll();
    }

    public Lecturer getLecturerByNidn(String nidn) {
        Lecturer lecturer = repository.findByNidn(nidn);

        if (lecturer == null) {
            throw new LecturerException(MessageConstant.LECTURER_NOT_FOUND);
        }

        return lecturer;
    }

    public void updateLecturer(String nidn, Lecturer updatedLecturer) {
        String validationMessage = validator.validateForUpdate(updatedLecturer);

        if (validationMessage != null) {
            throw new LecturerException(validationMessage);
        }

        if (!repository.update(nidn, updatedLecturer)) {
            throw new LecturerException(MessageConstant.LECTURER_NOT_FOUND);
        }
    }

    public void deleteLecturer(String nidn) {
        if (!repository.delete(nidn)) {
            throw new LecturerException(MessageConstant.LECTURER_NOT_FOUND);
        }
    }
}