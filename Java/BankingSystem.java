
import java.util.concurrent.locks.ReentrantLock;

// Account class with concurrency control for banking operations
class BankAccount {
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            if (amount > 0) {
                balance += amount;
                System.out.println(Thread.currentThread().getName() + " deposited " + amount + ", Balance: " + balance);
            }
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ", Balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " attempted to withdraw " + amount + ", Insufficient funds.");
            }
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}

// Main class for testing the BankAccount with concurrent transactions
public class BankingSystem {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance

        // Create threads for depositing and withdrawing
        Thread t1 = new Thread(() -> {
            account.deposit(200);
            account.withdraw(150);
        }, "User1");

        Thread t2 = new Thread(() -> {
            account.deposit(300);
            account.withdraw(500);
        }, "User2");

        Thread t3 = new Thread(() -> {
            account.withdraw(700);
            account.deposit(400);
        }, "User3");

        // Start the threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to complete
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final balance
        System.out.println("Final Balance: " + account.getBalance());
    }
}
