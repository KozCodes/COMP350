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

    @JsonProperty
    private int numRegistered = 0;  // Default value to avoid null in JSON

    @JsonProperty
    private int numSeats = 32;  // Default value (you can change this based on your logic)



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
                  boolean taken,
                  int numRegistered,
                  int numSeat
                ) {
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
        this.numSeats = numSeats;

        if (numRegistered == 0) {
            Random random = new Random();
            this.numRegistered = random.nextInt(33);
        } else {
            this.numRegistered = numRegistered;
        }
    }

    protected boolean hasTimeConflict(Course course) {
        // Check if the course days overlap
        for (RefactoredMain.Days day : this.courseDays) {
            if (course.courseDays.contains(day)) {
                // Check if the start and end times overlap
                for (int i = 0; i < this.startTime.size(); i++) {
                    Time thisStart = this.startTime.get(i);
                    Time thisEnd = this.endTime.get(i);
                    for (int j = 0; j < course.startTime.size(); j++) {
                        Time courseStart = course.startTime.get(j);
                        Time courseEnd = course.endTime.get(j);
                        if ((thisStart.before(courseEnd) && thisEnd.after(courseStart))) {
                            return true; // Conflict found
                        }
                    }
                }
            }
        }
        return false; // No conflict found
    }

    /**
     * Checks if two courses have overlapping days and times.
     *
     * @param course The course to check against.
     * @return First time of conflict if found, null otherwise.
     */
    protected Time getTimeConflict(Course course) {
        for (RefactoredMain.Days day : this.courseDays) {
            if (course.courseDays.contains(day)) {
                for (int i = 0; i < this.startTime.size(); i++) {
                    Time thisStart = this.startTime.get(i);
                    Time thisEnd = this.endTime.get(i);
                    for (int j = 0; j < course.startTime.size(); j++) {
                        Time courseStart = course.startTime.get(j);
                        Time courseEnd = course.endTime.get(j);
                        if (thisStart.before(courseEnd) && thisEnd.after(courseStart)) {
                            return thisStart.after(courseStart) ? thisStart : courseStart;
                        }
                    }
                }
            }
        }
        return null;
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

    protected String getAmbiguousCourseCode() {
        // Returns a string of the course code without the last letter of the code
        // Note: This method will not work properly if the course code's section does not follow typical conventions
        return courseCode.substring(0, courseCode.length() - 2);
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
                        "| %-2d | %-20s |  Professor: %-14s | %-7s | StartTime: %-5s | EndTime: %-5s | Days: %-7s| %-4s | Code: %-10s | Year: %-4s | %-5s |\n",
                id, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode, year, taken
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
