package org.hyperskill;

import org.hyperskill.model.Pair;

import java.util.Scanner;

public class Main {
    private static final String COMMAND_LINE = "Please choose mode: ENCODE, DECODE, SEND";

    public static void main(String[] args) {
        String command;
        try (Scanner scanner = new Scanner(System.in)) {
            command = readInput(scanner).toString();
        }
        System.out.println(Pair.ONE_ONE);
        System.out.println(Pair.ONE_ONE);
        System.out.println(Pair.ONE_ONE);
    }

    private static WorkingMode readInput(Scanner scanner) {
        WorkingMode selection = null;
        System.out.println(COMMAND_LINE);
        String input;
        while (selection == null) {
            input = scanner.nextLine().trim().toUpperCase();
            if (WorkingMode.contains(input)) {
                selection = WorkingMode.valueOf(input);
            } else {
                System.out.println(COMMAND_LINE);
            }
        }
        return selection;
    }



}
