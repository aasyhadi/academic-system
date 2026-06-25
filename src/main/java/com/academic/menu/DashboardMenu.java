package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.service.DashboardService;
import com.academic.util.ConsoleUtil;

public class DashboardMenu {

    private final DashboardService service = AppConfig.getDashboardService();

    public void show() {
        ConsoleUtil.title("DASHBOARD AKADEMIK");
        System.out.println("Total Mahasiswa   : " + service.getTotalStudents());
        System.out.println("Total Dosen       : " + service.getTotalLecturers());
        System.out.println("Total Mata Kuliah : " + service.getTotalCourses());
        System.out.println("Total KRS         : " + service.getTotalEnrollments());
        System.out.println("Total Nilai       : " + service.getTotalGrades());
    }
}