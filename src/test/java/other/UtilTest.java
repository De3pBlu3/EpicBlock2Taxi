package other;

import org.junit.Test;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static other.Util.generateInputErrorMessage;
import static other.Util.Color;
import static other.Util.print;

/**
 * A {@code TestPrintStream} object is used in tandem with
 * {@code System.setOut()} to temporarily switch the output of
 * {@code System.out.println()} used in the {@code Util.print()}
 * method (for testing purposes).
 */
class TestPrintStream extends PrintStream {
    public static String content;

    public TestPrintStream() {
        super(System.out, true);
    }

    @Override
    public void println(String string) {
        content = string;
    }

}

public class UtilTest {

    @Test
    public void generateInputErrorMessage_TestEmpty() {
        assertEquals(
                "Input cannot be empty",
                generateInputErrorMessage("")
        );
    }

    @Test
    public void generateInputErrorMessage_TestBlank() {
        assertEquals(
                "Input cannot be blank",
                generateInputErrorMessage(" ")
        );
    }

    @Test
    public void generateInputErrorMessage_TestIrregularString() {
        assertEquals(
                "",
                generateInputErrorMessage("ᖶᕼᓰS ᓰS ᗩᘉ ᓰᖇᖇᘿᘜᑘᒪᗩᖇ Sᖶᖇᓰᘉᘜ")
        );
    }

    @Test
    public void generateInputErrorMessage_TestRegularString() {
        assertEquals(
                "",
                generateInputErrorMessage("This is a regular string")
        );
    }

    @Test
    public void print_TestRedOutput() {
        System.setOut(new TestPrintStream());
        print(Color.RED, "This is an error message");

        assertEquals(
                 "\033[91mThis is an error message\033[0m",
                TestPrintStream.content
        );
    }

    @Test
    public void print_TestGreenOutput() {
        System.setOut(new TestPrintStream());
        print(Color.GREEN, "This is a success message");

        assertEquals(
                "\033[92mThis is a success message\033[0m",
                TestPrintStream.content
        );
    }

}