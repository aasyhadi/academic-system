package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.service.DashboardService;
import com.academic.util.ConsoleUtil;

import java.time.LocalDate;

public class DashboardMenu {

    private final DashboardService service =
            AppConfig.getDashboardService();

    public void show() {

        ConsoleUtil.title("ACADEMIC MANAGEMENT SYSTEM");

        System.out.println("Tanggal : " + LocalDate.now());
        System.out.println("Login   : ADMIN");

        ConsoleUtil.separator();

        System.out.println("Dashboard");

        ConsoleUtil.separator();

        System.out.printf("%-20s : %d%n",
                "Mahasiswa",
                service.getTotalStudents());

        System.out.printf("%-20s : %d%n",
                "Dosen",
                service.getTotalLecturers());

        System.out.printf("%-20s : %d%n",
                "Mata Kuliah",
                service.getTotalCourses());

        System.out.printf("%-20s : %d%n",
                "KRS",
                service.getTotalEnrollments());

        System.out.printf("%-20s : %d%n",
                "Nilai",
                service.getTotalGrades());

        ConsoleUtil.line();

    }

}