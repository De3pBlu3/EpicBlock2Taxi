package misc;

public final class Introduction {

    public static final String APP_NAME = "Roinn";

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
        System.out.println("A taxi-hiring application.\n");
    }

    public static void showGuide() {
        System.out.println("""
                Follow the below instruction prompts to create your very
                own simulation in which you can observe customers and taxis
                interact with one another and navigate the map.
                """);
    }

}
