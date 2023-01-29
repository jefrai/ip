package shigure;

import java.util.Scanner;

/**
 * A UI controller for Miki interactive command-line I/O.
 */
public class Ui {
    private boolean asciiOnly;
    private boolean autoDiv = true;

    private Scanner sc = new Scanner(System.in);

    private static final String DIV = "    ____________________________________________________________________________";

    /**
     * Creates a new UI, optionally using ASCII-only versions of built-in <code>Strings</code>.
     *
     * @param asciiOnly whether this Ui shall operate in ASCII-only mode
     */
    public Ui(boolean asciiOnly) {
        this.asciiOnly = asciiOnly;
    }

    /**
     * Prints an output prompt and reads a single line of input.
     *
     * @return the read line of user input
     */
    public String readLine() {
        System.out.print(">");
        return sc.nextLine();
    }

    /**
     * Prints a line divider to the user interface.
     */
    public void printDiv() {
        System.out.println(DIV);
    }

    /**
     * If automatic internal line dividers are enabled, prints a line divider.
     */
    public void printAutoDiv() {
        if (autoDiv) {
            printDiv();
        }
    }

    /**
     * Prints a line of output to the user interface,
     * formatted with indentation for clarity.
     *
     * @param s <code>String</code> to be printed
     */
    public void print(String s) {
        System.out.println("     " + s);
    }

    /**
     * Prints a welcome message to the user interface.
     */
    public void printIntro() {
        printAutoDiv();
        String username = System.getProperty("user.name");
        print("in honour / fuzuki miki / 2020 | 2021");
        if (!asciiOnly) {
            print("\uD83C\uDF80✨");
            print("Hello " + username + " !! Konmiki! ＼(￣▽￣)/");
        } else {
            print("01 f3 80 / 27 28");
            print("Hello " + username + " !! Konmiki! \\(^v^)/");
        }
        printAutoDiv();
    }

}
