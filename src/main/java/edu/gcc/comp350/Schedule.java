package edu.gcc.comp350;

import java.sql.ResultSet;
import java.sql.SQLException;
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
     * Class variable id is the id assigned by the database
     * Class variable classes is initialized to an empty ArrayList
     * Class variable lastChangedCourses is initialized to a new Stack
     * Class variable name is initialized to "Schedule " + 1 + the number of schedules the current student currently has
     */
    protected Schedule() {
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.name = "Schedule " + (Main.currentStudent.getSchedules().isEmpty() ? 1 : Main.currentStudent.getSchedules().size() + 1);
        this.id = addScheduleToDatabase(name);
    }

    /**
     * Schedule Constructor
     * Class variable id is the id assigned by the database
     * Class variable classes is initialized to an empty ArrayList
     * Class variable lastChangedCourses is initialized to a new Stack
     * Class variable name is initialized to param name
     *
     * @param name String specified name for this Schedule
     */
    protected Schedule(String name) {
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.name = name;
        this.id = addScheduleToDatabase(name);
    }

    /**
     * Schedule Constructor solely for loading a schedule from the database
     * Class variable id is the id assigned by the database
     * Class variable classes is initialized to an empty ArrayList
     * Class variable lastChangedCourses is initialized to a new Stack
     * Class variable name is initialized to param name
     *
     * @param name String specified name for this Schedule
     * @param id int database specified id for this Schedule
     */
    protected Schedule(String name, int id) {
        this.classes = new ArrayList<>();
        this.lastChangedCourses = new Stack<>();
        this.id = id;
        this.name = name;
    }

    /**
     * Add the Schedule to the database
     * @return int id assigned to the Schedule
     */
    protected int addScheduleToDatabase(String name) {
        String sql = "INSERT INTO Schedule (scheduleTitle, student) VALUES ('" + name + "', " + Main.currentStudent.getId() + ")";
        Main.db.injectSql(sql);

        // This code was produced using AI //
        sql = "SELECT id FROM Schedule WHERE scheduleTitle = '" + name + "' AND student = " + Main.currentStudent.getId();
        try (var stmt = Main.db.conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Return -1 if no ID is found

    }

    /**
     * Get the database ID of the Schedule object
     * @return int ID of the Schedule
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
        // find course in list of courses
        for (Course course : Main.courses) {
            if (course.getID() == courseID) {
                classes.add(course);
                // add course to lastChangedCourses
                lastChangedCourses.push(course);
                return;
            }
        }
    }

    /**
     * Remove a course from the Schedule
     * @param courseID int ID of the course to remove
     */
    protected void removeCourse(int courseID) {
        // find course in list of courses
        for (Course course : Main.courses) {
            if (course.getID() == courseID) {
                classes.remove(course);
                // add course to lastChangedCourses
                lastChangedCourses.push(course);
                return;
            }
        }
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

    @Override
    public String toString() {
        //TODO: Implement toString with visuals
        String title = "Schedule: " + name + "\n";
        String courses = "";

        return title + courses;
    }
}

