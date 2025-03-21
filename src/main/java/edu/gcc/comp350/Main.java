package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.List;
import  java.sql.*;

public class Main {
    public Main() {
    }

    protected static enum Days {
        MWF,
        TR
    }

    protected static enum Session {
        FALL,
        WINTER,
        SPRING,
        EARLYSUMMER,
        LATESUMMER
    }

    protected static List<Course> courses = new ArrayList<>();
    protected static List<Professor> professors;
    protected static Student currentStudent;
    protected static Search search;
    protected static Schedule currentSchedule;
    protected static ConsoleIO consoleIO = new ConsoleIO();

    // this must be protected so we all have one contact to the db
    protected static DatabaseConnect db = new DatabaseConnect();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        onLoad();
//        for(Course course : courses) {
//            System.out.println(course.getCourseTitle());
//        }
        // for MVP testing
        currentStudent = new Student(1, "John Smith", "Poli Sci", List.of("Music"));
        currentSchedule = currentStudent.addSchedule(new Schedule("John's Schedule"));
        consoleIO.run();
        db.disconnect();
    }

    protected static void onLoad() throws SQLException, ClassNotFoundException {
        db.connect();
        //db.setCoursesInDatabase();
        db.clearDatabase();
        db.createDatabase();
        db.resetCoursesInDatabase();
        loadCourses();
    }

    protected static void displaySchedule(Schedule schedule) {

    }

    private static void loadCourses() {
        ArrayList<Object> courseList = db.select("id, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode", "Courses");
        int currentId = -1;
        for (int i = 0; i < courseList.size(); i+=9) {
            currentId = (int) courseList.get(i);
            String courseTitle = (String) courseList.get(i+1);
            String professor = (String) courseList.get(i+2);
            String session = (String) courseList.get(i+3);
            String startTime = (String) courseList.get(i+4);
            String endTime = (String) courseList.get(i+5);
            String courseDays = (String) courseList.get(i+6);
            String courseDept = (String) courseList.get(i+7);
            String courseCode = (String) courseList.get(i+8);
            Course course = new Course(currentId, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode);
            courses.add(course);
        }
    }

    private static void loadProfessors() {

    }

}
