package edu.gcc.comp350;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Professor {

    private int id;
    private int score;
    private String name;
    private String department;

    // No-args constructor for Jackson
    public Professor() {}

    public Professor( @JsonProperty int id, @JsonProperty int score, @JsonProperty String name, @JsonProperty String department) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.department = department;
    }

    public int getID() { return id; }
    public int getScore() { return score; }
    public String getName() { return name; }
    public String getDepartment() { return department; }


    @Override
    public String toString() {
        return "Professor ID: " + id + ", Name: " + name + ", Department: " + department;
    }
}