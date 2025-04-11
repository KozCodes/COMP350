package edu.gcc.comp350;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class RESTController {
    // this must be protected so we all have one contact to the db

    @GetMapping("/runFunction")
    public String runFunction() throws SQLException, ClassNotFoundException {
        onLoad();
        //currentSchedule = currentStudent.getSchedule(0);
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

        RefactoredMain.db.disconnect();

        return RefactoredMain.courses.get(0).toString();//Main.main();
    }

    @GetMapping("/search")
    public String search() throws SQLException, ClassNotFoundException {
      return "";
    }

    protected static void onLoad() throws SQLException, ClassNotFoundException {
        RefactoredMain.db.connect();
        //db.setCoursesInDatabase();
        RefactoredMain.db.createDatabase();
        RefactoredMain.db.resetCoursesInDatabase();
        RefactoredMain.db.resetProfessorsInDatabase();
        RefactoredMain.db.populateProfessorsInDatabase();
        RefactoredMain.db.setProfessorsInDatabase();
        loadCourses();
    }

    private static void loadCourses() {
        ArrayList<Object> courseList = RefactoredMain.db.select("id, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode", "Courses");
        int currentId = -1;
        for (int i = 0; i < courseList.size(); i+=9) {
            currentId = (int) courseList.get(i);
            String courseTitle = (String) courseList.get(i+1);
            String professor = (String) courseList.get(i+2);
            String session = (String) courseList.get(i+3);
            String startTime = (String) courseList.get(i+4);
            String endTime = (String) courseList.get(i+5);
            String courseDays = (String) courseList.get(i+6);
            String courseDept = (String) courseList.get(i+7);
            String courseCode = (String) courseList.get(i+8);


            //course Start Times
            List<Time> start = new ArrayList<>();

            for (String times : startTime.split(", ")) {
                if (!start.contains(Time.valueOf(times))) {
                    start.add(Time.valueOf(times));
                }
            }

            //course End Times
            List<Time> end = new ArrayList<>();

            for(String times : endTime.split(", ")) {
                if (!end.contains(Time.valueOf(times))) {
                    end.add(Time.valueOf(times));
                }
            }

            //days
            List<String> splicedDays = Arrays.stream(courseDays.split(", ")).toList();
            List<RefactoredMain.Days> days = new ArrayList<>();


            //format extraneous cases:
            int numDays = splicedDays.size();
            switch(numDays) {
                case 2 -> {
                   //cases: all same
                   String temp = splicedDays.get(0) + splicedDays.get(1);
                }
                case 3 -> {
                    //cases: all same, 1 different

                }
                case 4 -> {
                    //cases: all same, 1 different, 2 different
                }
            }

            for (int j = 0; j < splicedDays.size(); j++) {
                switch(splicedDays.get(j)) {
                    case "M" -> days.add(RefactoredMain.Days.M);
                    case "T" -> days.add(RefactoredMain.Days.T);
                    case "W" -> days.add(RefactoredMain.Days.W);
                    case "R" -> days.add(RefactoredMain.Days.R);
                    case "F" -> days.add(RefactoredMain.Days.F);
                    case "MW" -> days.add(RefactoredMain.Days.MW);
                    case  "WF" -> days.add(RefactoredMain.Days.WF);
                    case "TR" -> days.add(RefactoredMain.Days.TR);
                    case "MWF" -> days.add(RefactoredMain.Days.MWF);
                }
            }

           //session
            String cutSession = session.split("_")[1];
            int year = Integer.parseInt(session.split("_")[0]);


            RefactoredMain.Session finalSession = RefactoredMain.Session.BLANK;

            switch(cutSession) {
                case "FALL" -> finalSession = RefactoredMain.Session.FALL;
                case "WINTER" -> finalSession = RefactoredMain.Session.WINTER;
                case "SPRING" -> finalSession = RefactoredMain.Session.SPRING;
                case "EARLYSUMMER" -> finalSession = RefactoredMain.Session.EARLYSUMMER;
                case "LATESUMMER" -> finalSession = RefactoredMain.Session.LATESUMMER;
            }

            //AI assisted in helping fill in the cases, which were numerous

            Course course = new Course(currentId, courseTitle, professor, finalSession, start, end, days, courseDept, courseCode, year);
            RefactoredMain.courses.add(course);
        }
    }

}
