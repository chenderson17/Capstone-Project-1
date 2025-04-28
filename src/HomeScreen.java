/***
 * TODO:
 * The home screen should give the user the following options. The
 * application should continue to run until the user chooses to exit.
 * § D) Add Deposit - prompt user for the deposit information and
 * save it to the csv file
 * § P) Make Payment (Debit) - prompt user for the debit
 * information and save it to the csv file
 * § L) Ledger - display the ledger screen
 * § X) Exit - exit the application
 */
import java.util.*;


public class HomeScreen {

    public static void main(String[] args) {
        String userInputPrompt = "Your Input: ";
        System.out.print("""
                           Welcome to Crestwood Financial\n
                           Type D: to add a deposit\n
                           Type P: to make a payment\n
                           Type L: to display the ledger screen\n
                           Type X: to exit\n
                           Your Input: """);
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine().toLowerCase();
        //case manager
        while(!choice.equalsIgnoreCase("X")) {
            switch (choice) {
                case "d":
                    System.out.println("add a deposit");
                    break;
                case "p":
                    System.out.println("make a payment");
                    break;
                case "l":
                    System.out.println("display ledger screen");
                    break;
                default:
                    System.out.println("Sorry, I didn't recognize that. Please try again or Press X to exit.");
            }
            System.out.print(userInputPrompt);
            choice = in.nextLine();
        }
    }

}
