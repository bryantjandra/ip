import java.util.Scanner;

public class Robert {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        Scanner sc = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Hello! I'm Robert");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            String userCommand = sc.nextLine();

            if (userCommand.equals("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;

            } else if (userCommand.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(line);

            } else {
                tasks[taskCount] = userCommand;
                taskCount++;

                System.out.println(line);
                System.out.println(" added: " + userCommand);
                System.out.println(line);
            }
        }
        sc.close();
    }
}
