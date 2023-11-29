package other;

import java.util.Scanner;

public class Util {

    public enum Color {
        RED,    // Indicates error
        GREEN   // Indicates success
    }

    /**
     * Repeatedly asks the user for an input until
     * a non-blank/non-empty one is given.
     *
     * @param prompt Prompt to be displayed to the user.
     */
    public static String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (input.isEmpty()) {
                print(Color.RED, "Input cannot be empty");
            } else if (input.isBlank()) {
                print(Color.RED, "Input cannot be blank");
            } else {
                return input;
            }
        }
    }

    /**
     * Prints an error or success message to the console.
     *
     * @param color     Color to be used in output.
     * @param message   Message to be displayed.
     * @see Color
     */
    public static void print(Color color, String message) {
        String colorCode = switch (color) {
            case RED -> "\033[91m";
            case GREEN -> "\033[92m";
        };

        System.out.println(colorCode + message + "\033[0m");
    }

}
