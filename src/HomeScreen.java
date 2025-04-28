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
        String menu = String.format("""
                           Welcome back to Crestwood Financial\n
                           Type D: to add a deposit\n
                           Type P: to make a payment\n
                           Type L: to display the ledger screen\n
                           Type X: to exit\n
                           Your Input: """);
        System.out.printf(menu);
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine().toLowerCase();
        //case manager
        while(!choice.equals("x")){
            switch (choice) {
                case "d":
                    System.out.println("add a deposit");
                    addDeposit(in);
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
    public static void addDeposit(Scanner in){
        System.out.printf("Enter the account number for the deposit: ");
        int accountNumber = in.nextInt();
        System.out.printf("Enter the amount you want to deposit: ");
        double deposit = in.nextDouble();
        System.out.printf("Does this look correct $%.2f (Y/N):", deposit);
        String confirm = in.next();
        System.out.println("Deposit Complete. Taking you back to the Main Menu");
        in.nextLine();
        //TODO: ADD A WRITE FUNCTION
    }
}
