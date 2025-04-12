package edu.gcc.comp350;

import java.sql.Time;
import java.util.*;

import static edu.gcc.comp350.RefactoredMain.*;

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
        for (Course course : RefactoredMain.courses) {
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
                List<String> days = Arrays.asList(val.split(", "));
                List<RefactoredMain.Days> finalDays = new ArrayList<>();

                for (int j = 0; j < days.size(); j++) {
                    switch(days.get(j)) {
                        case "M" -> finalDays.add(RefactoredMain.Days.M);
                        case "T" -> finalDays.add(RefactoredMain.Days.T);
                        case "W" -> finalDays.add(RefactoredMain.Days.W);
                        case "R" -> finalDays.add(RefactoredMain.Days.R);
                        case "F" -> finalDays.add(RefactoredMain.Days.F);
                    }
                }

                filter.setCourse(finalDays);
            case "start" :
                List<String> tempTimes = Arrays.asList(val.split(", "));
                List<Time> startTimes = new ArrayList<>();
                for (String time : tempTimes) {
                    startTimes.add(Time.valueOf(time));
                }
                filter.setStartTime(startTimes);
            case "end" :
                List<String> tempTimes2 = Arrays.asList(val.split(", "));
                List<Time> endTimes = new ArrayList<>();
                for (String time : tempTimes2) {
                    endTimes.add(Time.valueOf(time));
                }
                filter.setEndTime(endTimes);
            case "session" :
                RefactoredMain.Session finalSession = RefactoredMain.Session.BLANK;

                switch(val) {
                    case "FALL" -> finalSession = RefactoredMain.Session.FALL;
                    case "WINTER" -> finalSession = RefactoredMain.Session.WINTER;
                    case "SPRING" -> finalSession = RefactoredMain.Session.SPRING;
                    case "EARLYSUMMER" -> finalSession = RefactoredMain.Session.EARLYSUMMER;
                    case "LATESUMMER" -> finalSession = RefactoredMain.Session.LATESUMMER;
                }
                filter.setCourseSession(finalSession);
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
            System.out.println(String.format("Course ID: %-5d | Course Code: %-10s | Days: %-5s | Start Time: %-5s | End Time: %-5s | Title: %-20s",
                    course.getID(), course.getCourseCode(), String.join(", ", course.getCourseDays()), course.getStartTime(), course.getEndTime(), course.getCourseTitle()));
        }
        // Days of the week
        String[] days = {"M", "T", "W", "R", "F"};
        // Iterate through each day and print the schedule from 08:00 to 21:00
        for (String day : days) {
            System.out.println("\n" + getDayName(day) + " Schedule:");
            List<Course> coursesForDay = new ArrayList<>();
            for (Course course : currentSchedule.getCourses()) {
                if (course.getCourseDays().contains(day)) {
                    coursesForDay.add(course);
                }
            }
            // Sort courses by start time
            coursesForDay.sort(Comparator.comparing(Course::getStartTime));
            // Schedule time slots from 08:00 to 21:00
            String currentTime = "08:00";
            String endTime = "21:00";
            while (currentTime.compareTo(endTime) < 0) {
                Course scheduledCourse = null;
                // Check if there is a course at this time
                for (Course course : coursesForDay) {
                    if (course.getStartTime().equals(currentTime)) {
                        scheduledCourse = course;
                        break;
                    }
                }
                if (scheduledCourse != null) {
                    // Print the course in its respective time slot
                    System.out.println(String.format("  %s - %s | %s (ID: %d)",
                            scheduledCourse.getStartTime(), scheduledCourse.getEndTime(), scheduledCourse.getCourseTitle(), scheduledCourse.getID()));

                    currentTime = getNextTimeSlot(currentTime);

                    // Move to the next available slot
                    } else {
                    // Print an empty time slot
                    System.out.println(String.format("  %s - %s | No class scheduled", currentTime, getNextTimeSlot(currentTime)));
                    currentTime = getNextTimeSlot(currentTime);
                }
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

    // Helper method to advance time by 50-minute intervals (standard class duration)
    private String getNextTimeSlot(String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);
        minute += 60;
        if (minute >= 60) {
            hour += 1;
            minute -= 60;    }
        return String.format("%02d:%02d", hour, minute);
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
