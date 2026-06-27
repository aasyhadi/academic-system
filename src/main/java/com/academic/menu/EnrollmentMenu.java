package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.exception.EnrollmentException;
import com.academic.model.Enrollment;
import com.academic.service.EnrollmentService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.util.GenericTableUtil;
import com.academic.util.AcademicCsvExporter;

import java.util.List;
import java.util.ArrayList;

public class EnrollmentMenu extends BaseMenu {

    private final EnrollmentService service = AppConfig.getEnrollmentService();

    public void show() {
        boolean running = true;

        while (running) {
            String[] menus = {
                    "1. Tambah KRS",
                    "2. Lihat Semua KRS",
                    "3. Lihat KRS Mahasiswa",
                    "4. Hapus KRS",
                    "5. Export CSV KRS Mahasiswa",
                    "6. Kembali"
            };

            printMenu("MENU KRS / ENROLLMENT", menus);

            int menu = inputMenu();

            switch (menu) {
                case 1 -> addEnrollment();
                case 2 -> showEnrollments();
                case 3 -> showStudentEnrollments();
                case 4 -> deleteEnrollment();
                case 5 -> AcademicCsvExporter.exportEnrollments(service.getAllEnrollments());
                case 6 -> running = false;
                default -> invalidMenu();
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

        printEnrollmentTable(service.getAllEnrollments());
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

    private void printEnrollmentTable(ArrayList<Enrollment> enrollments) {
        String[] headers = {
                "ID",
                "NIM",
                "Kode MK"
        };

        List<String[]> rows = enrollments.stream()
                .map(enrollment -> new String[]{
                        String.valueOf(enrollment.getId()),
                        enrollment.getStudentNim(),
                        enrollment.getCourseCode()
                })
                .toList();

        GenericTableUtil.print(headers, rows);
    }

    private void printEnrollment(Enrollment enrollment) {
        ConsoleUtil.line();
        System.out.println("ID              : " + enrollment.getId());
        System.out.println("NIM Mahasiswa   : " + enrollment.getStudentNim());
        System.out.println("Kode Mata Kuliah: " + enrollment.getCourseCode());
    }
}