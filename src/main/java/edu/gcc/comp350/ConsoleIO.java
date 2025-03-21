package edu.gcc.comp350;

import java.sql.Time;
import java.util.*;

import static edu.gcc.comp350.Main.*;

public class ConsoleIO {
    List<String> commands = Arrays.asList("add", "remove", "save", "load", "delete", "new", "filter", "search", "print", "schedule", "faculty");
    Filter filter = new Filter();

    /**
     * runs testing cycle for console input
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        String in = "";
        String command = "";
        System.out.println("Running:");

        // complete cycle
        while (!command.equals("quit") && !command.equals("q")) {
            System.out.print("> ");
            in = sc.nextLine();
            String[] tokens = in.trim().split(" ", 2);
            command = tokens[0];

            switch (command) {
                case "help":
                    help();
                    break;
                case "add":
                    if (tokens.length > 1) {
                        int referenceNumber = Integer.parseInt(tokens[1]);
                        add(referenceNumber);
                    }
                    break;
                case "remove":
                    if (tokens.length > 1) {
                        int referenceNumber = Integer.parseInt(tokens[1]);
                        remove(referenceNumber);
                    }
                    break;
                case "undo":
                    undo();
                    break;
                case "save":
                    save();
                    break;
                case "load":
                    if (tokens.length > 1) {
                        int schedule = Integer.parseInt(tokens[1]);
                        load(schedule);
                    }
                    break;
                case "delete":
                    if (tokens.length > 1) {
                        int schedule = Integer.parseInt(tokens[1]);
                        delete(schedule);
                    }
                    break;
                case "new":
                    newSchedule();
                    break;
                case "filter":
                    if (tokens.length > 2) {
                        String type = tokens[1];
                        String val = tokens[2];
                        filter(type, val);
                    }
                    break;
                case "search":
                    if (tokens.length > 1) {
                        String keywords = in.substring(command.length()).trim();
                        search(keywords);
                    }
                    break;
                case "print":
                    if (tokens.length > 1) {
                        int referenceNumber = Integer.parseInt(tokens[1]);
                        print(referenceNumber);
                    }
                    break;
                case "schedule":
                    schedule();
                    break;
                case "faculty":
                    faculty();
                    break;
                default:
                    System.out.println("Invalid command, help or try again");
            }
        }
    }

    /**
     * Add course instance to current schedule
     * @param referenceNumber int reference number of course instance
     */
    private void add(int referenceNumber) {
        Course newCourse = null;
        boolean conflictDetected = false; // Reset conflict detection

        // Find the course with the given reference number
        for (Course course : Main.courses) {
            if (course.getID() == referenceNumber) {
                newCourse = course;
                break;
            }
        }

        if (newCourse == null) {
            System.out.println("Course with reference number " + referenceNumber + " not found.");
            return;
        }

        // Check for conflicts in the current schedule
        for (Course existingCourse : currentSchedule.getCourses()) {
            if (currentSchedule.hasDayConflict(existingCourse, newCourse) && currentSchedule.hasTimeConflict(existingCourse, newCourse)) {
                conflictDetected = true;
                System.out.println("Error: Course " + newCourse.getCourseTitle() +
                        " conflicts with " + existingCourse.getCourseTitle() +
                        " and cannot be added to the schedule.");

                Scanner scanner = new Scanner(System.in);
                System.out.println("Do you want to remove the conflicting course and add this course? (yes/no)");
                String response = scanner.nextLine().trim().toLowerCase();

                if ("yes".equals(response)) {
                    currentSchedule.removeCourse(existingCourse.getID()); // Remove the conflicting course
                    currentSchedule.addCourse(referenceNumber); // Add the new course
                    System.out.println("Conflicting course removed and new course added.");
                    return;
                } else {
                    System.out.println("Course not added due to conflict.");
                    return;
                }
            }
        }

        if (!conflictDetected) {
            currentSchedule.addCourse(referenceNumber);
            System.out.println("Added course with reference number " + referenceNumber);
        }
    }

    /**
     * removes course instance from current schedule
     * @param referenceNumber int reference number of course instance
     */
    private void remove(int referenceNumber) {
        currentSchedule.removeCourse(referenceNumber);
        System.out.println("Removed course with reference number " + referenceNumber);
    }

    /**
     * saves current schedule to database
     */
    private void save() {
        currentStudent.saveSchedule(currentSchedule);
        System.out.println("Saved schedule");
    }

    /**
     * loads schedule
     * @param schedule int id of desired schedule
     */
    private void load(int schedule) {
        currentSchedule = currentStudent.getSchedule(schedule);
        System.out.println("Loaded schedule " + schedule);
    }

    /**
     * deletes schedule from database
     * @param schedule int id of desired schedule
     */
    private void delete(int schedule) {
        Schedule to_delete = currentStudent.getSchedule(schedule);
        currentStudent.deleteSchedule(to_delete);
        System.out.println("Deleted schedule " + schedule);
    }

