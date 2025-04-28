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
        System.out.print("""
                           Welcome to Crestwood Financial\n
                           Type D: to add a deposit\n
                           Type P: to make a payment\n
                           Type L: to display the ledger screen\n
                           Type X: to exit\n
                           Your Input: """);
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();

    }

}
