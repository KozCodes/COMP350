package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Schedule {

    private int id;
    private List<Course> classes;
    private String name;
    private Stack<Course> lastChangedCourses;

    // constructor
    protected Schedule() {
        this.id = Main.currentStudent.getSchedules().size();
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.name = "Schedule " + this.id;
    }

    // overloaded constructor
    protected Schedule(String name) {
        this.id = Main.currentStudent.getSchedules().size();
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.name = name;
    }

    protected int getId() {
        return -1;
    }

    protected String getName() {
        return null;
    }

    protected void setName() {

    }

    protected void addCourse(int courseID) {

    }

    protected void undo() {

    }
}
