package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.exception.GradeException;
import com.academic.model.Grade;
import com.academic.service.GradeService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.util.TableUtil;
import com.academic.constant.MessageConstant;
import com.academic.util.GenericTableUtil;

import java.util.List;
import java.util.ArrayList;

public class GradeMenu extends BaseMenu {

    private final GradeService service = AppConfig.getGradeService();

    public void show() {
        boolean running = true;

        while (running) {
            String[] menus = {
                    "1. Tambah Nilai",
                    "2. Lihat Nilai",
                    "3. Cari Nilai",
                    "4. Update Nilai",
                    "5. Hapus Nilai",
                    "6. Transkrip Mahasiswa",
                    "7. Kembali"
            };

            printMenu("MENU NILAI", menus);

            int menu = inputMenu();

            switch (menu) {
                case 1 -> addGrade();
                case 2 -> showGrades();
                case 3 -> searchGrade();
                case 4 -> updateGrade();
                case 5 -> deleteGrade();
                case 6 -> showStudentTranscript();
                case 7 -> running = false;
                default -> invalidMenu();
            }
        }
    }

    private void addGrade() {
        try {
            String nim = InputUtil.inputString("NIM Mahasiswa: ");
            String courseCode = InputUtil.inputString("Kode Mata Kuliah: ");
            Double score = InputUtil.inputDouble("Nilai: ");

            Grade grade = new Grade(nim, courseCode, score);

            service.addGrade(grade);
            ConsoleUtil.success(MessageConstant.GRADE_ADD_SUCCESS);

        } catch (GradeException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void showGrades() {
        ConsoleUtil.title("DATA NILAI");

        if (service.getAllGrades().isEmpty()) {
            ConsoleUtil.info(MessageConstant.GRADE_EMPTY);
            return;
        }

        printGradeTable(service.getAllGrades());
    }

    private void searchGrade() {
        try {
            String nim = InputUtil.inputString("Masukkan NIM Mahasiswa: ");
            String courseCode = InputUtil.inputString("Masukkan Kode Mata Kuliah: ");

            Grade grade = service.getGrade(nim, courseCode);
            printGrade(grade);

        } catch (GradeException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void updateGrade() {
        try {
            String nim = InputUtil.inputString("Masukkan NIM Mahasiswa: ");
            String courseCode = InputUtil.inputString("Masukkan Kode Mata Kuliah: ");
            Double score = InputUtil.inputDouble("Nilai baru: ");

            Grade updatedGrade = new Grade(nim, courseCode, score);

            service.updateGrade(nim, courseCode, updatedGrade);
            ConsoleUtil.success(MessageConstant.GRADE_UPDATE_SUCCESS);

        } catch (GradeException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void deleteGrade() {
        try {
            String nim = InputUtil.inputString("Masukkan NIM Mahasiswa: ");
            String courseCode = InputUtil.inputString("Masukkan Kode Mata Kuliah: ");

            service.deleteGrade(nim, courseCode);
            ConsoleUtil.success(MessageConstant.GRADE_DELETE_SUCCESS);

        } catch (GradeException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void showStudentTranscript() {
        try {
            String nim = InputUtil.inputString("Masukkan NIM Mahasiswa: ");

            if (service.getGradesByStudentNim(nim).isEmpty()) {
                ConsoleUtil.info("Mahasiswa belum memiliki nilai.");
                return;
            }

            ConsoleUtil.title("TRANSKRIP NILAI MAHASISWA");

            for (Grade grade : service.getGradesByStudentNim(nim)) {
                printGrade(grade);
            }

            double gpa = service.calculateGpaByStudentNim(nim);

            System.out.println();
            System.out.printf("IPK Sementara: %.2f%n", gpa);

            if (gpa >= 3.5) {
                ConsoleUtil.success("Status: Sangat Baik");
            } else if (gpa >= 3.0) {
                ConsoleUtil.success("Status: Baik");
            } else if (gpa >= 2.0) {
                ConsoleUtil.info("Status: Cukup");
            } else {
                ConsoleUtil.error("Status: Perlu Pembinaan Akademik");
            }

        } catch (GradeException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void printGradeTable(ArrayList<Grade> grades) {
        String[] headers = {
                "ID",
                "NIM",
                "Kode MK",
                "Nilai",
                "Huruf"
        };

        List<String[]> rows = grades.stream()
                .map(grade -> new String[]{
                        String.valueOf(grade.getId()),
                        grade.getStudentNim(),
                        grade.getCourseCode(),
                        String.valueOf(grade.getScore()),
                        grade.getLetter()
                })
                .toList();

        GenericTableUtil.print(headers, rows);
    }

    private void printGrade(Grade grade) {
        ConsoleUtil.line();
        System.out.println("ID              : " + grade.getId());
        System.out.println("NIM Mahasiswa   : " + grade.getStudentNim());
        System.out.println("Kode Mata Kuliah: " + grade.getCourseCode());
        System.out.println("Nilai           : " + grade.getScore());
        System.out.println("Huruf           : " + grade.getLetter());
    }
}