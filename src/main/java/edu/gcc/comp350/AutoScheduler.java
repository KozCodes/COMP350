package edu.gcc.comp350;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoScheduler {

    boolean validCourses;
    Schedule schedule;
    ArrayList<ArrayList<Course>> conflictingCourses = new ArrayList<>();

    protected AutoScheduler() {
        this.validCourses = true;
    }

    protected boolean isValid(ArrayList<String> enteredCourses) {
        List<Course> allCourses = RefactoredMain.courses;
        for (String enteredCourse : enteredCourses) {
            boolean isValid = allCourses.stream().anyMatch(course -> course.getCourseCode().contains(enteredCourse));
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    protected Schedule generateSections(ArrayList<String> enteredCourses, RefactoredMain.Session session, int year) {
        System.out.println("Enter courses");
        System.out.println(enteredCourses);
        System.out.println(session);
        System.out.println(year);
        List<Course> allCourses = RefactoredMain.courses;
        // Check if the courses are valid. I.e. all entered courses are in the allCourses list
        if(!isValid(enteredCourses)) {
            System.out.println("Invalid courses");
            validCourses = false; // If not, return not valid. Ideally, the function will be called before this so this will not be needed but added for safety
            return null;
        }

        // If yes, add the courses to the schedule
        ArrayList<Course> potentialSchedule = new ArrayList<>();
        for (String enteredCourse : enteredCourses) {
            for (Course course : allCourses) {
                if (course.getCourseCode().contains(enteredCourse) && course.getSession().equals(session) && course.getYear() == year) {
                    potentialSchedule.add(course);
                    // Potential schedule is a list of all courses that match the entered courses in the entered session and year
                }
            }
        }
        // Then check the schedule for time conflicts
        for (int i = 0; i < potentialSchedule.size(); i++) {
            Course course1 = potentialSchedule.get(i);
            for (int j = i + 1; j < potentialSchedule.size(); j++) {
                Course course2 = potentialSchedule.get(j);
                if (course1.hasTimeConflict(course2)) {
                    ArrayList<Course> coursesToRemove = handleTimeConflicts(new ArrayList<>(List.of(course1, course2)), potentialSchedule);
                    potentialSchedule.removeAll(coursesToRemove);
                }
            }
        }
        // Remove duplicate courses. If you want a different course, blacklist the course this generates
        ArrayList<String> uniqueCourseCodes = new ArrayList<>();
        for (Course course : potentialSchedule) {
            if(!uniqueCourseCodes.contains(course.getAmbiguousCourseCode())) {
                uniqueCourseCodes.add(course.getAmbiguousCourseCode());
            } else {
                potentialSchedule.remove(course);
            }
        }
        Schedule newSchedule = new Schedule();
        for (Course course : potentialSchedule) {
            newSchedule.addCourse(course.getID());
        }


        return newSchedule;
    }

    protected ArrayList<Schedule> generateCourses(ArrayList<String> courses, int numSchedueles) {
        return null;
    }

    protected ArrayList<Schedule> generateCourses(ArrayList<String> courses, int numSchedueles, HashMap<String, ArrayList<Course>> coursePrerequisites, HashMap<String, Boolean> courseIsRequired, HashMap<String, Integer> courseYearsOffered) {
        return null;
    }

    protected ArrayList<Course> handleTimeConflicts(ArrayList<Course> conflictingCourses, ArrayList<Course> potentialSchedule) {
        ArrayList<Course> coursesToRemove = new ArrayList<>();
        // Go through all courses in conflictingCourses. Checks all those against potentailSchedule to find the courses that work best and remove rest
        for (Course course : conflictingCourses) {
            boolean isBest = true;
            for (Course potentialCourse : potentialSchedule) {
                if (course.hasTimeConflict(potentialCourse)) {
                    isBest = false;
                    break;
                }
            }
            if (!isBest) {
                coursesToRemove.add(course);
            }
        }
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
