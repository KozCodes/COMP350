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

    private static DatabaseConnect db = new DatabaseConnect();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        onLoad();
        // for MVP testing
        currentStudent = new Student("John Smith", "Poli Sci", List.of("Music"));
        currentSchedule = currentStudent.addSchedule(new Schedule("John's Schedule"));
        consoleIO.run();
        db.disconnect();
    }

    protected static void onLoad() throws SQLException, ClassNotFoundException {
        db.connect();
        db.setCoursesInDatabase();
        loadCourses();
    }

    protected static void displaySchedule(Schedule schedule) {

    }

    private static void loadCourses() {
        ArrayList<Object> courseList = db.select("id, professor, session, startTime, endTime, courseDays, courseDept, courseCode", "Courses");
        int currentId = -1;
        for (int i = 0; i < courseList.size(); i+=8) {
            currentId = (int) courseList.get(i);
            String professor = (String) courseList.get(i+1);
            String session = (String) courseList.get(i+2);
            String startTime = (String) courseList.get(i+3);
            String endTime = (String) courseList.get(i+4);
            String courseDays = (String) courseList.get(i+5);
            String courseDept = (String) courseList.get(i+6);
            String courseCode = (String) courseList.get(i+7);
            Course course = new Course(currentId, professor, session, startTime, endTime, courseDays, courseDept, courseCode);
            courses.add(course);
        }
    }

    private static void loadProfessors() {

    }

}