    /**
     * makes new empty schedule for student
     * this schedule becomes current schedule
     */
    private void newSchedule() {
        currentSchedule = currentStudent.addSchedule(new Schedule());
        System.out.println("Now working in new schedule");
    }

    /**
     * edit search filter option to given value
     * @param type String option to be edited
     * @param val String new value to set option to
     */
    private void filter(String type, String val) {
        switch (type) {
            case "days" :
                filter.setCourse(val);
            case "start" :
                filter.setStartTime(val);
            case "end" :
                filter.setEndTime(val);
            case "session" :
                filter.setCourseSession(val);
            case "courseCode" :
                filter.setCourseCodes(Arrays.asList(val.split(", ")));
            case "department" :
                filter.setDepartment(val);
            default :
                System.out.println("Invalid filter type");
        }
    }

    /**
     * searches and prints results based on keywords
     * @param keywords String to search by
     */
    private void search(String keywords) {
        Search search = new Search(keywords, filter);
        search.search(keywords);
        for (Course course : search.getSearchResults()) {
            System.out.println(course.toString());
        }
    }

    /**
     * prints information of desired course
     * @param id int id of desired schedule
     */
    private void print(int id) {
        for (Course course : courses) {
            if (course.getID() == id) {
                System.out.println(course.toString());
            }
        }
    }

    /**
     * print contents of current schedule
     */
    private void schedule() {
        printSchedule();
    }

    /**
     * Undoes the last change made to the current schedule
     */
    private void undo() {
        if (!currentSchedule.getCourses().isEmpty()) {
            currentSchedule.undo();
            System.out.println("Last change undone.");
        } else {
            System.out.println("No changes to undo.");
        }
    }

    /**
     * Displays all professors from the database.
     */
    private void faculty() {
        DatabaseConnect dbConnect = new DatabaseConnect();
        dbConnect.connect();  // Ensure connection to the database
        List<Professor> professors = dbConnect.getAllProfessors();  // Get all professors

        if (professors.isEmpty()) {
            System.out.println("No professors found.");
        } else {
            System.out.println("List of Professors:");
            for (Professor professor : professors) {
                System.out.println(professor);
            }
        }
    }


    /**
     * Print the current schedule in a formatted manner
     */
    private void printSchedule() {
        System.out.println("Weekly Schedule for: " + currentSchedule.getName());

        // Print all classes at the top
        System.out.println("\nAll Classes in the Schedule:");
        List<Course> sortedCourses = new ArrayList<>(currentSchedule.getCourses());
        sortedCourses.sort(Comparator.comparing(Course::getStartTime));

        for (Course course : sortedCourses) {
            System.out.println(String.format("Course Code: %-10s | Days: %-5s | Start Time: %-5s | End Time: %-5s | Title: %-20s",
                    course.getCourseCode(), String.join(", ", course.getCourseDays()), course.getStartTime(), course.getEndTime(), course.getCourseTitle()));
        }

        // Days of the week
        String[] days = {"M", "T", "W", "R", "F"};

        // Iterate through each day and print courses
        for (String day : days) {
            System.out.println("\n" + getDayName(day) + ":");
            List<Course> coursesForDay = new ArrayList<>();

            // Filter courses for this day
            for (Course course : currentSchedule.getCourses()) {
                if (course.getCourseDays().contains(day)) {
                    coursesForDay.add(course);
                }
            }

            // Sort courses by start time
            coursesForDay.sort(Comparator.comparing(Course::getStartTime));

            if (coursesForDay.isEmpty()) {
                System.out.println("  No classes scheduled.");
                continue;
            }

            String lastEndTime = "08:00"; // Assume earliest class start time

            for (Course course : coursesForDay) {

                // Print course details
                System.out.println("  " + course.getStartTime() + " - " + course.getEndTime() + " | " + course.getCourseTitle());

                // Update last end time
                lastEndTime = course.getEndTime();
            }
        }
    }

    // Helper method to convert day abbreviations to full names
    private String getDayName(String abbreviation) {
        switch (abbreviation) {
            case "M": return "Monday";
            case "T": return "Tuesday";
            case "W": return "Wednesday";
            case "R": return "Thursday";
            case "F": return "Friday";
            default: return "Unknown";
        }
    }



    /**
     * print all commands and syntax to console
     */
    private void help() {
        System.out.println("Commands:");
        System.out.println("add <referenceNumber>");
        System.out.println("remove <referenceNumber>");
        System.out.println("save");
        System.out.println("load <schedule>");
        System.out.println("delete <schedule>");
        System.out.println("new");
        System.out.println("filter <filter type> <filter value>");
        System.out.println("search <keyword string>");
        System.out.println("print <id>");
        System.out.println("schedule");
        System.out.println("faculty");
    }
}
