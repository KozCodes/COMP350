package edu.gcc.comp350;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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

            Time start = Time.valueOf(startTime);
            Time end = Time.valueOf(endTime);
            String cutSession = session.split("_")[0];
            String splicedDays = courseDays.replace(",", "");
            RefactoredMain.Session finalSession = RefactoredMain.Session.BLANK;
            RefactoredMain.Days days = RefactoredMain.Days.BLANK;

            //AI assisted in helping fill in the cases, which were numerous

            switch(cutSession) {
                case "FALL" -> finalSession = RefactoredMain.Session.FALL;
                case "WINTER" -> finalSession = RefactoredMain.Session.WINTER;
                case "SPRING" -> finalSession = RefactoredMain.Session.SPRING;
                case "EARLYSUMMER" -> finalSession = RefactoredMain.Session.EARLYSUMMER;
                case "LATESUMMER" -> finalSession = RefactoredMain.Session.LATESUMMER;
            }

            switch(splicedDays) {
                case "MWF" -> days = RefactoredMain.Days.MWF;
                case "TR" -> days = RefactoredMain.Days.TR;
                case "M" -> days = RefactoredMain.Days.M;
                case "T" -> days = RefactoredMain.Days.T;
                case "W" -> days = RefactoredMain.Days.W;
                case "R" -> days = RefactoredMain.Days.R;
                case "F" -> days = RefactoredMain.Days.F;
                case "MW" -> days = RefactoredMain.Days.MW;
                case "WF" -> days = RefactoredMain.Days.WF;
                case "MTWF" -> days = RefactoredMain.Days.MTWF;
                case "MWRF" -> days = RefactoredMain.Days.MWRF;
            }

            Course course = new Course(currentId, courseTitle, professor, finalSession, start, end, days, courseDept, courseCode);
            RefactoredMain.courses.add(course);
        }
    }

}
