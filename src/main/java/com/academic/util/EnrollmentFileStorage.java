package com.academic.util;

import com.academic.model.Enrollment;

import java.io.*;
import java.util.ArrayList;

public class EnrollmentFileStorage {

    private static final String FILE_PATH = "data/enrollments.txt";

    public static void save(ArrayList<Enrollment> enrollments) {
        try {
            File folder = new File("data");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                for (Enrollment enrollment : enrollments) {
                    writer.write(
                            enrollment.getId() + "|" +
                                    enrollment.getStudentNim() + "|" +
                                    enrollment.getCourseCode() + "\n"
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Gagal menyimpan data KRS: " + e.getMessage());
        }
    }

    public static ArrayList<Enrollment> load() {
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return enrollments;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                Enrollment enrollment = new Enrollment(
                        data[1],
                        data[2]
                );

                enrollment.setId(Long.parseLong(data[0]));
                enrollments.add(enrollment);
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca data KRS: " + e.getMessage());
        }

        return enrollments;
    }
}