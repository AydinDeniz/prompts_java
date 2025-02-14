// Expense.java
import java.time.LocalDate;

public class Expense {
    private String description;
    private double amount;
    private LocalDate date;

    public Expense(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Expense[" + description + ", " + amount + ", " + date + "]";
    }
}

// ExpenseTracker.java
import java.util.ArrayList;
import java.util.List;

public class ExpenseTracker {
    private List<Expense> expenses;

    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
}

// BudgetAdvisor.java
public class BudgetAdvisor {
    private double income;
    private double savingsGoal;

    public BudgetAdvisor(double income, double savingsGoal) {
        this.income = income;
        this.savingsGoal = savingsGoal;
    }

    public void adviseBudget(double totalExpenses) {
        double budget = income - savingsGoal;
        if (totalExpenses > budget) {
            System.out.println("Warning: You have exceeded your budget!");
        } else {
            System.out.println("You are within the budget.");
        }
        System.out.println("Your budget: " + budget);
        System.out.println("Total expenses: " + totalExpenses);
    }
}

// InvestmentAdvisor.java
public class InvestmentAdvisor {
    public void suggestInvestments(double availableFunds) {
        System.out.println("Available funds for investment: " + availableFunds);
        if (availableFunds <= 1000) {
            System.out.println("Advice: Consider a high-yield savings account or micro-investing.");
        } else if (availableFunds <= 5000) {
            System.out.println("Advice: Look into mutual funds or ETFs.");
        } else {
            System.out.println("Advice: Explore stock market options or real estate investments.");
        }
    }
}

// FinanceAssistantApp.java
import java.time.LocalDate;

public class FinanceAssistantApp {
    public static void main(String[] args) {
        // Initialize components
        ExpenseTracker tracker = new ExpenseTracker();
        BudgetAdvisor budgetAdvisor = new BudgetAdvisor(5000, 1000); // Income, Savings goal
        InvestmentAdvisor investmentAdvisor = new InvestmentAdvisor();

        // Add some expenses
        tracker.addExpense(new Expense("Groceries", 300, LocalDate.now()));
        tracker.addExpense(new Expense("Rent", 1200, LocalDate.now()));
        tracker.addExpense(new Expense("Utilities", 150, LocalDate.now()));
        tracker.addExpense(new Expense("Transport", 100, LocalDate.now()));

        double totalExpenses = tracker.getTotalExpenses();
        
        // Budget analysis
        budgetAdvisor.adviseBudget(totalExpenses);

        // Investment suggestions
        double availableFunds = budgetAdvisor.savingsGoal + (budgetAdvisor.income - totalExpenses);
        investmentAdvisor.suggestInvestments(availableFunds);
    }
}