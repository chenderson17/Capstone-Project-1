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
import java.util.regex.Pattern;

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
                    ledgerScreen(in);
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
                writer.write(String.format("%s|Deposit|%.2f\n", getFormattedDate(), deposit));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public static String handleDeposit(Scanner in,double deposit){
        System.out.printf("Does this look correct %.2f (Y/N):", deposit);
        return in.nextLine();
    }
    public static void makePayment(Scanner in){
        //what are you buying
        try {
            System.out.printf("Enter the Transaction Description/Item name:");
            String item = in.nextLine();
            //price of what you are buying
            System.out.printf("Enter the price of the item: ");
            double price = in.nextDouble();
            //who are you buying it from
            System.out.printf("What is the sellers name: ");
            String sellerName = in.next();
            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write(String.format("%s|%s|%.2f\n",getFormattedDate(),item,price,sellerName));
            writer.close();
        }
        catch(Exception error){
            System.out.println(error);
        }
    }
    public static String getFormattedDate (){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        return date.format(format);
    }


    /**
     *A) All - Display all entries
     * o D) Deposits - Display only the entries that are deposits into the
     * account
     * o P) Payments - Display only the negative entries (or payments)
     * o R) Reports - A new screen that allows the user to run pre-defined
     * reports or to run a custom search
     * § 1) Month To Date
     * § 2) Previous Month
     * § 3) Year To Date
     * § 4) Previous Year
     * § 5) Search by Vendor - prompt the user for the vendor name
     * and display all entries for that vendor
     * § 0) Back - go back to the report page
     * o H) Home - go back to the home page
     */

    public static void ledgerScreen(Scanner in){
        System.out.printf("""
                          A - Display all entries
                          P - Display all payments
                          R - Display Reports
                              * 1 - Month to Date
                              * 2 - Previous Month
                              * 3 - Year to Date
                              * 4 - Previous Year
                              * 5 - Search by Vendor
                              * 0 - Back
                              * H - Go back to home page\n
                          Your Input: 
                          """);
        String choice = in.nextLine().toLowerCase();
        while(!choice.equals("h")){
            switch(choice) {
                case "a":
                    //display all entries
                    for (String transaction : getTranscations()) {
                        System.out.println(transaction);
                    }
                    break;
                case "p":
                    System.out.println("Payment history");
                    for (String transaction : getTranscations()) {
                        String[] parts = transaction.trim().split("\\|");
                        double value = Double.parseDouble(parts[parts.length - 1]);
                        if (value < 0) {
                            System.out.println(transaction);
                        }
                    }
                    break;
                case "r":
                    LocalDate current = LocalDate.now();
                    ArrayList<String> transactions = getTranscations();
                    while(!choice.equals("0")){
                        System.out.printf("""
                                Generate Reports
                                1 - Month to Date
                                2 - Previous Month
                                3 - Year to Date
                                4 - Previous Year
                                5 - Search by Vendor
                                0 - Back
                                """);
                        choice = in.nextLine().toLowerCase();
                        switch (choice){
                            case "1":
                                System.out.println("Month to Date");
                                for(String transaction: transactions){
                                    String[] parts = parsedTranscations(transaction);
                                    String[] strip = parts[0].split("\\|");
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    LocalDate date = LocalDate.parse(strip[0]);
                                    if(date.getYear() == current.getYear() && date.getMonth() == current.getMonth() && date.getDayOfMonth() <= current.getDayOfMonth()){
                                        System.out.println(transaction);
                                    }
                                }
                                break;
                            case "2":
                                System.out.println("Previous Month");
                                break;
                            case "3":
                                System.out.println("Year to Date");
                                break;
                            case "4":
                                System.out.println("Previous Year");
                                break;
                            case "5":
                                System.out.println("Search by vendor");
                                break;
                            default:
                                System.out.println("Sorry, I didn't recognize that command");
                                break;
                        }
                    }
                    break;
                default:
                    System.out.println("Sorry, I did not recognize that command");
                    break;

            }
            System.out.printf("""
                          A - Display all entries
                          P - Display all payments
                          R - Display Reports
                              * 1 - Month to Date
                              * 2 - Previous Month
                              * 3 - Year to Date
                              * 4 - Previous Year
                              * 5 - Search by Vendor
                              * 0 - Back
                              * H - Go back to home page\n
                          Your Input: 
                          """);
             choice = in.nextLine().toLowerCase();

        }
    }

    public static ArrayList<String> getTranscations(){
        ArrayList<String> transactions = new ArrayList<>();
        try {
            File file = new File("transactions.csv");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                transactions.add(reader.nextLine());
            }
        }
        catch (Exception error){
            System.out.println(error);
        }
        return transactions;

    }
    public static String[] parsedTranscations(String s){
        return s.split("\\|");
    }

}
