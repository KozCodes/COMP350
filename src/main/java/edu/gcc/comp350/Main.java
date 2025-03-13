package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.List;

public class Main {
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

    protected List<Course> courses;
    protected List<Professor> professors;
    protected static Student currentStudent;
    protected Search search;
    protected Schedule currentSchedule;

    public static void main(String[] args) {
        Student s = new Student(274819, "Jonah Kozora", "Computer Science", new ArrayList<>());
    }

    protected static void onLoad() {

    }

    protected static void displaySchedule(Schedule schedule) {

    }

    private static void loadCourses() {

    }

    private static void loadProfessors() {

    }

}