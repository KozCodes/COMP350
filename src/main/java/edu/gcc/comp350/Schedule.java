package edu.gcc.comp350;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import static edu.gcc.comp350.RefactoredMain.db;


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
        this.name = "Schedule " + (RefactoredMain.currentStudent.getSchedules().isEmpty() ? 1 : RefactoredMain.currentStudent.getSchedules().size() + 1);
        this.id = addScheduleToDatabase();
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
        this.id = addScheduleToDatabase();
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

        // Get courses from db
        String sql = "SELECT course FROM ScheduleCourses WHERE schedule = " + this.id;
        try (var stmt = RefactoredMain.db.conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("course");
                int courseID = rs.getInt("course");
                for (Course course : RefactoredMain.courses) {
                    if (course.getID() == courseID) {
                        System.out.println(course.getID());
                        classes.add(course);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add the Schedule to the database
     * @return int id assigned to the Schedule
     */
    protected int addScheduleToDatabase() {
        try (var pstmt = RefactoredMain.db.conn.prepareStatement("INSERT INTO Schedule (scheduleTitle, student) VALUES (?, ?)")) {
            pstmt.setString(1, this.name);
            pstmt.setInt(2, RefactoredMain.currentStudent.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql = "SELECT id FROM Schedule WHERE scheduleTitle = ? AND student = ?";
        try (var pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            pstmt.setString(1, this.name);
            pstmt.setInt(2, RefactoredMain.currentStudent.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
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
    protected int addCourse(int courseID) {
        for (Course newCourse : RefactoredMain.courses) {
            if (newCourse.getID() == courseID) {
                for (Course existingCourse : classes) {
                    if (hasDayConflict(existingCourse, newCourse)) {
                        if (hasTimeConflict(existingCourse, newCourse)) {
                            System.out.println("Error: Course " + newCourse.getCourseTitle() +
                                    " conflicts with " + existingCourse.getCourseTitle() +
                                    " and cannot be added to the schedule.");
                            return 0;
                        }
                    }
                }
                classes.add(newCourse);
                lastChangedCourses.push(newCourse);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Checks if two courses have overlapping days.
     */
    public boolean hasDayConflict(Course existingCourse, Course newCourse) {
        List<RefactoredMain.Days> existingDays = existingCourse.getCourseDays();
        List<RefactoredMain.Days> newDays = newCourse.getCourseDays();

        for (RefactoredMain.Days day : existingDays) {
            if (newDays.contains(day)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if two courses have overlapping time slots.
     */
    public boolean hasTimeConflict(Course existingCourse, Course newCourse) {
        List<Time> existingStartTimes = existingCourse.getStartTime();
        List<Time> existingEndTimes = existingCourse.getEndTime();
        List<Time> newStartTimes = newCourse.getStartTime();
        List<Time> newEndTimes = newCourse.getEndTime();

        for (int i = 0; i < existingStartTimes.size(); i++) {
            Time existingStart = existingStartTimes.get(i);
            Time existingEnd = existingEndTimes.get(i);

            for (int j = 0; j < newStartTimes.size(); j++) {
                Time newStart = newStartTimes.get(j);
                Time newEnd = newEndTimes.get(j);

                // Check for overlap: (start1 < end2) && (start2 < end1)
                if (existingStart.before(newEnd) && newStart.before(existingEnd)) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * Remove a course from the Schedule
     * @param courseID int ID of the course to remove
     */
    protected void removeCourse(int courseID) {
        // find course in list of courses
        for (Course course : RefactoredMain.courses) {
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


    protected void saveSchedule() {
//        String scheduleSql = "INSERT INTO Schedule (scheduleTitle, student) VALUES ('" + schedule.getName() + "', " + id + ")";
//        db.injectSql(scheduleSql);

        String scheduleCourseSql = "DELETE FROM ScheduleCourses WHERE schedule = ?";
        try (var pstmt = db.conn.prepareStatement(scheduleCourseSql)) {
            pstmt.setInt(1, this.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        for (Course course : this.getCourses()) {
            try (var pstmt = db.conn.prepareStatement("INSERT INTO ScheduleCourses (schedule, course) VALUES (?, ?)")) {
                pstmt.setInt(1, this.getId());
                pstmt.setInt(2, course.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

