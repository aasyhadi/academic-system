package com.academic.util;

import com.academic.model.Lecturer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LecturerFileStorage {

    private static final String FILE_PATH = "data/lecturers.txt";

    public static void save(ArrayList<Lecturer> lecturers) {
        try {
            File folder = new File("data");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                for (Lecturer lecturer : lecturers) {
                    writer.write(
                            lecturer.getId() + "|" +
                                    lecturer.getNidn() + "|" +
                                    lecturer.getName() + "|" +
                                    lecturer.getGender() + "|" +
                                    lecturer.getDepartment() + "|" +
                                    lecturer.getEmail() + "|" +
                                    lecturer.getPhone() + "\n"
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Gagal menyimpan data dosen: " + e.getMessage());
        }
    }

    public static ArrayList<Lecturer> load() {
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return lecturers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                Lecturer lecturer = new Lecturer(
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        data[6]
                );

                lecturer.setId(Long.parseLong(data[0]));
                lecturers.add(lecturer);
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca data dosen: " + e.getMessage());
        }

        return lecturers;
    }
}