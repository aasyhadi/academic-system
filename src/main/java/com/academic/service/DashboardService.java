package com.academic.service;

public class DashboardService {

    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final CourseService courseService;
    private final GradeService gradeService;
    private final EnrollmentService enrollmentService;

    public DashboardService(StudentService studentService,
                            LecturerService lecturerService,
                            CourseService courseService,
                            GradeService gradeService,
                            EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.courseService = courseService;
        this.gradeService = gradeService;
        this.enrollmentService = enrollmentService;
    }

    public int getTotalStudents() {
        return studentService.getAllStudents().size();
    }

    public int getTotalLecturers() {
        return lecturerService.getAllLecturers().size();
    }

    public int getTotalCourses() {
        return courseService.getAllCourses().size();
    }

    public int getTotalGrades() {
        return gradeService.getAllGrades().size();
    }

    public int getTotalEnrollments() {
        return enrollmentService.getAllEnrollments().size();
    }
}