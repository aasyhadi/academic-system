package com.academic.util;

import com.academic.model.Course;

import java.io.*;
import java.util.ArrayList;

public class CourseFileStorage {

    private static final String FILE_PATH = "data/courses.txt";

    public static void save(ArrayList<Course> courses) {
        try {
            File folder = new File("data");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                for (Course course : courses) {
                    writer.write(
                            course.getId() + "|" +
                                    course.getCode() + "|" +
                                    course.getName() + "|" +
                                    course.getCredit() + "|" +
                                    course.getSemester() + "\n"
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Gagal menyimpan data mata kuliah: " + e.getMessage());
        }
    }

    public static ArrayList<Course> load() {
        ArrayList<Course> courses = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return courses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");

                Course course = new Course(
                        data[1],
                        data[2],
                        Integer.parseInt(data[3]),
                        data[4]
                );

                course.setId(Long.parseLong(data[0]));
                courses.add(course);
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca data mata kuliah: " + e.getMessage());
        }

        return courses;
    }
}