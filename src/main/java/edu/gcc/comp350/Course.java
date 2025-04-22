package edu.gcc.comp350;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Time;
import java.util.List;
import java.util.Random;

public class Course {

    @JsonProperty
    private final int id;

    @JsonProperty
    private String courseTitle;

    @JsonProperty
    private Professor professor;

    @JsonProperty
    private RefactoredMain.Session session;

    @JsonProperty
    private List<Time> startTime;

    @JsonProperty
    private List<Time> endTime;

    @JsonProperty
    private List<RefactoredMain.Days> courseDays;

    @JsonProperty
    private String courseDept;

    @JsonProperty
    private String courseCode;

    @JsonProperty
    private int year;

    @JsonProperty
    private boolean taken;


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
                  Professor professor,
                  RefactoredMain.Session session,
                  List<Time> startTime,
                  List<Time> endTime,
                  List<RefactoredMain.Days> courseDays,
                  String courseDept,
                  String courseCode,
                  int year,
                  boolean taken) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.professor = professor;
        this.session = session;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDays = courseDays;
        this.courseDept = courseDept;
        this.courseCode = courseCode;
        this.year = year;
        this.taken = taken;
    }

    protected String getCourseTitle() {
        return courseTitle;
    }

    protected Professor getProfessor() {
        return professor;
    }

    protected RefactoredMain.Session getSession() {
        return session;
    }

    protected List<Time> getStartTime() {
        return startTime;
    }

    protected List<Time> getEndTime() {
        return endTime;
    }

    protected List<RefactoredMain.Days> getCourseDays() {
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
        return courseDays.toString();
    }

    protected boolean hasConflict(Course course) {
        return false;
    }

    protected int getYear() {
        return year;
    }

    protected boolean getTaken() {
        return taken;
    }

    protected void setTaken(boolean taken) {
        this.taken = taken;
    }

    protected void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format(
                        "| %-2d | %-20s |  Professor: %-14s | %-7s | StartTime: %-5s | EndTime: %-5s | Days: %-7s| %-4s | Code: %-10s |\n",
                id, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode
        );
    }

    /**
     * Convert the Schedule object to a JSON string
     * @return String JSON string representation of the Schedule
     */
    protected String toJson() {
        ObjectMapper om = new ObjectMapper();
        String courseJSON = "";
        try {
            System.out.println("Converting Course to JSON");
            courseJSON = om.writeValueAsString(this);
            System.out.println(courseJSON);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            courseJSON = "ERROR";
        }
        return courseJSON;
    }

}
