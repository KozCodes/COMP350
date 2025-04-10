package edu.gcc.comp350;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    private RefactoredMain.Days course;
    private Time startTime;
    private Time endTime;
    private RefactoredMain.Session courseSession;
    private List<String> courseCodes;
    private String department;

    // empty constructor
    protected Filter() {
        this.course = RefactoredMain.Days.BLANK;
        this.startTime = Time.valueOf("00:00:00");
        this.endTime = Time.valueOf("00:00:00");
        this.courseSession = RefactoredMain.Session.BLANK;
        this.courseCodes = new ArrayList<>();
        this.department = "";
    }

    // overloaded constructor
    protected Filter(RefactoredMain.Days course,
                     Time startTime,
                     Time endTime,
                     RefactoredMain.Session courseSession,
                     List<String> courseCodes,
                     String department) {

        this.course = course;
        if (startTime.compareTo(Time.valueOf("08:00:00")) >= 0 && startTime.compareTo(Time.valueOf("18:00:00")) <= 0) {
            this.startTime = startTime;
        } else {
            this.startTime = Time.valueOf("00:00:00");
        }
        if (endTime.compareTo(Time.valueOf("08:50:00")) >= 0 && endTime.compareTo(Time.valueOf("21:00:00")) <= 0) {
            this.endTime = endTime;
        } else {
            this.endTime = Time.valueOf("00:00:00");
        }
        this.courseSession = courseSession;
        this.courseCodes = courseCodes;
        this.department = department;
    }

    protected RefactoredMain.Days getCourse() {
        return course;
    }

    protected void setCourse(RefactoredMain.Days course) {
        this.course = course;
    }

    protected Time getStartTime() {
        return startTime;
    }

    protected void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    protected Time getEndTime() {
        return endTime;
    }

    protected void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    protected RefactoredMain.Session getCourseSession() {
        return courseSession;
    }

    protected void setCourseSession(RefactoredMain.Session courseSession) {
        this.courseSession = courseSession;
    }

    protected List<String> getCourseCodes() {
        return courseCodes;
    }

    protected void setCourseCodes(List<String> courseCodes) {
        this.courseCodes = courseCodes;
    }

    protected String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
