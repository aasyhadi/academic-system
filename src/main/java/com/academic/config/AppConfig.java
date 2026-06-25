package com.academic.config;

import com.academic.repository.CourseRepository;
import com.academic.repository.EnrollmentRepository;
import com.academic.repository.GradeRepository;
import com.academic.repository.LecturerRepository;
import com.academic.repository.StudentRepository;
import com.academic.service.CourseService;
import com.academic.service.DashboardService;
import com.academic.service.EnrollmentService;
import com.academic.service.GradeService;
import com.academic.service.LecturerService;
import com.academic.service.StudentService;
import com.academic.validation.CourseValidator;
import com.academic.validation.EnrollmentValidator;
import com.academic.validation.GradeValidator;
import com.academic.validation.LecturerValidator;
import com.academic.validation.StudentValidator;

public class AppConfig {

    private static final StudentRepository studentRepository =
            StudentRepository.getInstance();

    private static final StudentValidator studentValidator =
            new StudentValidator();

    private static final StudentService studentService =
            new StudentService(studentRepository, studentValidator);

    private static final LecturerRepository lecturerRepository =
            LecturerRepository.getInstance();

    private static final LecturerValidator lecturerValidator =
            new LecturerValidator();

    private static final LecturerService lecturerService =
            new LecturerService(lecturerRepository, lecturerValidator);

    private static final CourseRepository courseRepository =
            CourseRepository.getInstance();

    private static final CourseValidator courseValidator =
            new CourseValidator();

    private static final CourseService courseService =
            new CourseService(courseRepository, courseValidator);

    private static final GradeRepository gradeRepository =
            GradeRepository.getInstance();

    private static final GradeValidator gradeValidator =
            new GradeValidator();

    private static final GradeService gradeService =
            new GradeService(
                    gradeRepository,
                    studentRepository,
                    courseRepository,
                    gradeValidator
            );

    private static final EnrollmentRepository enrollmentRepository =
            EnrollmentRepository.getInstance();

    private static final EnrollmentValidator enrollmentValidator =
            new EnrollmentValidator();

    private static final EnrollmentService enrollmentService =
            new EnrollmentService(
                    enrollmentRepository,
                    studentRepository,
                    courseRepository,
                    enrollmentValidator
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