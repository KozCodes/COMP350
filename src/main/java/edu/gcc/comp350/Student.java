package edu.gcc.comp350;

import java.util.List;

public class Student {

    private int id;
    private String name;
    private String major;
    private List<String> minors;
    private List<Schedule> schedules;

    protected Student(String name, String major, List<String> minors) {

    }

    protected int getId() {
        return -1;
    }

    protected String getName() {
        return null;
    }

    protected void setName(String name) {

    }

    protected String getMajor() {
        return null;
    }

    protected List<String> getMinors() {
        return null;
    }

    protected List<Schedule> getSchedules() {
        return null;
    }

    protected void addSchedule(Schedule schedule) {

    }

    protected void deleteSchedule(Schedule schedule) {

    }

    protected Schedule getSchedule(int id) {
        return null;
    }
}
