package edu.gcc.comp350;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    private String course;
    private String startTime;
    private String endTime;
    private String courseSession;
    private List<String> courseCodes;
    private String department;

    // empty constructor
    protected Filter() {
        this.course = "BLANK";
        this.startTime = "00:00:00";
        this.endTime = "00:00:00";
        this.courseSession = "BLANK";
        this.courseCodes = new ArrayList<>();
        this.department = "";
    }

    // overloaded constructor
    protected Filter(String course,
                     String startTime,
                     String endTime,
                     String courseSession,
                     List<String> courseCodes,
                     String department) {

        this.course = course;
        if (startTime.compareTo("08:00:00") >= 0 && startTime.compareTo("18:00:00") <= 0) {
            this.startTime = startTime;
        } else {
            this.startTime = "00:00:00";
        }
        if (endTime.compareTo("08:50:00") >= 0 && endTime.compareTo("21:00:00") <= 0) {
            this.endTime = endTime;
        } else {
            this.endTime = "00:00:00";
        }
        this.courseSession = courseSession;
        this.courseCodes = courseCodes;
        this.department = department;
    }

    protected String getCourse() {
        return course;
    }

    protected void setCourse(String course) {
        this.course = course;
    }

    protected String getStartTime() {
        return startTime;
    }

    protected void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    protected String getEndTime() {
        return endTime;
    }

    protected void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    protected String getCourseSession() {
        return courseSession;
    }

    protected void setCourseSession(String courseSession) {
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
