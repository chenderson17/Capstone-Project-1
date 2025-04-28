public class User {
    String firstName;
    String lastName;
    int checkingAccountNumber;
    double checkingAccountBalance;
    int savingsAccountNumber;
    double savingsAccountBalance;
    User(String firstName, String lastName, int checkingAccountNumber, double checkingAccountBalance, int savingsAccountNumber, double savingsAccountBalance){
        this.firstName = firstName;
        this.lastName = lastName;
        this.checkingAccountNumber = checkingAccountNumber;
        this.checkingAccountBalance = checkingAccountBalance;
        this.savingsAccountBalance = savingsAccountBalance;
        this.savingsAccountNumber = savingsAccountNumber;
    }

}
