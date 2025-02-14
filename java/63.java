// Transaction.java
import java.time.LocalDate;

public class Transaction {
    private String description;
    private double amount;
    private LocalDate date;
    private String category;

    public Transaction(String description, double amount, LocalDate date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Transaction[Desc: " + description + ", Amount: " + amount + ", Date: " + date + ", Category: " + category + "]";
    }
}

// FinanceAnalyzer.java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FinanceAnalyzer {

    public double calculateTotalExpenditure(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public Map<String, Double> calculateCategoryWiseExpenditure(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)));
    }

    public double calculateSavings(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}

// FinanceAdvisor.java
import java.util.List;
import java.util.Map;

public class FinanceAdvisor {

    public void provideAdvice(List<Transaction> transactions) {
        FinanceAnalyzer analyzer = new FinanceAnalyzer();

        double totalExpenditure = analyzer.calculateTotalExpenditure(transactions);
        double savings = analyzer.calculateSavings(transactions);
        Map<String, Double> categoryWiseExpenditure = analyzer.calculateCategoryWiseExpenditure(transactions);

        System.out.println("Total Expenditure: " + totalExpenditure);
        System.out.println("Total Savings: " + savings);

        System.out.println("Expenditure by Category:");
        categoryWiseExpenditure.forEach((category, amount) -> {
            System.out.println("Category: " + category + ", Amount: " + amount);
        });

        if (savings + totalExpenditure < 0) {
            System.out.println("Advice: Your expenditure exceeds your income. Consider reducing expenses.");
        } else {
            System.out.println("Advice: You are saving well. Keep up the good work.");
        }
    }
}

// FinanceApp.java
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinanceApp {
    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Salary", 3000, LocalDate.of(2025, 1, 1), "Income"));
        transactions.add(new Transaction("Coffee", -4.5, LocalDate.of(2025, 1, 2), "Food"));
        transactions.add(new Transaction("Groceries", -150, LocalDate.of(2025, 1, 3), "Food"));
        transactions.add(new Transaction("Rent", -800, LocalDate.of(2025, 1, 4), "Housing"));
        transactions.add(new Transaction("Electricity Bill", -60, LocalDate.of(2025, 1, 5), "Utilities"));
        transactions.add(new Transaction("Freelance", 400, LocalDate.of(2025, 1, 6), "Income"));

        FinanceAdvisor advisor = new FinanceAdvisor();
        advisor.provideAdvice(transactions);
    }
}