package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.constant.MessageConstant;
import com.academic.exception.StudentException;
import com.academic.model.Student;
import com.academic.service.StudentService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.util.GenericTableUtil;
import com.academic.util.StudentCsvExporter;

import java.util.List;
import java.util.ArrayList;

public class StudentMenu extends BaseMenu {

    private final StudentService service = AppConfig.getStudentService();

    public void show() {
        boolean running = true;

        while (running) {
            String[] menus = {
                    "1. Tambah Mahasiswa",
                    "2. Lihat Mahasiswa",
                    "3. Cari Mahasiswa by NIM",
                    "4. Cari Mahasiswa by Nama",
                    "5. Cari Mahasiswa by Jurusan",
                    "6. Sorting Mahasiswa",
                    "7. Pagination Mahasiswa",
                    "8. Export CSV Mahasiswa",
                    "9. Update Mahasiswa",
                    "10. Hapus Mahasiswa",
                    "11. Kembali"
            };

            printMenu("MENU MAHASISWA", menus);

            int menu = inputMenu();

            switch (menu) {
                case 1 -> addStudent();
                case 2 -> showStudents();
                case 3 -> searchStudent();
                case 4 -> searchStudentByName();
                case 5 -> searchStudentByMajor();
                case 6 -> sortStudents();
                case 7 -> showStudentsWithPagination();
                case 8 -> exportStudentsToCsv();
                case 9 -> updateStudent();
                case 10 -> deleteStudent();
                case 11 -> running = false;
                default -> invalidMenu();
            }
        }
    }

    private void addStudent() {
        try {
            String nim = InputUtil.inputString("NIM: ");
            String name = InputUtil.inputString("Nama: ");
            String gender = InputUtil.inputString("Gender (L/P): ");
            String major = InputUtil.inputString("Jurusan: ");
            Integer semester = InputUtil.inputInteger("Semester: ");
            String email = InputUtil.inputString("Email: ");
            String phone = InputUtil.inputString("Phone: ");

            Student student = new Student(
                    nim,
                    name,
                    gender,
                    major,
                    semester,
                    email,
                    phone
            );

            service.addStudent(student);
            ConsoleUtil.success(MessageConstant.STUDENT_ADD_SUCCESS);

        } catch (StudentException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void showStudents() {
        ConsoleUtil.title("DATA MAHASISWA");

        if (service.getAllStudents().isEmpty()) {
            ConsoleUtil.info(MessageConstant.STUDENT_EMPTY);
            return;
        }

        printStudentTable(service.getAllStudents());
    }

    private void searchStudent() {
        try {
            String nim = InputUtil.inputString("Masukkan NIM: ");

            Student student = service.getStudentByNim(nim);
            printStudent(student);

        } catch (StudentException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            String nim = InputUtil.inputString("Masukkan NIM yang akan diupdate: ");

            Student existingStudent = service.getStudentByNim(nim);

            String name = InputUtil.inputString("Nama baru: ");
            String gender = InputUtil.inputString("Gender baru (L/P): ");
            String major = InputUtil.inputString("Jurusan baru: ");
            Integer semester = InputUtil.inputInteger("Semester baru: ");
            String email = InputUtil.inputString("Email baru: ");
            String phone = InputUtil.inputString("Phone baru: ");

            Student updatedStudent = new Student(
                    existingStudent.getId(),
                    existingStudent.getNim(),
                    name,
                    gender,
                    major,
                    semester,
                    email,
                    phone
            );

            service.updateStudent(nim, updatedStudent);
            ConsoleUtil.success(MessageConstant.STUDENT_UPDATE_SUCCESS);

        } catch (StudentException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            String nim = InputUtil.inputString("Masukkan NIM yang akan dihapus: ");

            service.deleteStudent(nim);
            ConsoleUtil.success(MessageConstant.STUDENT_DELETE_SUCCESS);

        } catch (StudentException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void searchStudentByName() {
        String keyword = InputUtil.inputString("Masukkan nama mahasiswa: ");

        ArrayList<Student> students = service.searchStudentsByName(keyword);

        if (students.isEmpty()) {
            ConsoleUtil.info(MessageConstant.STUDENT_SEARCH_EMPTY);
            return;
        }

        printStudentTable(students);
    }

    private void sortStudents() {
        ConsoleUtil.title("SORTING MAHASISWA");
        System.out.println("1. Berdasarkan NIM");
        System.out.println("2. Berdasarkan Nama");
        System.out.println("3. Berdasarkan Semester");

        int menu = InputUtil.inputInteger("Pilih sorting: ");

        ArrayList<Student> students;

        switch (menu) {
            case 1 -> students = service.sortStudentsByNim();
            case 2 -> students = service.sortStudentsByName();
            case 3 -> students = service.sortStudentsBySemester();
            default -> {
                ConsoleUtil.error(MessageConstant.MENU_NOT_AVAILABLE);
                return;
            }
        }

        if (students.isEmpty()) {
            ConsoleUtil.info(MessageConstant.STUDENT_EMPTY);
            return;
        }

        printStudentTable(students);
    }

    private void searchStudentByMajor() {
        String major = InputUtil.inputString("Masukkan jurusan mahasiswa: ");

        ArrayList<Student> students = service.searchStudentsByMajor(major);

        if (students.isEmpty()) {
            ConsoleUtil.info("Data mahasiswa tidak ditemukan.");
            return;
        }

        printStudentTable(students);
    }

    private void printStudent(Student student) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);

        printStudentTable(students);
    }

    private void printStudentTable(ArrayList<Student> students) {
        String[] headers = {
                "ID",
                "NIM",
                "Nama",
                "Jurusan",
                "Semester"
        };

        List<String[]> rows = students.stream()
                .map(student -> new String[]{
                        String.valueOf(student.getId()),
                        student.getNim(),
                        student.getName(),
                        student.getMajor(),
                        String.valueOf(student.getSemester())
                })
                .toList();

        GenericTableUtil.print(headers, rows);
    }

    private void exportStudentsToCsv() {
        if (service.getAllStudents().isEmpty()) {
            ConsoleUtil.info(MessageConstant.STUDENT_EMPTY);
            return;
        }

        StudentCsvExporter.export(service.getAllStudents());
    }

    private void showStudentsWithPagination() {
        int size = InputUtil.inputInteger("Jumlah data per halaman: ");
        int totalPages = service.getTotalStudentPages(size);

        if (totalPages == 0) {
            ConsoleUtil.info(MessageConstant.STUDENT_EMPTY);
            return;
        }

        int page = 1;
        boolean running = true;

        while (running) {
            ConsoleUtil.title("DATA MAHASISWA - PAGE " + page + " / " + totalPages);

            ArrayList<Student> students = service.getStudentsByPage(page, size);
            printStudentTable(students);

            System.out.println("1. Halaman Sebelumnya");
            System.out.println("2. Halaman Berikutnya");
            System.out.println("3. Keluar Pagination");

            int menu = InputUtil.inputInteger("Pilih menu: ");

            switch (menu) {
                case 1 -> {
                    if (page > 1) {
                        page--;
                    } else {
                        ConsoleUtil.info(MessageConstant.FIRST_PAGE);
                    }
                }
                case 2 -> {
                    if (page < totalPages) {
                        page++;
                    } else {
                        ConsoleUtil.info(MessageConstant.LAST_PAGE);
                    }
                }
                case 3 -> running = false;
                default -> ConsoleUtil.error(MessageConstant.MENU_NOT_AVAILABLE);
            }
        }
    }

}