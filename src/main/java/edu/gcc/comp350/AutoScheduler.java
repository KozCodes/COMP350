package edu.gcc.comp350;

import java.util.*;

public class AutoScheduler {

    boolean validCourses;

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
        // Filter and group courses by session and year
        HashMap<String, ArrayList<Course>> courseSections = getCourseSections(enteredCourses, session, year);

        if (courseSections.isEmpty()) {
            return null;
        }

        // Retain only valid courses in enteredCourses
        enteredCourses.retainAll(courseSections.keySet());

        // Resolve conflicts and generate a potential schedule
        Schedule potentialSchedule = resolveConflicts(courseSections, enteredCourses);

        // Print course codes for debugging
        potentialSchedule.getCourses().stream()
                .map(Course::getCourseCode)
                .forEach(System.out::println);

        return potentialSchedule;
    }

    private Schedule resolveConflicts(HashMap<String, ArrayList<Course>> courseSections, ArrayList<String> orderOfPreference) {
        Schedule schedule = new Schedule();
        while (true) {
            HashMap<String, ArrayList<Course>> result = backtrack(courseSections, orderOfPreference, 0, schedule);
            if (result != null) {
                return schedule;
            }
            if (orderOfPreference.isEmpty()) {
                return null; // No valid combinations found
            }
            String lastPreference = orderOfPreference.remove(orderOfPreference.size() - 1);
            courseSections.remove(lastPreference);
        }
    }

    private HashMap<String, ArrayList<Course>> backtrack(HashMap<String, ArrayList<Course>> courseSections, ArrayList<String> orderOfPreference, int next_var, Schedule newSchedule) {
        HashMap<String, ArrayList<Course>> result = null;
        if(next_var == orderOfPreference.size()) {
            return courseSections;
        }
        String courseCode = orderOfPreference.get(next_var);
        for(Course course : courseSections.get(courseCode)) {
            HashMap<String, ArrayList<Course>> courseSectionsCopy = new HashMap<>(courseSections);
            courseSectionsCopy.put(courseCode, new ArrayList<>(Collections.singletonList(course)));
            // Check if the new schedule has any time conflicts
            if(newSchedule.addCourse(course.getID()) != 0) {
                result = backtrack(courseSectionsCopy, orderOfPreference, next_var + 1, newSchedule);
            }
            // Check if the new schedule has any time conflicts
            if(result != null) {
                return result;
            }
            // If no valid combination is found, remove the course from the schedule
            newSchedule.removeCourse(course.getID());
        }
        // If no valid combination is found, return null
        return null;
    }

    private HashMap<String, ArrayList<Course>> getCourseSections(ArrayList<String> enteredCourses, RefactoredMain.Session session, int year) {
        List<Course> allCourses = RefactoredMain.courses;
        HashMap<String, ArrayList<Course>> courseSections = new HashMap<>();
        for (String enteredCourse : enteredCourses) {
            for (Course course : allCourses) {
                if (course.getAmbiguousCourseCode().equals(enteredCourse) && course.getSession().equals(session) && course.getYear() == year) {
                    courseSections.putIfAbsent(course.getAmbiguousCourseCode(), new ArrayList<>());
                    courseSections.get(course.getAmbiguousCourseCode()).add(course);
                    //System.err.println("Course added: " + course.getCourseCode() + " " + course.getSession() + " " + course.getYear());
                }
                else {
                    if (course.getCourseCode().contains(enteredCourse) && course.getYear() != year) {
                        //System.out.println("Course year does not match: " + course.getCourseCode() + " " + course.getYear());
                    }
                    if (course.getCourseCode().contains(enteredCourse) && course.getYear() == year && !course.getSession().equals(session)) {
                        //System.out.println("Course session does not match in selected year: " + course.getCourseCode() + " " + course.getSession());
                    }
                }
            }
        }
        return courseSections;
    }


}
