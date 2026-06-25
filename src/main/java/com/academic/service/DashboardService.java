package com.academic.service;

public class DashboardService {

    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final CourseService courseService;
    private final GradeService gradeService;

    public DashboardService(StudentService studentService,
                            LecturerService lecturerService,
                            CourseService courseService,
                            GradeService gradeService) {
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.courseService = courseService;
        this.gradeService = gradeService;
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
}