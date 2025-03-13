package edu.gcc.comp350;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleIO {
    List<String> commands = Arrays.asList("add", "remove", "save", "load", "delete", "new", "filter", "search");

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
            command = in.split(" ")[0];
            if (commands.contains(in)) {

            } else if (commands.equals("help")) {
                help();
            }
        }

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
    }
}
