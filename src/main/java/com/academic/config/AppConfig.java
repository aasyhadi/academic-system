package com.academic.config;

import com.academic.factory.ServiceFactory;
import com.academic.service.*;

public class AppConfig {

    public static StudentService getStudentService() {
        return ServiceFactory.getStudentService();
    }

    public static LecturerService getLecturerService() {
        return ServiceFactory.getLecturerService();
    }

    public static CourseService getCourseService() {
        return ServiceFactory.getCourseService();
    }

    public static GradeService getGradeService() {
        return ServiceFactory.getGradeService();
    }

    public static EnrollmentService getEnrollmentService() {
        return ServiceFactory.getEnrollmentService();
    }

    public static DashboardService getDashboardService() {
        return ServiceFactory.getDashboardService();
    }
}