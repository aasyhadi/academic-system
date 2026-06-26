package com.academic.menu;

import com.academic.config.AppConfig;
import com.academic.exception.CourseException;
import com.academic.model.Course;
import com.academic.service.CourseService;
import com.academic.util.ConsoleUtil;
import com.academic.util.InputUtil;
import com.academic.util.TableUtil;
import com.academic.constant.MessageConstant;
import com.academic.util.GenericTableUtil;
import com.academic.util.AcademicCsvExporter;

import java.util.List;
import java.util.ArrayList;

public class CourseMenu extends BaseMenu {

    private final CourseService service = AppConfig.getCourseService();

    public void show() {
        boolean running = true;

        while (running) {
            String[] menus = {
                    "1. Tambah Mata Kuliah",
                    "2. Lihat Mata Kuliah",
                    "3. Cari Mata Kuliah",
                    "4. Update Mata Kuliah",
                    "5. Hapus Mata Kuliah",
                    "6. Export CSV Mata Kuliah",
                    "7. Kembali"
            };

            printMenu("MENU MATA KULIAH", menus);

            int menu = inputMenu();

            switch (menu) {
                case 1 -> addCourse();
                case 2 -> showCourses();
                case 3 -> searchCourse();
                case 4 -> updateCourse();
                case 5 -> deleteCourse();
                case 6 -> AcademicCsvExporter.exportCourses(service.getAllCourses());
                case 7 -> running = false;
                default -> invalidMenu();
            }
        }
    }

    private void addCourse() {
        try {
            String code = InputUtil.inputString("Kode MK: ");
            String name = InputUtil.inputString("Nama MK: ");
            Integer credit = InputUtil.inputInteger("SKS: ");
            String semester = InputUtil.inputString("Semester: ");

            Course course = new Course(code, name, credit, semester);

            service.addCourse(course);
            ConsoleUtil.success(MessageConstant.COURSE_ADD_SUCCESS);

        } catch (CourseException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void showCourses() {
        ConsoleUtil.title("DATA MATA KULIAH");

        if (service.getAllCourses().isEmpty()) {
            ConsoleUtil.info(MessageConstant.COURSE_EMPTY);
            return;
        }

        printCourseTable(service.getAllCourses());
    }

    private void searchCourse() {
        try {
            String code = InputUtil.inputString("Masukkan Kode MK: ");

            Course course = service.getCourseByCode(code);
            printCourse(course);

        } catch (CourseException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void updateCourse() {
        try {
            String code = InputUtil.inputString("Masukkan Kode MK yang akan diupdate: ");

            Course existingCourse = service.getCourseByCode(code);

            String name = InputUtil.inputString("Nama MK baru: ");
            Integer credit = InputUtil.inputInteger("SKS baru: ");
            String semester = InputUtil.inputString("Semester baru: ");

            Course updatedCourse = new Course(
                    existingCourse.getCode(),
                    name,
                    credit,
                    semester
            );

            service.updateCourse(code, updatedCourse);
            ConsoleUtil.success(MessageConstant.COURSE_UPDATE_SUCCESS);

        } catch (CourseException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void deleteCourse() {
        try {
            String code = InputUtil.inputString("Masukkan Kode MK yang akan dihapus: ");

            service.deleteCourse(code);
            ConsoleUtil.success(MessageConstant.COURSE_DELETE_SUCCESS);

        } catch (CourseException e) {
            ConsoleUtil.error(e.getMessage());
        }
    }

    private void printCourseTable(ArrayList<Course> courses) {
        String[] headers = {
                "ID",
                "Kode MK",
                "Nama MK",
                "SKS",
                "Semester"
        };

        List<String[]> rows = courses.stream()
                .map(course -> new String[]{
                        String.valueOf(course.getId()),
                        course.getCode(),
                        course.getName(),
                        String.valueOf(course.getCredit()),
                        course.getSemester()
                })
                .toList();

        GenericTableUtil.print(headers, rows);
    }

    private void printCourse(Course course) {
        ConsoleUtil.line();
        System.out.println("ID       : " + course.getId());
        System.out.println("Kode MK  : " + course.getCode());
        System.out.println("Nama MK  : " + course.getName());
        System.out.println("SKS      : " + course.getCredit());
        System.out.println("Semester : " + course.getSemester());
    }
}