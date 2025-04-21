package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoScheduler {

    boolean validCourses;
    int maxSchedules;
    ArrayList<Schedule> schedules = new ArrayList<>();
    ArrayList<ArrayList<Course>> conflictingCourses = new ArrayList<>();

    protected AutoScheduler(int maxSchedules) {
        this.validCourses = true;
        this.maxSchedules = maxSchedules;
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

    protected ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    protected ArrayList<Schedule> getConflictingCourses() {
        return schedules;
    }

    protected AutoScheduler generateSections(ArrayList<Course> allCourses, ArrayList<String> enteredCourses) {
        System.out.println("Enter courses");
        AutoScheduler autoScheduler = new AutoScheduler(maxSchedules);
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
        // Then check the schedule for time conflicts
        for (int i = 0; i < potentialSchedule.size(); i++) {
            Course course1 = potentialSchedule.get(i);
            for (int j = i + 1; j < potentialSchedule.size(); j++) {
                Course course2 = potentialSchedule.get(j);
                if (course1.hasTimeConflict(course2)) {
                    ArrayList<Course> coursesToRemove = handleTimeConflicts(new ArrayList<>(List.of(course1, course2)));
                    potentialSchedule.removeAll(coursesToRemove);
                }
            }
        }
        // Check for duplicate courses and generate unique schedules for each permutation with a max of one of each duplicate, up to maxSchedules
        // Check for duplicate courses and generate unique schedules for each permutation with a max of one of each duplicate, up to maxSchedules
        HashMap<String, ArrayList<Course>> courseGroups = new HashMap<>();
        for (Course course : potentialSchedule) {
            courseGroups.computeIfAbsent(course.getCourseCode(), k -> new ArrayList<>()).add(course);
        }

        ArrayList<ArrayList<Course>> uniqueSchedules = new ArrayList<>();
        generatePermutations(new ArrayList<>(courseGroups.values()), new ArrayList<>(), uniqueSchedules);

        // Limit the number of schedules to maxSchedules
        for (int i = 0; i < Math.min(uniqueSchedules.size(), maxSchedules); i++) {
            Schedule schedule = new Schedule();
            for (Course course : uniqueSchedules.get(i)) {
                schedule.addCourse(course.getID());
            }
            schedules.add(schedule);
        }
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

    protected ArrayList<Course> handleTimeConflicts(ArrayList<Course> conflictingCourses) {
        ArrayList<Course> coursesToRemove = new ArrayList<>();

        return coursesToRemove;
    }
    private void generatePermutations(ArrayList<ArrayList<Course>> courseGroups, ArrayList<Course> current, ArrayList<ArrayList<Course>> result) {
        if (courseGroups.isEmpty()) {
            result.add(new ArrayList<>(current));
            return;
        }

        ArrayList<Course> group = courseGroups.remove(0);
        for (Course course : group) {
            current.add(course);
            generatePermutations(courseGroups, current, result);
            current.remove(current.size() - 1);
        }
        courseGroups.add(0, group);
    }
}
