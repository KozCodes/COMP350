package edu.gcc.comp350;

//import org.json.JSONArray;
//import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Professor {

    private int id;
    private int score;
    private String name;
    private String title;
    private String department;

    public Professor(int id, int score, String name, String title, String department) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.title = title;
        this.department = department;
    }

    protected int getID() {
        return id;
    }

    protected int getScore() {
        return score;
    }

    protected String getName() {
        return name;
    }

    protected String getTitle() {
        return title;
    }

    protected String getDepartment() {
        return department;
    }

//    public static List<Professor> fromJson(String jsonString) {
//        List<Professor> professors = new ArrayList<>();
//        JSONObject jsonObj = new JSONObject(jsonString);
//        JSONArray classes = jsonObj.getJSONArray("classes");
//        int idCounter = 1;  // To assign unique IDs
//
//        for (int i = 0; i < classes.length(); i++) {
//            JSONObject classObj = classes.getJSONObject(i);
//            JSONArray facultyArray = classObj.getJSONArray("faculty");
//            String department = classObj.optString("subject", "Unknown");
//            String title = classObj.optString("name", "Unknown");
//
//            for (int j = 0; j < facultyArray.length(); j++) {
//                String name = facultyArray.getString(j);
//
//                Professor prof = new Professor(idCounter++, 0, name, title, department);
//                professors.add(prof);
//            }
//        }
//
//        return professors;
//    }
}
