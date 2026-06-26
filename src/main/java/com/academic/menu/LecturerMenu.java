package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.exception.LecturerException;
import com.academic.model.Lecturer;
import com.academic.service.LecturerService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.util.TableUtil;
import com.academic.constant.MessageConstant;
import com.academic.util.GenericTableUtil;
import com.academic.util.AcademicCsvExporter;

import java.util.List;
import java.util.ArrayList;

public class LecturerMenu extends BaseMenu {

    private final LecturerService service = AppConfig.getLecturerService();

    public void show() {
        boolean running = true;

        while (running) {
            String[] menus = {
                    "1. Tambah Dosen",
                    "2. Lihat Dosen",
                    "3. Cari Dosen",
                    "4. Update Dosen",
                    "5. Hapus Dosen",
                    "6. Export CSV Dosen",
                    "7. Kembali"
            };

            printMenu("MENU DOSEN", menus);

            int menu = inputMenu();

            switch (menu) {
                case 1 -> addLecturer();
                case 2 -> showLecturers();
                case 3 -> searchLecturer();
                case 4 -> updateLecturer();
                case 5 -> deleteLecturer();
                case 6 -> AcademicCsvExporter.exportLecturers(service.getAllLecturers());
                case 7 -> running = false;
                default -> invalidMenu();
            }
        }
    }

    private void addLecturer() {
        try {
            String nidn = InputUtil.inputString("NIDN: ");
            String name = InputUtil.inputString("Nama: ");
            String gender = InputUtil.inputString("Gender (L/P): ");
            String department = InputUtil.inputString("Departemen: ");
            String email = InputUtil.inputString("Email: ");
            String phone = InputUtil.inputString("Phone: ");

            Lecturer lecturer = new Lecturer(
                    nidn,
                    name,
                    gender,
                    department,
                    email,
                    phone
            );

            service.addLecturer(lecturer);
            ConsoleUtil.success(MessageConstant.LECTURER_ADD_SUCCESS);

        } catch (LecturerException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void showLecturers() {
        ConsoleUtil.title("DATA DOSEN");

        if (service.getAllLecturers().isEmpty()) {
            ConsoleUtil.info(MessageConstant.LECTURER_EMPTY);
            return;
        }

        printLecturerTable(service.getAllLecturers());
    }

    private void searchLecturer() {
        try {
            String nidn = InputUtil.inputString("Masukkan NIDN: ");

            Lecturer lecturer = service.getLecturerByNidn(nidn);
            printLecturer(lecturer);

        } catch (LecturerException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void updateLecturer() {
        try {
            String nidn = InputUtil.inputString("Masukkan NIDN yang akan diupdate: ");

            Lecturer existingLecturer = service.getLecturerByNidn(nidn);

            String name = InputUtil.inputString("Nama baru: ");
            String gender = InputUtil.inputString("Gender baru (L/P): ");
            String department = InputUtil.inputString("Departemen baru: ");
            String email = InputUtil.inputString("Email baru: ");
            String phone = InputUtil.inputString("Phone baru: ");

            Lecturer updatedLecturer = new Lecturer(
                    existingLecturer.getNidn(),
                    name,
                    gender,
                    department,
                    email,
                    phone
            );

            service.updateLecturer(nidn, updatedLecturer);
            ConsoleUtil.success(MessageConstant.LECTURER_UPDATE_SUCCESS);

        } catch (LecturerException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void deleteLecturer() {
        try {
            String nidn = InputUtil.inputString("Masukkan NIDN yang akan dihapus: ");

            service.deleteLecturer(nidn);
            ConsoleUtil.success(MessageConstant.LECTURER_DELETE_SUCCESS);

        } catch (LecturerException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void printLecturerTable(ArrayList<Lecturer> lecturers) {
        String[] headers = {
                "ID",
                "NIDN",
                "Nama",
                "Departemen"
        };

        List<String[]> rows = lecturers.stream()
                .map(lecturer -> new String[]{
                        String.valueOf(lecturer.getId()),
                        lecturer.getNidn(),
                        lecturer.getName(),
                        lecturer.getDepartment()
                })
                .toList();

        GenericTableUtil.print(headers, rows);
    }

    private void printLecturer(Lecturer lecturer) {
        ConsoleUtil.line();
        System.out.println("ID         : " + lecturer.getId());
        System.out.println("NIDN       : " + lecturer.getNidn());
        System.out.println("Nama       : " + lecturer.getName());
        System.out.println("Gender     : " + lecturer.getGender());
        System.out.println("Departemen : " + lecturer.getDepartment());
        System.out.println("Email      : " + lecturer.getEmail());
        System.out.println("Phone      : " + lecturer.getPhone());
    }
}