package edu.gcc.comp350;

import java.sql.Time;
import java.util.Random;

public class Course {

    private Professor professor;
    private Main.Session session;
    private Time startTime;
    private Time endTime;
    private Main.Days courseDays;
    private String courseDept;
    private String courseCode;
    private int referenceCode;
    private String description;


    /**
     * Course Constructor
     *
     * @param professor Professor object that refers to the professor that teaches this course instance
     * @param session Main.Session object that refers to what term session this course instance is taught in
     * @param startTime Time referring to start time of this course instance
     * @param endTime Time referring to the end time of this course instance
     * @param courseDays Main.Days object containing the days of the week during which this course instance is in session
     * @param courseDept String college department under which this course instance is listed, the 4-letter portion of the course code
     * @param courseCode String course code, including department and number, eg COMP 141, SOCI 221
     * @param referenceCode int id of course instance, as determined by myGCC software
     * @param description String course description
     */
    protected Course(Professor professor,
                     Main.Session session,
                     Time startTime,
                     Time endTime,
                     Main.Days courseDays,
                     String courseDept,
                     String courseCode,
                     int referenceCode,
                     String description) {
        this.professor = professor;
        this.session = session;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDays = courseDays;
        this.courseDept = courseDept;
        this.courseCode = courseCode;
        this.referenceCode = referenceCode;
        this.description = description;
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

    protected String courseDaysToString() {
        return null;
    }

    protected boolean hasConflict(Course course) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
