package edu.gcc.comp350;

import java.util.List;
import java.util.Stack;

public class Schedule {

    private int id;
    private List<Course> classes;
    private String name;
    private Stack<Course> lastChangedCourses;

    // constructor
    protected Schedule() {

    }

    // overloaded constructor
    protected Schedule(String name) {

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
