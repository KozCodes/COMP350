package edu.gcc.comp350;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")

public class RESTController {

    @GetMapping("/runFunction")
    public String runFunction() throws SQLException, ClassNotFoundException {
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
        RefactoredMain.db.disconnect();
        return RefactoredMain.courses.get(0).toString();
    }

    protected static void onLoad() throws SQLException, ClassNotFoundException {
        RefactoredMain.db.connect();
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws SQLException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        String sql = "SELECT * FROM Student WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) throws SQLException {
        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        String sql = "INSERT INTO Student (name, major, minor, username, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getName());
            pstmt.setString(2, request.getMajor());
            pstmt.setString(3, request.getMinor());
            pstmt.setString(4, request.getUsername());
            pstmt.setString(5, request.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
        }

        return ResponseEntity.ok("Signup successful");
    }

    static class SignupRequest {
        private String username, password, name, major, minor;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getMajor() { return major; }
        public void setMajor(String major) { this.major = major; }

        public String getMinor() { return minor; }
        public void setMinor(String minor) { this.minor = minor; }
    }
}
