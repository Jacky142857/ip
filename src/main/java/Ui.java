import java.util.Scanner;
public class Ui {
    Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String nextLine() {
        return scanner.nextLine();
    }


    public void greet() {
        System.out.println("Nice to see you again, What can I do for you?");
    }
}
