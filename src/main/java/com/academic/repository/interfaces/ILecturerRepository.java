package com.academic.repository.interfaces;

import com.academic.model.Lecturer;

import java.util.ArrayList;

public interface ILecturerRepository {

    void save(Lecturer lecturer);

    ArrayList<Lecturer> findAll();

    Lecturer findByNidn(String nidn);

    boolean update(String nidn, Lecturer lecturer);

    boolean delete(String nidn);
}