package edu.gcc.comp350;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import  java.sql.*;

@SpringBootApplication
public class RefactoredMain {

    public static List<Course> courses = new ArrayList<>();
    protected static List<Professor> professors = new ArrayList<>();
    public static Student currentStudent;
    protected static Search search;
    protected static Schedule currentSchedule;
    protected static List<String> Dictionary = new ArrayList<>();
    protected static List<String> stopwordList = Arrays.asList("a", "an", "the", "is", "are", "was", "were", "be", "being", "been",
            "have", "has", "had", "do", "does", "did", "doing", "will", "shall",
            "should", "can", "could", "may", "might", "must", "ought");
    protected static List<String> codesonly = new ArrayList<>();

    protected static DatabaseConnect db = new DatabaseConnect();

    protected static enum Days {
        BLANK,
        MWF,
        TR,
        M,
        T,
        W,
        R,
        F,
        MW,
        WF
    }

    protected static enum Session {
        BLANK,
        FALL,
        WINTER,
        SPRING,
        EARLYSUMMER,
        LATESUMMER
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
// If changes are made to the database structure, uncomment these lines and run once to reset the database
       // db.connect();
       // db.clearDatabase();
       // db.createDatabase();
      //  db.resetCoursesInDatabase();
       // db.resetProfessorsInDatabase();
       // db.populateProfessorsInDatabase();
      //  db.setProfessorsInDatabase();
      //  db.ProfRatings();
      //  db.disconnect();

        runFunction();

       SpringApplication.run(RefactoredMain.class, args);
    }

    public static String runFunction() throws SQLException, ClassNotFoundException {
        onLoad();
        String sql = "SELECT id, name, major, minor FROM Student WHERE id = ?";
        try (var pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String major = rs.getString("major");
                    ArrayList<String> minors = new ArrayList<>(List.of(rs.getString("minor").split(" ")));
                    RefactoredMain.currentStudent = new Student(id, name, major, minors);
                } else {
                    RefactoredMain.currentStudent = new Student(0, "John Doe", "Undeclared", new ArrayList<>());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        RefactoredMain.currentSchedule = RefactoredMain.currentStudent.getSchedule(0);

        return "Database Connected.";
    }

    /* Load Database Functions */

    protected static void onLoad() throws SQLException, ClassNotFoundException {
        RefactoredMain.db.connect();
        RefactoredMain.db.createDatabase();
        RefactoredMain.db.resetCoursesInDatabase();
        RefactoredMain.db.resetProfessorsInDatabase();
        RefactoredMain.db.populateProfessorsInDatabase();
        RefactoredMain.db.setProfessorsInDatabase();
        loadProfessors();
        loadCourses();
        loadDictionary();
        System.out.println("onLoad() called");
    }

    private static void loadDictionary() {
        for (int i = 0; i < RefactoredMain.courses.size(); i++) {
            List<String> word  = Arrays.stream(RefactoredMain.courses.get(i).getCourseTitle().split(" ")).toList();
            for (String words : word) {
                if (!RefactoredMain.Dictionary.contains(words)) {
                    RefactoredMain.Dictionary.add(words);
                }
            }
            RefactoredMain.Dictionary.add(RefactoredMain.courses.get(i).getCourseCode());
        }

        for (int i = 0; i < RefactoredMain.professors.size(); i++) {
            List<String> word  = Arrays.stream(RefactoredMain.professors.get(i).getName().split(", ")).toList();
            for (String words : word) {
                if (!RefactoredMain.Dictionary.contains(words)) {
                    RefactoredMain.Dictionary.add(words);
                }
            }
        }
    }

    private static void loadCourses() {
        ArrayList<Object> courseList = RefactoredMain.db.select("id, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode, numSeats, numRegistered", "Courses");
        int currentId = -1;
        int numSeats = -1;
        int numRegistered = -1;
        for (int i = 0; i < courseList.size(); i+=11) {
            currentId = (int) courseList.get(i);
            String courseTitle = (String) courseList.get(i+1);
            String professor = (String) courseList.get(i+2);
            String session = (String) courseList.get(i+3);
            String startTime = (String) courseList.get(i+4);
            String endTime = (String) courseList.get(i+5);
            String courseDays = (String) courseList.get(i+6);
            String courseDept = (String) courseList.get(i+7);
            String courseCode = (String) courseList.get(i+8);
            numSeats = (int) courseList.get(i+9);
            numRegistered = (int) courseList.get(i+10);

            //course Start Times
            List<Time> start = new ArrayList<>();
            if (!startTime.equals("")) {
                for (String times : startTime.split(", ")) {
                    start.add(Time.valueOf(times));
                }
            }

            //course End Times
            List<Time> end = new ArrayList<>();
            if (!endTime.equals("")) {
                for(String times : endTime.split(", ")) {
                    end.add(Time.valueOf(times));
                }
            } else {
                end.add(Time.valueOf("00:00:00"));
            }

            //days
            List<String> splicedDays = Arrays.stream(courseDays.split(", ")).toList();
            List<RefactoredMain.Days> days = new ArrayList<>();

            for (int j = 0; j < splicedDays.size(); j++) {
                switch(splicedDays.get(j)) {
                    case "M" -> days.add(RefactoredMain.Days.M);
                    case "T" -> days.add(RefactoredMain.Days.T);
                    case "W" -> days.add(RefactoredMain.Days.W);
                    case "R" -> days.add(RefactoredMain.Days.R);
                    case "F" -> days.add(RefactoredMain.Days.F);
                }
            }

            //session
            String cutSession = session.split("_")[1].toUpperCase();
            if(session.split("_").length > 2) {
                cutSession += session.split("_")[2];
            }
            int year = Integer.parseInt(session.split("_")[0]);
            RefactoredMain.Session finalSession = RefactoredMain.Session.BLANK;
            switch(cutSession) {
                case "FALL" -> finalSession = RefactoredMain.Session.FALL;
                case "WINTERONLINE" -> finalSession = RefactoredMain.Session.WINTER;
                case "SPRING" -> finalSession = RefactoredMain.Session.SPRING;
                case "EARLYSUMMER" -> finalSession = RefactoredMain.Session.EARLYSUMMER;
                case "LATESUMMER" -> finalSession = RefactoredMain.Session.LATESUMMER;
            }

            professor = professor.replace("[\"", "");
            professor = professor.replace("\"]", "");

            //professors
            Professor prof = new Professor(-1, -1,"John Doe", "BLANK");
            for (int j = 0; j < RefactoredMain.professors.size(); j++) {
                if (professor.equals(RefactoredMain.professors.get(j).getName())) {
                    prof = RefactoredMain.professors.get(j);
                }
            }

            if (!codesonly.contains(courseDept)) {
                codesonly.add(courseDept);
            }

            Course course = new Course(currentId, courseTitle, prof, finalSession, start, end, days, courseDept, courseCode, year, false);
            RefactoredMain.courses.add(course);
        }
    }

    private static void loadProfessors() {
        ArrayList<Object> profList = RefactoredMain.db.select("id, score, professorName, department", "Professors");
        int currentId = -1;
        int score = -1;
        for (int i = 0; i < profList.size(); i+= 4) {
            currentId = (int) profList.get(i);
            score = (int) profList.get(i+1);
            String profname = (String) profList.get(i+2);
            String dept = (String) profList.get(i+3);

            Professor prof = new Professor(currentId, score, profname, dept);
            RefactoredMain.professors.add(prof);
        }
    }

}
