package edu.gcc.comp350;

import java.sql.Time;

public class Course {

    private int id;
    private Professor professor;
    private Main.Session session;
    private Time startTime;
    private Time endTime;
    private Main.Days courseDays;
    private String courseDept;
    private String courseCode;
    private int referenceCode;
    private String description;

    protected Course(Professor professor,
                     Main.Session session,
                     Time startTime,
                     Time endTime,
                     Main.Days courseDays,
                     String courseDept,
                     String courseCode,
                     int referenceCode,
                     String description) {

    }

    protected Professor getProfessor() {
        return null;
    }

    protected Main.Session getSession() {
        return null;
    }

    protected Time getStartTime() {
        return null;
    }

    protected Time getEndTime() {
        return null;
    }

    protected Main.Days getCourseDays() {
        return null;
    }

    protected String getCourseDept() {
        return null;
    }

    protected String getCourseCode() {
        return null;
    }

    protected int getReferenceCode() {
        return -1;
    }

    protected String getDescription() {
        return null;
    }

    protected int getId() {
        return -1;
    }

    protected String courseDaysToString() {
        return null;
    }

    protected boolean hasConflict(Course course) {
        return false;
    }
}
