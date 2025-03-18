package edu.gcc.comp350;

import java.sql.Time;
import java.util.Random;

public class Course {

    private final int id;
    private String professor;
    private String session;
    private String startTime;
    private String endTime;
    private String courseDays;
    private String courseDept;
    private String courseCode;


    /**
     * Course Constructor
     *
     * @param id Auto-generated unique identifier for this course instance
     * @param professor Professor object that refers to the professor that teaches this course instance
     * @param session Main.Session object that refers to what term session this course instance is taught in
     * @param startTime Time referring to start time of this course instance
     * @param endTime Time referring to the end time of this course instance
     * @param courseDays Main.Days object containing the days of the week during which this course instance is in session
     * @param courseDept String college department under which this course instance is listed, the 4-letter portion of the course code
     * @param courseCode String course code, including department and number, eg COMP 141, SOCI 221
     */
    protected Course(int id,
                     String professor,
                     String session,
                     String startTime,
                     String endTime,
                     String courseDays,
                     String courseDept,
                     String courseCode) {
        this.id = id;
        this.professor = professor;
        this.session = session;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDays = courseDays;
        this.courseDept = courseDept;
        this.courseCode = courseCode;
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

    protected int getID() {
        return -1;
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
