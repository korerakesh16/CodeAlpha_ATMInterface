package atm;
import java.util.*;

class Account {
    private String accountNumber;
    private int pin;
    private double balance;
    private List<String> transactions;

    public Account(String accountNumber, int pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        transactions.add("Account created with balance: " + initialBalance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean authenticate(int enteredPin) {
        return this.pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: " + amount + " | Balance: " + balance);
            System.out.println("Deposit successful. Balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrawn: " + amount + " | Balance: " + balance);
            System.out.println("Withdrawal successful. Balance: " + balance);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public void transfer(Account target, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            target.balance += amount;
            transactions.add("Transferred: " + amount + " to " + target.accountNumber + " | Balance: " + balance);
            target.transactions.add("Received: " + amount + " from " + this.accountNumber + " | Balance: " + target.balance);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Check balance or amount.");
        }
    }

    public void changePin(int newPin) {
        this.pin = newPin;
        transactions.add("PIN changed successfully.");
        System.out.println("PIN changed successfully.");
    }

    public void printMiniStatement() {
        System.out.println("\nMini Statement for Account " + accountNumber);
        for (String t : transactions) {
            System.out.println(t);
        }
    }
}

public class ATMInterface {
    private static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        accounts.put("1001", new Account("1001", 1234, 10000));
        accounts.put("1002", new Account("1002", 1111, 5000));
        accounts.put("1003", new Account("1003", 2222, 2000));

        System.out.print("Enter account number: ");
        String accNumber = scanner.nextLine();

        Account currentAccount = accounts.get(accNumber);

        if (currentAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter PIN: ");
        int enteredPin = scanner.nextInt();

        if (!currentAccount.authenticate(enteredPin)) {
            System.out.println("Incorrect PIN. Access Denied.");
            return;
        }

        int choice;
        do {
            System.out.println("\nATM Menu");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Mini Statement");
            System.out.println("6. Change PIN");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: " + currentAccount.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    currentAccount.deposit(scanner.nextDouble());
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    currentAccount.withdraw(scanner.nextDouble());
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.print("Enter target account number: ");
                    String targetAcc = scanner.nextLine();
                    Account target = accounts.get(targetAcc);
                    if (target != null) {
                        System.out.print("Enter amount: ");
                        double amt = scanner.nextDouble();
                        currentAccount.transfer(target, amt);
                    } else {
                        System.out.println("Target account not found.");
                    }
                    break;
                case 5:
                    currentAccount.printMiniStatement();
                    break;
                case 6:
                    System.out.print("Enter new PIN: ");
                    currentAccount.changePin(scanner.nextInt());
                    break;
                case 7:
                    System.out.println("Thank you for using ATM.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 7);

        scanner.close();
    }
}


