package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Schedule {

    private int id;
    private List<Course> classes;
    private String name;
    private Stack<Course> lastChangedCourses;

    /**
     * Schedule Constructor
     * Initializes id, classes, lastChangedCourses, and name.
     */
    protected Schedule() {
        this.id = new Random().nextInt(6000);
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.name = "Schedule " + (Main.currentStudent.getSchedules().isEmpty() ? 1 : Main.currentStudent.getSchedules().size() + 1);
    }

    /**
     * Schedule Constructor with name parameter
     */
    protected Schedule(String name) {
        this.id = new Random().nextInt(6000);
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.name = name;
    }

    protected int getId() {
        return id;
    }

    protected String getName() {
        return name;  // Return the actual name
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void addCourse(Course course) {
        if (course != null) {
            classes.add(course);
            lastChangedCourses.push(course);  // For undo, keeps track of last course
        }
    }

    // Undo the last added course
    protected void undo() {
        if (!lastChangedCourses.isEmpty()) {
            Course lastCourse = lastChangedCourses.pop();
            classes.remove(lastCourse);
        }
    }

    // Returns all courses in the schedule
    protected List<Course> getCourses() {
        return classes;
    }
}
