import java.util.Scanner;

public class Robert {
    public static void main(String[] args) {
        String line = "____________________________________________________________";

        System.out.println(line);
        System.out.println(" Hello! I'm Robert");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String userCommand = sc.nextLine();

            if (userCommand.equals("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else {
                System.out.println(line);
                System.out.println(" " + userCommand);
                System.out.println(line);
            }
        }

        sc.close();
    }
}
