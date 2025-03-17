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

    /**
     * Get the database ID of the Schedule object
     * @return
     */
    protected int getId() {
        return id;
    }

    /**
     * Get the current name of the Schedule
     * @return String name of the Schedule
     */
    protected String getName() {
        return name;
    }

    /**
     * Set the name of the Schedule
     * @param name String new name for the Schedule
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Add a course to the Schedule
     * @param courseID int ID of the course to add
     */
    protected void addCourse(int courseID) {
        // find course in db
        Course course = null;
        // add course to classes
        classes.add(course);
        // add course to lastChangedCourses
        lastChangedCourses.push(course);
    }

    /**
     * Remove a course from the Schedule
     * @param courseID int ID of the course to remove
     */
    protected void removeCourse(int courseID) {
        // find course in db
        Course course = null;
        // remove course from classes
        classes.remove(course);
        // add course to lastChangedCourses
        lastChangedCourses.push(course);
    }

    /**
     * Undo the last change made to the Schedule
     * If the top of the lastChangedCourses stack is in the classes list, remove it
     * Else, add it to the classes list
     */
    protected void undo() {
        Course course = lastChangedCourses.pop();
        if (classes.contains(course)) {
            classes.remove(course);
        } else {
            classes.add(course);
        }
    }

    /**
     * Get the list of courses in the Schedule
     * @return List<Course> list of courses in the Schedule
     */
    protected List<Course> getCourses() {
        return classes;
    }
}
