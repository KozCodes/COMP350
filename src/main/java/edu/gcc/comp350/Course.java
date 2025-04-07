package edu.gcc.comp350;

import java.sql.Time;
import java.util.Random;

public class Course {

    private final int id;
    private String courseTitle;
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
    public Course(int id,
                  String courseTitle,
                  String professor,
                  String session,
                  String startTime,
                  String endTime,
                  String courseDays,
                  String courseDept,
                  String courseCode) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.professor = professor;
        this.session = session;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDays = courseDays;
        this.courseDept = courseDept;
        this.courseCode = courseCode;
    }

    protected String getCourseTitle() {
        return courseTitle;
    }

    protected String getProfessor() {
        return professor;
    }

    protected String getSession() {
        return session;
    }

    protected String getStartTime() {
        return startTime;
    }

    protected String getEndTime() {
        return endTime;
    }

    protected String getCourseDays() {
        return courseDays;
    }

    protected String getCourseDept() {
        return courseDept;
    }

    protected String getCourseCode() {
        return courseCode;
    }

    protected int getID() {
        return id;
    }

    protected String courseDaysToString() {
        return courseDays;
    }

    protected boolean hasConflict(Course course) {
        return false;
    }

    @Override
    public String toString() {
        return String.format(
                        "| %-2d | %-20s |  Professor: %-14s | %-7s | StartTime: %-5s | EndTime: %-5s | Days: %-7s| %-4s | Code: %-10s |\n",
                id, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode
        );
    }
}
