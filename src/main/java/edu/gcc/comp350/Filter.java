package edu.gcc.comp350;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    private List<RefactoredMain.Days> course;
    private List<Time> startTime;
    private List<Time> endTime;
    private RefactoredMain.Session courseSession;
    private List<String> courseCodes;
    private String department;

    // empty constructor
    protected Filter() {
        List<RefactoredMain.Days> days = new ArrayList<>();
        days.add(RefactoredMain.Days.BLANK);
        this.course = days;
        List<Time> startTime = new ArrayList<>();
        startTime.add(Time.valueOf("00:00:00"));
        this.startTime = startTime;
        List<Time> endTime = new ArrayList<>();
        endTime.add(Time.valueOf("00:00:00"));
        this.endTime = endTime;
        this.courseSession = RefactoredMain.Session.BLANK;
        this.courseCodes = new ArrayList<>();
        this.department = "";
    }

    // overloaded constructor
    protected Filter(List<RefactoredMain.Days> course,
                     List<Time> startTime,
                     List<Time> endTime,
                     RefactoredMain.Session courseSession,
                     List<String> courseCodes,
                     String department) {

        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseSession = courseSession;
        this.courseCodes = courseCodes;
        this.department = department;
    }

    protected List<RefactoredMain.Days> getCourse() {
        return course;
    }

    protected void setCourse(List<RefactoredMain.Days> course) {
        this.course = course;
    }

    protected List<Time> getStartTime() {
        return startTime;
    }

    protected void setStartTime(List<Time> startTime) {
        this.startTime = startTime;
    }

    protected List<Time> getEndTime() {
        return endTime;
    }

    protected void setEndTime(List<Time> endTime) {
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
