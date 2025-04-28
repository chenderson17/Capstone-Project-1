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
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class HomeScreen {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        String userInputPrompt = "Your Input: ";
        String menu = String.format("""
                           Thank you for choosing Crestwood Financial\n
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
                    makePayment(in);
                    break;
                case "l":
                    System.out.println("display ledger screen");
                    break;
                default:
                    System.out.println("Sorry, I didn't recognize that command. Please try again or Press X to exit.");
            }
            System.out.print(menu);
            choice = in.nextLine().toLowerCase();
        }
    }
    public static void addDeposit(Scanner in){
            try {
                /*
                System.out.printf("Enter the account number for the deposit or press e to exit: ");
                int accountNumber = in.nextInt();
                */
                System.out.printf("Enter the amount you want to deposit:$");
                double deposit = in.nextDouble();
                handleDeposit(in,deposit);
                while(!handleDeposit(in,deposit).equalsIgnoreCase("y")){
                    System.out.printf("Enter the amount you want to deposit:$ ");
                    deposit = in.nextDouble();
                    handleDeposit(in,deposit);
                }
                System.out.println("Deposit Complete. Taking you back to the Main Menu");
                FileWriter writer = new FileWriter("transactions.csv", true);
                String formattedDate = getFormattedDate();
                writer.write(String.format("%s|Deposit|$%.2f\n", getFormattedDate(), deposit));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public static String handleDeposit(Scanner in,double deposit){
        System.out.printf("Does this look correct $%.2f (Y/N):", deposit);
        return in.nextLine();
    }
    public static void makePayment(Scanner in){
        //what are you buying
        try {
            System.out.printf("Enter what you are purchasing/transaction description:");
            String item = in.nextLine();
            //price of what you are buying
            System.out.printf("Enter the price of the item:$ ");
            double price = in.nextDouble() * -1.0;
            //who are you buying it from
            System.out.printf("What is the sellers name: ");
            String sellerName = in.next();
            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write(String.format("%s|%s|$%.2f\n",getFormattedDate(),item,price,sellerName));
            writer.close();
        }
        catch(Exception error){
            System.out.println(error);
        }
    }
    public static String getFormattedDate (){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        String formattedDate = date.format(format);
        return formattedDate;
    }

}
