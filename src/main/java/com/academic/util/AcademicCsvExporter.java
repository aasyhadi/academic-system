package com.academic.util;

import com.academic.model.Course;
import com.academic.model.Enrollment;
import com.academic.model.Grade;
import com.academic.model.Lecturer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AcademicCsvExporter {

    private static void ensureDataFolder() {
        File folder = new File("data");

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static void exportLecturers(ArrayList<Lecturer> lecturers) {
        ensureDataFolder();

        try (FileWriter writer = new FileWriter("data/lecturers_export.csv")) {
            writer.write("ID,NIDN,Nama,Gender,Departemen,Email,Phone\n");

            for (Lecturer lecturer : lecturers) {
                writer.write(
                        lecturer.getId() + "," +
                                lecturer.getNidn() + "," +
                                lecturer.getName() + "," +
                                lecturer.getGender() + "," +
                                lecturer.getDepartment() + "," +
                                lecturer.getEmail() + "," +
                                lecturer.getPhone() + "\n"
                );
            }

            ConsoleUtil.success("Export dosen berhasil: data/lecturers_export.csv");

        } catch (IOException e) {
            ConsoleUtil.error("Gagal export dosen: " + e.getMessage());
        }
    }

    public static void exportCourses(ArrayList<Course> courses) {
        ensureDataFolder();

        try (FileWriter writer = new FileWriter("data/courses_export.csv")) {
            writer.write("ID,Kode MK,Nama MK,SKS,Semester\n");

            for (Course course : courses) {
                writer.write(
                        course.getId() + "," +
                                course.getCode() + "," +
                                course.getName() + "," +
                                course.getCredit() + "," +
                                course.getSemester() + "\n"
                );
            }

            ConsoleUtil.success("Export mata kuliah berhasil: data/courses_export.csv");

        } catch (IOException e) {
            ConsoleUtil.error("Gagal export mata kuliah: " + e.getMessage());
        }
    }

    public static void exportGrades(ArrayList<Grade> grades) {
        ensureDataFolder();

        try (FileWriter writer = new FileWriter("data/grades_export.csv")) {
            writer.write("ID,NIM,Kode MK,Nilai,Huruf\n");

            for (Grade grade : grades) {
                writer.write(
                        grade.getId() + "," +
                                grade.getStudentNim() + "," +
                                grade.getCourseCode() + "," +
                                grade.getScore() + "," +
                                grade.getLetter() + "\n"
                );
            }

            ConsoleUtil.success("Export nilai berhasil: data/grades_export.csv");

        } catch (IOException e) {
            ConsoleUtil.error("Gagal export nilai: " + e.getMessage());
        }
    }

    public static void exportEnrollments(ArrayList<Enrollment> enrollments) {
        ensureDataFolder();

        try (FileWriter writer = new FileWriter("data/enrollments_export.csv")) {
            writer.write("ID,NIM,Kode MK\n");

            for (Enrollment enrollment : enrollments) {
                writer.write(
                        enrollment.getId() + "," +
                                enrollment.getStudentNim() + "," +
                                enrollment.getCourseCode() + "\n"
                );
            }

            ConsoleUtil.success("Export KRS berhasil: data/enrollments_export.csv");

        } catch (IOException e) {
            ConsoleUtil.error("Gagal export KRS: " + e.getMessage());
        }
    }
}