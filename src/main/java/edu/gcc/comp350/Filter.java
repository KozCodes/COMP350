package edu.gcc.comp350;

import java.sql.Time;
import java.util.List;

public class Filter {

    private Main.Days course;
    private Time startTime;
    private Time endTime;
    private Main.Session courseSession;
    private List<String> courseCodes;
    private String department;

    // empty constructor
    protected Filter() {}

    // overloaded constructor
    protected Filter(Main.Days course,
                     Time startTime,
                     Time endTime,
                     Main.Session courseSession,
                     List<String> courseCodes,
                     String department) {}

    protected Main.Days getCourse() {
        return null;
    }

    protected void setCourse(Main.Days course) {

    }

    protected Time getStartTime() {
        return null;
    }

    protected void setStartTime(Time startTime) {

    }

    protected Time getEndTime() {
        return null;
    }

    protected void setEndTime(Time endTime) {

    }

    protected Main.Session getCourseSession() {
        return null;
    }

    protected void setCourseSession(Main.Session courseSession) {

    }

    protected List<String> getCourseCodes() {
        return null;
    }

    protected void setCourseCodes(List<String> courseCodes) {

    }

    protected String getDepartment() {
        return null;
    }

    public void setDepartment(String department) {

    }


}
