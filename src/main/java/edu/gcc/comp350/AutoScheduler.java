package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.HashMap;

public class AutoScheduler {

    boolean validCourses;
    ArrayList<Schedule> schedules = new ArrayList<>();
    ArrayList<ArrayList<Course>> conflictingCourses = new ArrayList<>();

    protected AutoScheduler() {
        this.validCourses = true;
    }

    protected boolean isValid(ArrayList<Course> allCourses, ArrayList<String> enteredCourses) {
        for (String enteredCourse : enteredCourses) {
            boolean isValid = allCourses.stream().anyMatch(course -> course.getCourseCode().contains(enteredCourse));
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    protected AutoScheduler generateSections(ArrayList<Course> allCourses, ArrayList<String> enteredCourses, int numSchedueles) {
        System.out.println("Enter courses");
        AutoScheduler autoScheduler = new AutoScheduler();
        // Check if the courses are valid. I.e. all entered courses are in the allCourses list
        if(isValid(allCourses, enteredCourses)) {
            validCourses = false; // If not, return not valid. Ideally, the function will be called before this so this will not be needed but added for safety
            return autoScheduler;
        }

        // If yes, add the courses to the schedule
        ArrayList<Course> potentialSchedule = new ArrayList<>();
        for (String enteredCourse : enteredCourses) {
            for (Course course : allCourses) {
                if (course.getCourseCode().contains(enteredCourse)) {
                    potentialSchedule.add(course);
                }
            }
        }
        // Then check the schedule for time conflicts and duplicate courses
        // Use AI algorithm to determine the best schedule
        // Specifically, use backtracking with MRV and forward checking to find the best schedule
        // If no schedule is found, return the schedule with the least number of conflicts and the most given courses. Also print the courses that conflicted

        return autoScheduler;
    }

    protected ArrayList<Schedule> generateCourses(ArrayList<String> courses, int numSchedueles) {
        return null;
    }

    protected ArrayList<Schedule> generateCourses(ArrayList<String> courses, int numSchedueles, HashMap<String, ArrayList<Course>> coursePrerequisites, HashMap<String, Boolean> courseIsRequired, HashMap<String, Integer> courseYearsOffered) {
        return null;
    }
}
