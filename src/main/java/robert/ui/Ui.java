package robert.ui;

import java.util.Scanner;

/**
 * Handles user interactions such as reading commands and showing output.
 */
public class Ui {
    private final Scanner sc;

    /**
     * Creates an Ui object for interacting with the user.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads the next command from the user.
     *
     * @return A trimmed string representing the user's command.
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Prints a divider line to the console.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Shows the welcome message to the user.
     */
    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Robert");
        System.out.println(" What can I do for you?");
    }

    /**
     * Displays an error message when loading tasks fails.
     */
    public void showLoadingError() {
        System.out.println("OOPS!!! There was an error loading your tasks.");
    }

    /**
     * Shows a given message to the user.
     *
     * @param msg The message to be printed.
     */
    public void showError(String msg) {
        System.out.println(msg);
    }
}
