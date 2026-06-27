package com.academic.factory;

import com.academic.repository.*;
import com.academic.service.*;
import com.academic.validation.*;

public class ServiceFactory {

    private static final StudentService studentService =
            new StudentService(
                    StudentRepository.getInstance(),
                    new StudentValidator()
            );

    private static final LecturerService lecturerService =
            new LecturerService(
                    LecturerRepository.getInstance(),
                    new LecturerValidator()
            );

    private static final CourseService courseService =
            new CourseService(
                    CourseRepository.getInstance(),
                    new CourseValidator()
            );

    private static final GradeService gradeService =
            new GradeService(
                    GradeRepository.getInstance(),
                    StudentRepository.getInstance(),
                    CourseRepository.getInstance(),
                    new GradeValidator()
            );

    private static final EnrollmentService enrollmentService =
            new EnrollmentService(
                    EnrollmentRepository.getInstance(),
                    StudentRepository.getInstance(),
                    CourseRepository.getInstance(),
                    new EnrollmentValidator()
            );

    private static final DashboardService dashboardService =
            new DashboardService(
                    studentService,
                    lecturerService,
                    courseService,
                    gradeService,
                    enrollmentService
            );

    public static StudentService getStudentService() {
        return studentService;
    }

    public static LecturerService getLecturerService() {
        return lecturerService;
    }

    public static CourseService getCourseService() {
        return courseService;
    }

    public static GradeService getGradeService() {
        return gradeService;
    }

    public static EnrollmentService getEnrollmentService() {
        return enrollmentService;
    }

    public static DashboardService getDashboardService() {
        return dashboardService;
    }
}