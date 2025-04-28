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
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;


public class HomeScreen {

    public static void main(String[] args) {

        /**
         * Example User:
         * Name: Jerry Hendricks
         * Checking Account Number: 11087624
         * Savings Account Number: 87610291
         */
        String[] exampleUser = {"Jerry Hendricks","11087624", "87610291"};

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
                    addDeposit(in,exampleUser);
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
    public static void addDeposit(Scanner in,String[] exampleUser){
        try {

            System.out.printf("Enter the account number for the deposit: ");
            int accountNumber = in.nextInt();
            System.out.printf("Enter the amount you want to deposit: ");
            double deposit = in.nextDouble();
            System.out.printf("Does this look correct $%.2f (Y/N):", deposit);
            String confirm = in.next();
            System.out.println("Deposit Complete. Taking you back to the Main Menu");
            File file = new File(String.format("%s-%d-Deposit",exampleUser[0],Integer.parseInt(exampleUser[1]),deposit));
            FileWriter writer = new FileWriter(file,true);
            LocalDate date = LocalDate.now();
            writer.write(String.format("Date:%s | Transaction Type: Deposit | Amount: $%.2f\n",date,deposit));
            writer.close();
            in.nextLine();
        }
        catch (Exception e){

        }
    }
}
