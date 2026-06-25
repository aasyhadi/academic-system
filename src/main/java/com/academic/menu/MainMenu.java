package com.academic.menu;

import com.academic.service.LoginService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.constant.MessageConstant;

public class MainMenu {

    private final LoginService loginService = new LoginService();
    private final StudentMenu studentMenu = new StudentMenu();
    private final LecturerMenu lecturerMenu = new LecturerMenu();
    private final CourseMenu courseMenu = new CourseMenu();
    private final GradeMenu gradeMenu = new GradeMenu();
    private final DashboardMenu dashboardMenu = new DashboardMenu();
    private final EnrollmentMenu enrollmentMenu = new EnrollmentMenu();

    public void show() {
        if (!login()) {
            ConsoleUtil.error(MessageConstant.LOGIN_FAILED);
            return;
        }

        boolean running = true;

        while (running) {
            ConsoleUtil.title("ACADEMIC SYSTEM");
            System.out.println("1. Dashboard");
            System.out.println("2. Mahasiswa");
            System.out.println("3. Dosen");
            System.out.println("4. Mata Kuliah");
            System.out.println("5. Nilai");
            System.out.println("6. KRS / Enrollment");
            System.out.println("7. Logout");

            int menu = InputUtil.inputInteger("Pilih menu: ");

            switch (menu) {
                case 1 -> dashboardMenu.show();
                case 2 -> studentMenu.show();
                case 3 -> lecturerMenu.show();
                case 4 -> courseMenu.show();
                case 5 -> gradeMenu.show();
                case 6 -> enrollmentMenu.show();
                case 7 -> {
                    running = false;
                    ConsoleUtil.success("Logout berhasil.");
                }
                default -> ConsoleUtil.error("Menu tidak tersedia.");
            }
        }
    }

    private boolean login() {
        ConsoleUtil.title("LOGIN ADMIN");

        String username = InputUtil.inputString("Username: ");
        String password = InputUtil.inputString("Password: ");

        if (loginService.login(username, password)) {
            ConsoleUtil.success(MessageConstant.LOGIN_SUCCESS);
            return true;
        }

        return false;
    }
}