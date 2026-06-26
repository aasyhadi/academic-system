package com.academic.util;

import com.academic.model.Student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StudentCsvExporter {

    private static final String FILE_PATH = "data/students_export.csv";

    public static void export(ArrayList<Student> students) {
        try {
            File folder = new File("data");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write("ID,NIM,Nama,Gender,Jurusan,Semester,Email,Phone\n");

                for (Student student : students) {
                    writer.write(
                            student.getId() + "," +
                                    student.getNim() + "," +
                                    student.getName() + "," +
                                    student.getGender() + "," +
                                    student.getMajor() + "," +
                                    student.getSemester() + "," +
                                    student.getEmail() + "," +
                                    student.getPhone() + "\n"
                    );
                }
            }

            ConsoleUtil.success("Export mahasiswa berhasil: " + FILE_PATH);

        } catch (IOException e) {
            ConsoleUtil.error("Gagal export mahasiswa: " + e.getMessage());
        }
    }
}