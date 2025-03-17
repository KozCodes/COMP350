package edu.gcc.comp350;

import java.util.ArrayList;
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

    protected static List<Course> courses;
    protected static List<Professor> professors;
    protected static Student currentStudent;
    protected static Search search;
    protected static Schedule currentSchedule;

    protected static DatabaseConnect db = new DatabaseConnect();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        onLoad();
        ArrayList<Object> idList = db.select("startTime","Courses");
        for (Object id : idList) {
            System.out.println(id);
        }
        db.disconnect();
    }

    protected static void onLoad() throws SQLException, ClassNotFoundException {
        connect();
        db.resetDatabase();
    }


    protected static void connect() {
        // connection string
        db.connect();
    }

    protected static void displaySchedule(Schedule schedule) {

    }

    private static void loadCourses() {

    }

    private static void loadProfessors() {

    }

}
