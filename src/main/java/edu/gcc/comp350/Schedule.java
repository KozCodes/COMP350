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
     * Class variable id is initialized to a random number [1, 6000)
     * Class variable classes is initialized to an empty ArrayList
     * Class variable lastChangedCourses is initialized to a new Stack
     * Class variable name is initialized to "Schedule " + 1 + the number of schedules the current student currently has
     */
    protected Schedule() {
        this.id = new Random().nextInt(6000);
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.name = "Schedule " + (Main.currentStudent.getSchedules().isEmpty() ? 1 : Main.currentStudent.getSchedules().size() + 1);
    }

    /**
     * Schedule Constructor
     * Class variable id is initialized to a random number [1, 6000)
     * Class variable classes is initialized to an empty ArrayList
     * Class variable lastChangedCourses is initialized to a new Stack
     * Class variable name is initialized to param name
     *
     * @param name String specified name for this Schedule
     */
    protected Schedule(String name) {
        this.id = new Random().nextInt(6000);
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

    protected void removeCourse(int courseID) {

    }

    protected ArrayList<Course> getCourses() {
        return null;
    }

    protected void undo() {

    }

    public void save() {
    }
}
