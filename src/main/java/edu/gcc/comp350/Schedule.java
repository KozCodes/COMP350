package edu.gcc.comp350;

import java.util.*;

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

        // TODO add to database
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

        // TODO add to database
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
        for (Course newCourse : Main.courses) {
            if (newCourse.getID() == courseID) {
                for (Course existingCourse : classes) {
                    if (hasDayConflict(existingCourse, newCourse)) {
                        if (hasTimeConflict(existingCourse, newCourse)) {
                            System.out.println("Error: Course " + newCourse.getCourseTitle() +
                                    " conflicts with " + existingCourse.getCourseTitle() +
                                    " and cannot be added to the schedule.");
                            return;
                        }
                    }
                }
                classes.add(newCourse);
                lastChangedCourses.push(newCourse);
                return;
            }
        }
    }

    /**
     * Checks if two courses have overlapping days.
     */
    public boolean hasDayConflict(Course existingCourse, Course newCourse) {
        String existingDays = existingCourse.getCourseDays();
        String newDays = newCourse.getCourseDays();

        for (char day : existingDays.toCharArray()) {
            if (newDays.contains(String.valueOf(day))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if two courses have overlapping time slots.
     */
    public boolean hasTimeConflict(Course existingCourse, Course newCourse) {
        int existingStart = Integer.parseInt(existingCourse.getStartTime().replace(":", ""));
        int existingEnd = Integer.parseInt(existingCourse.getEndTime().replace(":", ""));
        int newStart = Integer.parseInt(newCourse.getStartTime().replace(":", ""));
        int newEnd = Integer.parseInt(newCourse.getEndTime().replace(":", ""));

        return newStart < existingEnd && newEnd > existingStart;
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
}

