package other;

import java.util.Scanner;

public class Util {

    public enum Color {
        RED("\033[91m"),    // Indicates error
        GREEN("\033[92m");  // Indicates success

        private final String value;

        Color(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * Returns the appropriate error message based on
     * the given input string.
     *
     * @param input     Input string to be validated
     * @see Util#getInput(String)
     */
    public static String generateInputErrorMessage(String input) {
        // Made public for testing
        if (input.isEmpty()) {
            return "Input cannot be empty";
        } else if (input.isBlank()) {
            return "Input cannot be blank";
        } else {
            return "";
        }
    }

    /**
     * Repeatedly asks the user for an input until
     * a non-blank/non-empty one is given.
     *
     * @param prompt Prompt to be displayed to the user.
     * @see Util#generateInputErrorMessage(String)
     */
    public static String getInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;
        String errorMessage;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            errorMessage = generateInputErrorMessage(input);

            if (errorMessage.isEmpty()) {
                scanner.close();
                return input;
            }
            print(Color.RED, errorMessage);
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
        System.out.println(color.getValue() + message + "\033[0m");
    }


}
