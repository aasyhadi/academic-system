package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.exception.GradeException;
import com.academic.model.Grade;
import com.academic.service.GradeService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.constant.MessageConstant;

public class GradeMenu {

    private final GradeService service = AppConfig.getGradeService();

    public void show() {
        boolean running = true;

        while (running) {
            ConsoleUtil.title("MENU NILAI");
            System.out.println("1. Tambah Nilai");
            System.out.println("2. Lihat Nilai");
            System.out.println("3. Cari Nilai");
            System.out.println("4. Update Nilai");
            System.out.println("5. Hapus Nilai");
            System.out.println("6. Kembali");

            int menu = InputUtil.inputInteger("Pilih menu: ");

            switch (menu) {
                case 1 -> addGrade();
                case 2 -> showGrades();
                case 3 -> searchGrade();
                case 4 -> updateGrade();
                case 5 -> deleteGrade();
                case 6 -> running = false;
                default -> ConsoleUtil.error(MessageConstant.MENU_NOT_AVAILABLE);
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

        for (Grade grade : service.getAllGrades()) {
            printGrade(grade);
        }
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

    private void printGrade(Grade grade) {
        ConsoleUtil.line();
        System.out.println("ID              : " + grade.getId());
        System.out.println("NIM Mahasiswa   : " + grade.getStudentNim());
        System.out.println("Kode Mata Kuliah: " + grade.getCourseCode());
        System.out.println("Nilai           : " + grade.getScore());
    }
}