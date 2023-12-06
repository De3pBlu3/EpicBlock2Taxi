package other;

public final class Introduction {

    public static final String APP_NAME = "[insert name here]";

    public static void showHeader() {
        System.out.println(
                "Welcome to "
                + Util.Color.CYAN.getValue()
                + APP_NAME
                + Util.Color.NONE.getValue()
                + "!"
        );
    }

    public static void showDescription() {
        System.out.println("A taxi-hiring application.");
    }

}
