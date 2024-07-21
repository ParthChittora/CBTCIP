import java.util.*;
import java.io.*;

class Account {
    private String accountNumber;
    private String ownerName;
    private double balance;

    public Account(String accountNumber, String ownerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
            return true;
        }
        return false;
    }
}

class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void createAccount(String accountNumber, String ownerName, double initialBalance) {
        accounts.put(accountNumber, new Account(accountNumber, ownerName, initialBalance));
        System.out.println("Account created for " + ownerName);
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            if (!account.withdraw(amount)) {
                System.out.println("Insufficient funds");
            }
        } else {
            System.out.println("Account not found");
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            if (fromAccount.withdraw(amount)) {
                toAccount.deposit(amount);
                System.out.println("Transfer successful");
            } else {
                System.out.println("Transfer failed: Insufficient funds");
            }
        } else {
            System.out.println("One or both accounts not found");
        }
    }

    public void saveAccountsToFile(String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (Account account : accounts.values()) {
                out.println(account.getAccountNumber() + "," + account.getOwnerName() + "," + account.getBalance());
            }
            System.out.println("Accounts saved to file");
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public void loadAccountsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    createAccount(parts[0], parts[1], Double.parseDouble(parts[2]));
                }
            }
            System.out.println("Accounts loaded from file");
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }
}

public class BankY {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Save Accounts\n6. Load Accounts\n7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accNum = scanner.nextLine();
                    System.out.print("Enter owner name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();
                    bank.createAccount(accNum, name, balance);
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accNum = scanner.nextLine();
                    System.out.print("Enter deposit amount: ");
                    double amount = scanner.nextDouble();
                    bank.deposit(accNum, amount);
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    accNum = scanner.nextLine();
                    System.out.print("Enter withdrawal amount: ");
                    amount = scanner.nextDouble();
                    bank.withdraw(accNum, amount);
                    break;
                case 4:
                    System.out.print("Enter from account number: ");
                    String fromAcc = scanner.nextLine();
                    System.out.print("Enter to account number: ");
                    String toAcc = scanner.nextLine();
                    System.out.print("Enter transfer amount: ");
                    amount = scanner.nextDouble();
                    bank.transfer(fromAcc, toAcc, amount);
                    break;
                case 5:
                    System.out.print("Enter filename to save: ");
                    String saveFile = scanner.nextLine();
                    bank.saveAccountsToFile(saveFile);
                    break;
                case 6:
                    System.out.print("Enter filename to load: ");
                    String loadFile = scanner.nextLine();
                    bank.loadAccountsFromFile(loadFile);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
