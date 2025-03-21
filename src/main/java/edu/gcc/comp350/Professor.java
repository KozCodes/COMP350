package edu.gcc.comp350;

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


    @Override
    public String toString() {
        return "Professor ID: " + id + ", Name: " + name + ", Department: " + department;
    }
}