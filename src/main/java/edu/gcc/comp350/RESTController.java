package edu.gcc.comp350;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class RESTController {
    @GetMapping("/currentStudentName")
    public String getCurrentStudentName() {
        return RefactoredMain.currentStudent != null ? RefactoredMain.currentStudent.getName() : "Guest";
    }

    @GetMapping("/studentSchedules")
    public ResponseEntity<List<Map<String, Object>>> getStudentSchedules() {
        // Check if the current student is set
        if (RefactoredMain.currentStudent == null || RefactoredMain.currentStudent.getId() == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Get the current student's ID
        int studentId = RefactoredMain.currentStudent.getId();

        // Filter schedules belonging to the current student
        List<Map<String, Object>> studentSchedules = RefactoredMain.currentStudent.getSchedules().stream()
        .map(schedule -> {
            Map<String, Object> scheduleMap = new HashMap<>();
            scheduleMap.put("id", schedule.getId());
            scheduleMap.put("name", schedule.getName());
            if (!schedule.getCourses().isEmpty()) {
                Course firstCourse = schedule.getCourses().get(0);
                scheduleMap.put("session", firstCourse.getSession());
                scheduleMap.put("year", firstCourse.getYear());
            } else {
                scheduleMap.put("session", "Empty");
                scheduleMap.put("year", 0);
            }
            return scheduleMap;
        })
        .toList();

        System.out.println("Student schedules: " + studentSchedules);

        return ResponseEntity.ok(studentSchedules);
    }

    @PostMapping("/setCurrentSchedule")
    public void setCurrentSchedule(@RequestBody Schedule schedule) {
        if (schedule != null) {
            RefactoredMain.currentSchedule = schedule;
        } else {
            throw new IllegalArgumentException("Schedule cannot be null");
        }
    }

    /* Login Functions */

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) throws SQLException {
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
                HttpSession session = request.getSession(true);
                session.setAttribute("studentId", rs.getInt("id"));
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logged out");
    }

    /* Sign Up Functions */

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) throws SQLException {
        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        // Check if the ID is already taken
        String checkSql = "SELECT id FROM Student WHERE id = ?";
        try (PreparedStatement checkStmt = RefactoredMain.db.conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, request.getId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Student ID already taken.");
            }
        }

        // Check if username is taken
        String userCheckSql = "SELECT username FROM Student WHERE username = ?";
        try (PreparedStatement checkStmt = RefactoredMain.db.conn.prepareStatement(userCheckSql)) {
            checkStmt.setString(1, request.getUsername());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
            }
        }

        // Insert the new student
        String sql = "INSERT INTO Student (id, name, major, minor, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            pstmt.setInt(1, request.getId());
            pstmt.setString(2, request.getName());
            pstmt.setString(3, request.getMajor());
            pstmt.setString(4, request.getMinor());
            pstmt.setString(5, request.getUsername());
            pstmt.setString(6, request.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed: " + e.getMessage());
        }

        return ResponseEntity.ok("Signup successful");
    }


    static class SignupRequest {
        private int id;
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

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
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
        newSchedule.saveSchedule();
        return ResponseEntity.ok("/schedule/"+newSchedule.getId());
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

    /* Schedule Functions */
    @RequestMapping("/schedule")
    public ResponseEntity<String> getSchedule(@RequestParam("id") int id) throws SQLException {

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
                String scheduleJSON = schedule.toJson();
                return ResponseEntity.ok(scheduleJSON);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok(null);
    }

    @GetMapping("/professors")
    public ResponseEntity<List<Professor>> getAllProfessors() throws SQLException {
        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        String sql = "SELECT * FROM Professors";
        List<Professor> professors = new ArrayList<>();

        try (PreparedStatement pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            // Using a Set to avoid duplicates
            Set<Integer> professorIds = new HashSet<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                int score = rs.getInt("score");
                String name = rs.getString("professorName");
                String dept = rs.getString("department");

                // Check if this professor has already been added by their unique id
                if (!professorIds.contains(id)) {
                    professors.add(new Professor(id, score, name, dept));
                    professorIds.add(id);  // Mark this professor as added
                }
            }

            return ResponseEntity.ok(professors);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/{query}/{Days}/{Times}/{EndTimes}/{Session}/{Year}/{CourseCode}/{CourseDepartment}")
    public ResponseEntity<List<String>> HandleSearch(@PathVariable String query, @PathVariable String[] Days, @PathVariable String[] Times,
                                                     @PathVariable String[] EndTimes, @PathVariable String Session, @PathVariable String Year, @PathVariable String CourseCode,
                                                     @PathVariable String CourseDepartment) throws SQLException, ClassNotFoundException {
        Filter filter = new Filter();

        List<RefactoredMain.Days> daysList = new ArrayList<>();

        if (!Days[0].equals("Empty")) {
            for (int i = 0; i < Days.length; i++) {
                switch(Days[i]) {
                    case "Monday" -> daysList.add(RefactoredMain.Days.M);
                    case "Tuesday" -> daysList.add(RefactoredMain.Days.T);
                    case "Wednesday" -> daysList.add(RefactoredMain.Days.W);
                    case "Thursday" -> daysList.add(RefactoredMain.Days.R);
                    case "Friday" -> daysList.add(RefactoredMain.Days.F);
                }

            }
        } else {
            daysList.add(RefactoredMain.Days.BLANK);
        }

        filter.setCourse(daysList);

        List<Time> startTimes = new ArrayList<>();

        if (!Times[0].equals("Empty")) {
            for (int i = 0; i < Times.length; i++) {
                startTimes.add(Time.valueOf(Times[i]));
            }
        } else {
            startTimes.add(Time.valueOf("00:00:00"));
        }

        filter.setStartTime(startTimes);

        List<Time> endTimes = new ArrayList<>();

        if (!EndTimes[0].equals("Empty")) {
            for (int i = 0; i < EndTimes.length; i++) {
                endTimes.add(Time.valueOf(EndTimes[i]));
            }
        } else {
            endTimes.add(Time.valueOf("00:00:00"));
        }

        filter.setEndTime(endTimes);

        RefactoredMain.Session finalSession = RefactoredMain.Session.BLANK;

        switch(Session) {
            case "Fall" -> finalSession = RefactoredMain.Session.FALL;
            case "Winter" -> finalSession = RefactoredMain.Session.WINTER;
            case "Spring" -> finalSession = RefactoredMain.Session.SPRING;
            case "Early Summer" -> finalSession = RefactoredMain.Session.EARLYSUMMER;
            case "Late Summer" -> finalSession = RefactoredMain.Session.LATESUMMER;
            case "Blank" -> finalSession = RefactoredMain.Session.BLANK;
        }

        filter.setCourseSession(finalSession);

        Search search = new Search(Session, filter);
        search.search(query);
        List<Course> courses;
        if (search.getFilteredResults().isEmpty()) {
          courses = search.getSearchResults();
        } else {
            courses = search.getFilteredResults();
        }

        List<String> codes = new ArrayList<>();

        if (!CourseCode.equals("None")) {
            codes.add(CourseCode);
        }

        filter.setCourseCodes(codes);

        filter.setDepartment(CourseDepartment);

        filter.setYear(Integer.valueOf(Year));

        List<String> courseJSON = new ArrayList<>();

        for (Course course : courses) {
            courseJSON.add(course.toJson());
        }

        return ResponseEntity.ok(courseJSON);
    }

    // Fetch Professor Rating
    @GetMapping("/professor/{id}/rating")
    public ResponseEntity<Integer> getProfessorRating(@PathVariable int id) throws SQLException {
        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        String sql = "SELECT score FROM Professors WHERE id = ?";
        try (PreparedStatement pstmt = RefactoredMain.db.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return ResponseEntity.ok(rs.getInt("score"));
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return not found if the professor is not in the database
    }

    // Update Professor Rating
    @PutMapping("/professor/{id}/rating")
    public ResponseEntity<String> updateProfessorRating(@PathVariable int id, @RequestBody RatingRequest ratingRequest, HttpSession session) throws SQLException {
        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        try {
            // 1. Insert new rating into studentRatings
            String insertSql = "INSERT INTO studentRatings (studentId, professorId, rating) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = RefactoredMain.db.conn.prepareStatement(insertSql)) {
                Object studentIdObj = session.getAttribute("studentId");
                if (studentIdObj == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to rate a professor.");
                }
                int studentId = (int) studentIdObj;
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, id);
                insertStmt.setInt(3, ratingRequest.getRating());
                insertStmt.executeUpdate();
            }

            // 2. Recalculate average and count
            String statsSql = "SELECT AVG(rating) AS avgRating, COUNT(*) AS totalRatings FROM studentRatings WHERE professorId = ?";
            double avgRating = 0;
            int numRatings = 0;

            try (PreparedStatement statsStmt = RefactoredMain.db.conn.prepareStatement(statsSql)) {
                statsStmt.setInt(1, id);
                ResultSet rs = statsStmt.executeQuery();
                if (rs.next()) {
                    avgRating = rs.getDouble("avgRating");
                    numRatings = rs.getInt("totalRatings");
                }
            }

            // 3. Update Professors table with new average and count
            String updateSql = "UPDATE Professors SET score = ?, numRatings = ? WHERE id = ?";
            try (PreparedStatement updateStmt = RefactoredMain.db.conn.prepareStatement(updateSql)) {
                updateStmt.setDouble(1, avgRating);
                updateStmt.setInt(2, numRatings);
                updateStmt.setInt(3, id);
                updateStmt.executeUpdate();
            }

            return ResponseEntity.ok("Rating submitted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to submit rating: " + e.getMessage());
        }
    }


    static class RatingRequest {
        private int rating;

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }

    @RequestMapping("/register-course")
    public ResponseEntity<String> registerForCourse(@RequestParam("courseId") int courseId, HttpSession session) throws SQLException {
        if (RefactoredMain.db.conn == null || RefactoredMain.db.conn.isClosed()) {
            RefactoredMain.db.connect();
        }

        Object studentIdObj = session.getAttribute("studentId");
        if (studentIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to register for a course.");
        }
        int studentId = (int) studentIdObj;

        // Fetch course details
        String courseSql = "SELECT numSeats, numRegistered FROM Courses WHERE id = ?";
        try (PreparedStatement courseStmt = RefactoredMain.db.conn.prepareStatement(courseSql)) {
            courseStmt.setInt(1, courseId);
            ResultSet rs = courseStmt.executeQuery();
            if (!rs.next()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
            }

            int numSeats = rs.getInt("numSeats");
            int numRegistered = rs.getInt("numRegistered");

            if (numRegistered >= numSeats) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No available seats for this course.");
            }

            // Insert registration
            String registerSql = "INSERT INTO CourseRegistrations (courseId, studentId) VALUES (?, ?)";
            try (PreparedStatement registerStmt = RefactoredMain.db.conn.prepareStatement(registerSql)) {
                registerStmt.setInt(1, courseId);
                registerStmt.setInt(2, studentId);
                registerStmt.executeUpdate();
            }

            // Update course registration count
            String updateSql = "UPDATE Courses SET numRegistered = numRegistered + 1 WHERE id = ?";
            try (PreparedStatement updateStmt = RefactoredMain.db.conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, courseId);
                updateStmt.executeUpdate();
            }

            return ResponseEntity.ok("Successfully registered for the course.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
}
