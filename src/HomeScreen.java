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
                           Thank you for choosing Crestwood Financial
                           Type D: to add a deposit
                           Type P: to make a payment
                           Type L: to display the ledger screen
                           Type X: to exit
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
            in.nextLine();
            //who are you buying it from
            System.out.printf("What is the sellers name: ");
            String sellerName = in.nextLine();
            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write(String.format("%s|%s|%s|%.2f\n",getFormattedDate(),item,sellerName,price));
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
                              * 6 - Custom Search
                              * 0 - Back
                              * H - Go back to home page
                          Your Input: """);
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
                                6 - Custom Search
                                0 - Back
                                Your Input: """);
                        choice = in.nextLine().toLowerCase();
                        switch (choice){
                            case "1":
                                System.out.println("Month to Date");
                                reports("mtd", in);
                                break;
                            case "2":
                                System.out.println("Previous Month");
                                reports("pm", in);
                                break;
                            case "3":
                                System.out.println("Year to Date");
                                reports("yd", in);
                                break;
                            case "4":
                                System.out.println("Previous Year");
                                reports("py", in);
                                break;
                            case "5":
                                System.out.printf("Search by vendor: ");
                                Scanner sc = new Scanner(System.in);
                                reports(sc.nextLine(),in);
                                break;
                            case "6":
                                System.out.println("Custom Search");
                                customSearch(in);
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
                              * 6 - Custom Search
                              * 0 - Back
                              * H - Go back to home page
                          Your Input: """);
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
    public static void reports(String report, Scanner in){
        LocalDate current = LocalDate.now();
        ArrayList<String> transactions = getTranscations();
        for(String transaction : transactions) {
            String[] parts = parsedTranscations(transaction);
            String[] strip = parts[0].split("\\|");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(strip[0]);
            if (report.equals("mtd")&& date.getYear() == current.getYear() && date.getMonth() == current.getMonth() && date.getDayOfMonth() <= current.getDayOfMonth()) {
                System.out.println(transaction);
            }
            else if(report.equals("pm") && date.getMonthValue() == current.getMonthValue() - 1 && date.getYear() == current.getYear()){
                //previous month
                System.out.println(transaction);
            }
            else if(report.equals("yd") && date.getYear() == current.getYear()){
                //year to date
                System.out.println(transaction);
            }
            else if(report.equals("py") && date.getYear() == current.getYear() - 1){
                //previous year

                System.out.println(transaction);
            }
            else {
                //search
                if(parts[3].toLowerCase().contains(report.toLowerCase())){
                    System.out.println(transaction);
                }

            }

        }

    }
    public static void customSearch(Scanner in){
        /**
         *o Start Date
         * o End Date
         * o Description
         * o Vendor
         * o Amount
         */
        System.out.printf("Enter the start date(yyyy-mm-dd) or press Enter: ");
        String start = in.nextLine().toLowerCase();
        LocalDate startDate = evaluateDate(start);
        System.out.println(start);
        System.out.printf("Enter the end date(yyyy-mm-dd) or press Enter:");
        String end = in.nextLine().toLowerCase();
        LocalDate endDate = evaluateDate(end);
        System.out.printf("Enter the description of the transcation or press Enter: ");
        String description = in.nextLine().toLowerCase().trim();
        System.out.printf("Enter the vendor or press Enter: ");
        String vendor = in.nextLine().toLowerCase().trim();
        System.out.printf("Enter the price or press Enter: ");
        String price = in.nextLine().toLowerCase();
        for(String transaction: getTranscations()){
            String[] parts = transaction.split("\\|");
            String[] stripDate = parts[0].split("\\|");
            LocalDate current = LocalDate.parse(stripDate[0]);
            //individual transaction evaluate if it meets requiremnts
            //System.out.println(Arrays.toString(parts));
            if(evaluateDates(startDate,current,endDate) && parts[2].toLowerCase().contains(description) && parts[3].toLowerCase().contains(vendor) && parts[4].contains(price)){
                System.out.println(transaction);
            }
        }
    }
    public static LocalDate evaluateDate(String s){
        return s == "" ? null: LocalDate.parse(s);
    }
    public static Boolean evaluateDates(LocalDate min, LocalDate current, LocalDate max){
        if(min == null && max == null){
            return true;
        }
        else if(min != null && max == null){
            if(current.isEqual(min) || current.isAfter(min)){
                return true;
            }
            return false;
        }
        else if(min == null && max != null){
            if(current.isBefore(max) || current.isEqual(max)){
                return true;
            }
            return false;
        }
        else {
            if (current.isEqual(min) || current.isAfter(min) && current.isBefore(max) || current.isEqual(max)) {
                return true;
            }
            return false;
        }
    }


}
