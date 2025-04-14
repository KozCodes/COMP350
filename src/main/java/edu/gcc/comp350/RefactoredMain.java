package edu.gcc.comp350;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.ArrayList;
import java.util.List;
import  java.sql.*;

@SpringBootApplication
public class RefactoredMain {

    public static List<Course> courses = new ArrayList<>();
    protected static List<Professor> professors;
    public static Student currentStudent;
    protected static Search search;
    protected static Schedule currentSchedule;

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
        SpringApplication.run(RefactoredMain.class, args);
    }

}
