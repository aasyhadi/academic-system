package com.academic.util;

import com.academic.model.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StudentFileStorage {

    private static final String FILE_PATH = "data/students.txt";

    public static void save(ArrayList<Student> students) {
        try {
            File folder = new File("data");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                for (Student student : students) {
                    writer.write(
                            student.getId() + "|" +
                                    student.getNim() + "|" +
                                    student.getName() + "|" +
                                    student.getGender() + "|" +
                                    student.getMajor() + "|" +
                                    student.getSemester() + "|" +
                                    student.getEmail() + "|" +
                                    student.getPhone() + "\n"
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Gagal menyimpan data mahasiswa: " + e.getMessage());
        }
    }

    public static ArrayList<Student> load() {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                Student student = new Student(
                        Long.parseLong(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        Integer.parseInt(data[5]),
                        data[6],
                        data[7]
                );

                students.add(student);
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca data mahasiswa: " + e.getMessage());
        }

        return students;
    }
}