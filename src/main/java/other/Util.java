package other;

import java.util.Scanner;

@SuppressWarnings("unused")
public class Util {

    private static final String[] countyAbbreviations = {
            "C", "CE", "CN", "CW", "D", "DL", "G", "KE",
            "KK", "KY", "L", "LD", "LH", "LM", "LS", "MH",
            "MN", "MO", "OY", "RN", "SO", "T", "W", "WH",
            "WX", "WW"
    };

    public enum Color {
        RED("\033[91m"),    // Indicates error
        GREEN("\033[92m"),  // Indicates success
        YELLOW("\033[93m"),
        BLUE("\033[94m"),
        MAGENTA("\033[95m"),
        CYAN("\033[96m"),
        NONE("\033[0;0m");


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
     * Prints a colored message to the console.
     * Mainly intended for error/success messages.
     *
     * @param color     Color to be used in output.
     * @param message   Message to be displayed.
     * @see Color
     */
    public static void print(Color color, String message) {
        System.out.println(color.getValue() + message + Color.NONE.getValue());
    }

    /**
     * Generates a random integer between a specific range
     * where both min and max are inclusive in said range.
     *
     * @param min Minimum value
     * @param max Maximum value
     */
    public static int randInt(int min, int max) {
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }

    /**
     * Generates a random Irish car registration number
     * between the years of 2000 to 2023.
     *
     * @return Registration number.
     */
    public static String randomRegistrationString() {
        StringBuilder registrationString = new StringBuilder();

        int year = randInt(0, 23);

        if (year > 12) {
            year = year*10 + randInt(1, 2);
        }

        int randomIndex = randInt(0, 26);
        String county = countyAbbreviations[randomIndex];

        StringBuilder sequentialNumber = new StringBuilder();
        int iterations = randInt(3, 6);
        for (int i = 0; i < iterations; i++) {
            sequentialNumber.append(randInt(0, 9));
        }

        return registrationString
                .append(year)
                .append('-')
                .append(county)
                .append('-')
                .append(sequentialNumber)
                .toString();
    }

    /**
     * Pauses program execution for the given number of seconds.
     *
     * @param secs Seconds to sleep for.
     */
    public static void sleep(double secs) {
        try {
            Thread.sleep((int) Math.round(secs * 1000));
        } catch (InterruptedException e) {
            // Don't sleep
        }
    }

}
