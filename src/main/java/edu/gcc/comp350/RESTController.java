package edu.gcc.comp350;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")

public class RESTController {

    @GetMapping("/test")
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

    @PostMapping("/generateSchedule")
    public ResponseEntity<?> generateSchedule(@RequestBody Map<String, Object> request) {
        ArrayList<String> enteredCourses = (ArrayList<String>) request.get("enteredCourses");
        String session = (String) request.get("session");
        Integer year = (Integer) request.get("year");
        // Check if the year is valid
        if (year == null || year <= 0) {
            System.out.println("Invalid year entered: " + year);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid year entered: " + year);
        }
        // Process the data...
        AutoScheduler autoScheduler = new AutoScheduler();
        System.out.println("Generating schedule...");
        // Generate sections
        enteredCourses.removeIf(String::isEmpty);
        // Check if the courses are valid. I.e. all entered courses are in the allCourses list
        if(!autoScheduler.isValid(enteredCourses)) {
            System.out.println("Invalid courses entered: " + enteredCourses);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid courses entered: " + enteredCourses);
        }

        // Convert session string to RefactoredMain.Session enum
        RefactoredMain.Session sessionEnum = switch (session.toUpperCase()) {
            case "FALL" -> RefactoredMain.Session.FALL;
            case "WINTERONLINE" -> RefactoredMain.Session.WINTER;
            case "SPRING" -> RefactoredMain.Session.SPRING;
            case "EARLYSUMMER" -> RefactoredMain.Session.EARLYSUMMER;
            case "LATESUMMER" -> RefactoredMain.Session.LATESUMMER;
            default -> RefactoredMain.Session.BLANK;
        };

        Schedule newSchedule = autoScheduler.generateSections(enteredCourses, sessionEnum, year);    // Generate sections

        if (newSchedule == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: No courses match entered session.");
        }
        return ResponseEntity.ok("Courses have been Generated"); // newSchedule.getCourses()
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
        System.out.println("onLoad() called");
    }

    @GetMapping("/allAmbiguousCourses")
    public ResponseEntity<List<String>> getAllAmbiguousCourses() {
        List<String> courseNames = RefactoredMain.courses.stream()
                .map(Course::getAmbiguousCourseCode) // Assuming `getAmbiguousCourseCode` returns the course name
                .toList();
        return ResponseEntity.ok(courseNames.stream().distinct().collect(Collectors.toList()));
    }

    @GetMapping("/allCourses")
    public ResponseEntity<List<String>> getAllCourses() {
        List<String> courseNames = RefactoredMain.courses.stream()
                .map(Course::getCourseCode) // Assuming `getCourseCode` returns the course name
                .toList();
        return ResponseEntity.ok(courseNames);
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
            String cutSession = session.split("_")[1];
            if(session.split("_").length > 2) {
                cutSession += session.split("_")[2];
            }
            int year = Integer.parseInt(session.split("_")[0]);
            RefactoredMain.Session finalSession = RefactoredMain.Session.BLANK;
            switch(cutSession.toUpperCase()) {
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

    /* Login Functions */

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

    /* Sign Up Functions */

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

    /* Schedule Functions */
    @RequestMapping("/schedule")
    public ResponseEntity<List<String>> getSchedule(@RequestParam("id") int id) throws SQLException {

        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        // Get courses from db
        String sql = "SELECT scheduleTitle FROM Schedule WHERE id = ?";
        try (PreparedStatement pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("scheduleTitle");
                Schedule schedule = new Schedule(name, id);
                ArrayList<String> courseJSONList = new ArrayList<>();
                for(Course course : schedule.getCourses()) {
                    courseJSONList.add(course.toJson());
                }
                return ResponseEntity.ok(courseJSONList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok(null);
    }
}
