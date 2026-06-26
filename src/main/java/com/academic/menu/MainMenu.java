package com.academic.menu;

import com.academic.constant.MessageConstant;
import com.academic.service.LoginService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;

public class MainMenu extends BaseMenu {

    private final LoginService loginService = new LoginService();

    private final DashboardMenu dashboardMenu = new DashboardMenu();
    private final StudentMenu studentMenu = new StudentMenu();
    private final LecturerMenu lecturerMenu = new LecturerMenu();
    private final CourseMenu courseMenu = new CourseMenu();
    private final GradeMenu gradeMenu = new GradeMenu();
    private final EnrollmentMenu enrollmentMenu = new EnrollmentMenu();

    public void show() {
        if (!login()) {
            ConsoleUtil.error(MessageConstant.LOGIN_FAILED);
            return;
        }

        boolean running = true;

        while (running) {
            String[] menus = {
                    "1. Dashboard",
                    "2. Mahasiswa",
                    "3. Dosen",
                    "4. Mata Kuliah",
                    "5. Nilai",
                    "6. KRS / Enrollment",
                    "7. Logout"
            };

            printMenu("ACADEMIC MANAGEMENT SYSTEM", menus);

            int menu = inputMenu();

            switch (menu) {
                case 1 -> dashboardMenu.show();
                case 2 -> studentMenu.show();
                case 3 -> lecturerMenu.show();
                case 4 -> courseMenu.show();
                case 5 -> gradeMenu.show();
                case 6 -> enrollmentMenu.show();
                case 7 -> {
                    running = false;
                    ConsoleUtil.success(MessageConstant.LOGOUT_SUCCESS);
                }
                default -> invalidMenu();
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