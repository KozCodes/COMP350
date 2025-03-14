package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.List;

public class Main {
    protected static enum Days {
       BLANK,
        MWF,
        TR
    }

    protected static enum Session {
        BLANK,
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