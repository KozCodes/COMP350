package edu.gcc.comp350;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            Course course = new Course(currentId, courseTitle, professor, session, startTime, endTime, courseDays, courseDept, courseCode);
            RefactoredMain.courses.add(course);
        }
    }

}
