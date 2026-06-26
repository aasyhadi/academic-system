package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.exception.EnrollmentException;
import com.academic.model.Enrollment;
import com.academic.service.EnrollmentService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.util.TableUtil;

public class EnrollmentMenu {

    private final EnrollmentService service = AppConfig.getEnrollmentService();

    public void show() {
        boolean running = true;

        while (running) {
            ConsoleUtil.title("MENU KRS / ENROLLMENT");
            System.out.println("1. Tambah KRS");
            System.out.println("2. Lihat Semua KRS");
            System.out.println("3. Lihat KRS Mahasiswa");
            System.out.println("4. Hapus KRS");
            System.out.println("5. Kembali");

            int menu = InputUtil.inputInteger("Pilih menu: ");

            switch (menu) {
                case 1 -> addEnrollment();
                case 2 -> showEnrollments();
                case 3 -> showStudentEnrollments();
                case 4 -> deleteEnrollment();
                case 5 -> running = false;
                default -> ConsoleUtil.error("Menu tidak tersedia.");
            }
        }
    }

    private void addEnrollment() {
        try {
            String studentNim = InputUtil.inputString("NIM Mahasiswa: ");
            String courseCode = InputUtil.inputString("Kode Mata Kuliah: ");

            Enrollment enrollment = new Enrollment(studentNim, courseCode);

            service.addEnrollment(enrollment);
            ConsoleUtil.success("Data KRS berhasil ditambahkan.");

        } catch (EnrollmentException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void showEnrollments() {
        ConsoleUtil.title("DATA KRS");

        if (service.getAllEnrollments().isEmpty()) {
            ConsoleUtil.info("Belum ada data KRS.");
            return;
        }

        TableUtil.printEnrollmentHeader();

        for (Enrollment enrollment : service.getAllEnrollments()) {
            TableUtil.printEnrollmentRow(
                    enrollment.getId(),
                    enrollment.getStudentNim(),
                    enrollment.getCourseCode()
            );
        }

        TableUtil.printEnrollmentFooter();
    }

    private void showStudentEnrollments() {
        try {
            String studentNim = InputUtil.inputString("Masukkan NIM Mahasiswa: ");

            if (service.getEnrollmentsByStudentNim(studentNim).isEmpty()) {
                ConsoleUtil.info("Mahasiswa belum mengambil mata kuliah.");
                return;
            }

            for (Enrollment enrollment : service.getEnrollmentsByStudentNim(studentNim)) {
                printEnrollment(enrollment);
            }

        } catch (EnrollmentException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void deleteEnrollment() {
        try {
            String studentNim = InputUtil.inputString("NIM Mahasiswa: ");
            String courseCode = InputUtil.inputString("Kode Mata Kuliah: ");

            service.deleteEnrollment(studentNim, courseCode);
            ConsoleUtil.success("Data KRS berhasil dihapus.");

        } catch (EnrollmentException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void printEnrollment(Enrollment enrollment) {
        ConsoleUtil.line();
        System.out.println("ID              : " + enrollment.getId());
        System.out.println("NIM Mahasiswa   : " + enrollment.getStudentNim());
        System.out.println("Kode Mata Kuliah: " + enrollment.getCourseCode());
    }
}