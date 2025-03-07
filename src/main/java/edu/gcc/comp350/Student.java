package edu.gcc.comp350;

import java.util.List;

public class Student {

    private int id;
    private String name;
    private String major;
    private List<String> minors;
    private List<Schedule> schedules;

    public Student(int id, String name, String major, List<String> minors) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.minors = minors;
    }

    protected int getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getMajor() {
        return major;
    }

    protected List<String> getMinors() {
        return minors;
    }

    protected List<Schedule> getSchedules() {
        return schedules;
    }

    protected void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    protected void deleteSchedule(Schedule schedule) {
        schedules.remove(schedule);
    }

    protected Schedule getSchedule(int id) {
        for (Schedule schedule : schedules) {
            if (schedule.getId() == id) {
                return schedule;
            }
        }
        return null;
    }
}
