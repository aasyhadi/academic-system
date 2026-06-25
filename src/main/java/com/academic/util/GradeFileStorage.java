package com.academic.util;

import com.academic.model.Grade;

import java.io.*;
import java.util.ArrayList;

public class GradeFileStorage {

    private static final String FILE_PATH = "data/grades.txt";

    public static void save(ArrayList<Grade> grades) {
        try {
            File folder = new File("data");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                for (Grade grade : grades) {
                    writer.write(
                            grade.getId() + "|" +
                                    grade.getStudentNim() + "|" +
                                    grade.getCourseCode() + "|" +
                                    grade.getScore() + "|" +
                                    grade.getLetter() + "\n"
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Gagal menyimpan data nilai: " + e.getMessage());
        }
    }

    public static ArrayList<Grade> load() {
        ArrayList<Grade> grades = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return grades;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                Grade grade = new Grade(
                        data[1],
                        data[2],
                        Double.parseDouble(data[3])
                );

                grade.setId(Long.parseLong(data[0]));
                grade.setLetter(data[4]);

                grades.add(grade);
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca data nilai: " + e.getMessage());
        }

        return grades;
    }
}