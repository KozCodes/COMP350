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
    private int year;

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
        this.year = 0000;
    }

    // overloaded constructor
    protected Filter(List<RefactoredMain.Days> course,
                     List<Time> startTime,
                     List<Time> endTime,
                     RefactoredMain.Session courseSession,
                     List<String> courseCodes,
                     String department, int year) {

        this.course = course;
        boolean validStartTime = true;
        for (int i = 0; i < startTime.size(); i++) {
            if (startTime.get(i).compareTo(Time.valueOf("08:00:00")) < 0 ||
                    startTime.get(i).compareTo(Time.valueOf("21:00:00")) > 0) {
                validStartTime = false;
                break;
            }
        }
        if (validStartTime) {
            this.startTime = startTime;
        } else {
            startTime = new ArrayList<>();
            startTime.add(Time.valueOf("00:00:00"));
            this.startTime = startTime;
        }

        boolean validEndTime = true;
        for (int i = 0; i < endTime.size(); i++) {
            if (endTime.get(i).compareTo(Time.valueOf("08:50:00")) < 0 ||
                    endTime.get(i).compareTo(Time.valueOf("21:50:00")) > 0) {
                validEndTime = false;
                break;
            }
        }

        if (validEndTime) {
            this.endTime = endTime;
        } else {
            endTime = new ArrayList<>();
            endTime.add(Time.valueOf("00:00:00"));
            this.endTime = endTime;
        }

        this.courseSession = courseSession;
        this.courseCodes = courseCodes;
        this.department = department;
        this.year = 0000;
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

    protected int getYear() {
        return year;
    }

    protected void setYear(int year) {
        this.year = year;
    }
}
