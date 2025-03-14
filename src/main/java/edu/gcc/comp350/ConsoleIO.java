package edu.gcc.comp350;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleIO {
    List<String> commands = Arrays.asList("add", "remove", "save", "load", "delete", "new", "filter", "search", "print", "schedule");

    /**
     * runs testing cycle for console input
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        String in = "";
        String command = "";
        System.out.println("Running:");

        // complete cycle
        while (!command.equals("quit") && !command.equals("q")) {
            System.out.print(">");
            in = sc.nextLine();
            String[] tokens = in.split(" ");
            command = tokens[0];

            switch (command) {
                case "help" :
                    help();
                    break;
                case "add" :
                    if (tokens.length > 1) {
                        int referenceNumber = Integer.parseInt(tokens[1]);
                        add(referenceNumber);
                    }
                    break;
                case "remove" :
                    if (tokens.length > 1) {
                        int referenceNumber = Integer.parseInt(tokens[1]);
                        remove(referenceNumber);
                    }
                    break;
                case "save" :
                    save();
                    break;
                case "load" :
                    if (tokens.length > 1) {
                        int schedule = Integer.parseInt(tokens[1]);
                        load(schedule);
                    }
                    break;
                case "delete" :
                    if (tokens.length > 1) {
                        int schedule = Integer.parseInt(tokens[1]);
                        delete(schedule);
                    }
                    break;
                case "new" :
                    newSchedule();
                    break;
                case "filter" :
                    if (tokens.length > 2) {
                        String type = tokens[1];
                        String val = tokens[2];
                        filter(type, val);
                    }
                    break;
                case "search" :
                    if (tokens.length > 1) {
                        String keywords = in.substring(command.length()).trim();
                        search(keywords);
                    }
                    break;
                case "print" :
                    if (tokens.length > 1) {
                        int referenceNumber = Integer.parseInt(tokens[1]);
                        print(referenceNumber);
                    }
                    break;
                case "schedule" :
                    schedule();
                    break;
                default:
                    System.out.println("Invalid command, help or try again");
            }
        }

    }

    /**
     * add course instance to current schedule
     * @param referenceNumber int reference number of course instance
     */
    private void add(int referenceNumber) {

    }

    /**
     * removes course instance from current schedule
     * @param referenceNumber int reference number of course instance
     */
    private void remove(int referenceNumber) {

    }

    /**
     * saves current schedule to database
     */
    private void save() {

    }

    /**
     * loads schedule
     * @param schedule int id of desired schedule
     */
    private void load(int schedule) {

    }

    /**
     * deletes schedule from database
     * @param schedule int id of desired schedule
     */
    private void delete(int schedule) {

    }

    /**
     * makes new empty schedule for student
     * this schedule becomes current schedule
     */
    private void newSchedule() {

    }

    /**
     * edit search filter option to given value
     * @param type String option to be edited
     * @param val String new value to set option to
     */
    private void filter(String type, String val) {

    }

    /**
     * searches and prints results based on keywords
     * @param keywords String to search by
     */
    private void search(String keywords) {

    }

    /**
     * prints information of desired course
     * @param referenceNumber int id of desired schedule
     */
    private void print(int referenceNumber) {

    }

    /**
     * print contents of current schedule
     */
    private void schedule() {

    }

    /**
     * print all commands and syntax to console
     */
    private void help() {
        System.out.println("Commands:");
        System.out.println("add <referenceNumber>");
        System.out.println("remove <referenceNumber>");
        System.out.println("save");
        System.out.println("load <schedule>");
        System.out.println("delete <schedule>");
        System.out.println("new");
        System.out.println("filter <filter type> <filter value>");
        System.out.println("search <keyword string>");
        System.out.println("print <referenceNumber>");
        System.out.println("schedule");
    }
}
