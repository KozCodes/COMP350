package edu.gcc.comp350;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Professor {

    private int id;
    private int score;
    private String name;
    private String department;

    public Professor(int id, int score, String name, String department) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.department = department;
    }

    public int getID() { return id; }
    public int getScore() { return score; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    public static List<Professor> fromJsonFile(String filePath) {
        List<Professor> professors = new ArrayList<>();
        Set<String> existingProfessors = new HashSet<>(); // Track existing professors by name (or use IDs if available)

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONArray classes = jsonObj.getJSONArray("classes");
            int idCounter = 1; // Unique ID for professors

            for (int i = 0; i < classes.length(); i++) {
                JSONObject classObj = classes.getJSONObject(i);
                JSONArray facultyArray = classObj.getJSONArray("faculty");
                String department = classObj.optString("subject", "Unknown");

                for (int j = 0; j < facultyArray.length(); j++) {
                    String name = facultyArray.getString(j);

                    // Check if the professor already exists by name (or ID if you have it)
                    if (!existingProfessors.contains(name)) {
                        Professor prof = new Professor(idCounter++, 0, name, department);
                        professors.add(prof);
                        existingProfessors.add(name); // Add to set to avoid duplicate professors
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
        }
        return professors;
    }

    @Override
    public String toString() {
        return "Professor ID: " + id + ", Name: " + name + ", Department: " + department;
    }
}
