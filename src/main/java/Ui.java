import java.util.Scanner;

public class Ui {
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Robert");
        System.out.println(" What can I do for you?");
    }

    public void showLoadingError() {
        System.out.println("OOPS!!! There was an error loading your tasks.");
    }

    public void showError(String msg) {
        System.out.println(msg);
    }
}
