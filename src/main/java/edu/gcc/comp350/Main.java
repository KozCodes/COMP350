package edu.gcc.comp350;

import java.util.List;
import  java.sql.*;

public class Main {
    public Main() {
    }

    protected static enum Days {
        MWF,
        TR
    }

    protected static enum Session {
        FALL,
        WINTER,
        SPRING,
        EARLYSUMMER,
        LATESUMMER
    }

    protected List<Course> courses;
    protected List<Professor> professors;
    protected Student currentStudent;
    protected Search search;
    protected Schedule currentSchedule;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        onLoad();
    }

    protected static void onLoad() throws SQLException, ClassNotFoundException {
        connect();
    }

    public static void connect() {
        // connection string
        var url = "jdbc:sqlite:C:/Users/SODERLUNDJS22/comp350/Scrumbucket/Database/scrumbucketData.db";

        var sql = "SELECT id, professor, session, startTime, endTime, courseDays, courseDept, courseCode, referenceCode, description FROM courses";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") +
                        " | " + rs.getString("professor") +
                        " | " + rs.getInt("session") +
                        " | " + rs.getString("startTime") +
                        " | " + rs.getString("endTime") +
                        " | " + rs.getString("courseDays") +
                        " | " + rs.getString("courseDept") +
                        " | " + rs.getString("courseCode") +
                        " | " + rs.getInt("referenceCode") +
                        " | " + rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected static void displaySchedule(Schedule schedule) {

    }

    private static void loadCourses() {

    }

    private static void loadProfessors() {

    }



}
