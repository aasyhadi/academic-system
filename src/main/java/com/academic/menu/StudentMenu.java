package com.academic.menu;

import com.academic.exception.StudentException;
import com.academic.model.Student;
import com.academic.service.StudentService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.config.AppConfig;
import com.academic.constant.MessageConstant;

public class StudentMenu {

    private final StudentService service = AppConfig.getStudentService();

    public void show() {
        boolean running = true;

        while (running) {
            ConsoleUtil.title("MENU MAHASISWA");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Lihat Mahasiswa");
            System.out.println("3. Cari Mahasiswa");
            System.out.println("4. Update Mahasiswa");
            System.out.println("5. Hapus Mahasiswa");
            System.out.println("6. Kembali");

            int menu = InputUtil.inputInteger("Pilih menu: ");

            switch (menu) {
                case 1 -> addStudent();
                case 2 -> showStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> running = false;
                default -> ConsoleUtil.error(MessageConstant.MENU_NOT_AVAILABLE);
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

        for (Student student : service.getAllStudents()) {
            printStudent(student);
        }
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

    private void printStudent(Student student) {
        ConsoleUtil.line();
        System.out.println("ID       : " + student.getId());
        System.out.println("NIM      : " + student.getNim());
        System.out.println("Nama     : " + student.getName());
        System.out.println("Gender   : " + student.getGender());
        System.out.println("Jurusan  : " + student.getMajor());
        System.out.println("Semester : " + student.getSemester());
        System.out.println("Email    : " + student.getEmail());
        System.out.println("Phone    : " + student.getPhone());
    }
}