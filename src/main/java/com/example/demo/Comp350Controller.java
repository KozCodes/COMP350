// src/main/java/com/example/demo/Comp350Controller.java
package com.example.demo;

import edu.gcc.comp350.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Comp350Controller {

    protected static List<Course> courses = new ArrayList<>();
    protected static List<Professor> professors;
    protected static Student currentStudent;
    protected static Search search;
    protected static Schedule currentSchedule;
    protected static ConsoleIO consoleIO = new ConsoleIO();

    protected static DatabaseConnect db = new DatabaseConnect();

    @GetMapping("/runFunction")
    public String runFunction() throws SQLException, ClassNotFoundException {
        // Call your function from the edu.gcc.comp350 package
        onLoad();
        //currentSchedule = currentStudent.getSchedule(0);
        db.disconnect();
        return courses.get(0).toString();//Main.main();
    }

    protected void onLoad() throws SQLException, ClassNotFoundException {
        db.connect();
        db.setCoursesInDatabase();
        db.createDatabase();
        //db.resetCoursesInDatabase();
        db.resetProfessorsInDatabase();
        db.populateProfessorsInDatabase();
        db.setProfessorsInDatabase();
        loadCourses();
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
}

