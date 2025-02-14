// Asset.java
public class Asset {
    private String symbol;
    private double currentValue;
    private double quantity;

    public Asset(String symbol, double currentValue, double quantity) {
        this.symbol = symbol;
        this.currentValue = currentValue;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getQuantity() {
        return quantity;
    }

    public void updateValue(double newValue) {
        this.currentValue = newValue;
    }

    @Override
    public String toString() {
        return "Asset[Symbol: " + symbol + ", Current Value: " + currentValue + ", Quantity: " + quantity + "]";
    }
}

// Portfolio.java
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private String owner;
    private List<Asset> assets;

    public Portfolio(String owner) {
        this.owner = owner;
        this.assets = new ArrayList<>();
    }

    public String getOwner() {
        return owner;
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public double calculateTotalValue() {
        double totalValue = 0;
        for (Asset asset : assets) {
            totalValue += asset.getCurrentValue() * asset.getQuantity();
        }
        return totalValue;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    @Override
    public String toString() {
        return "Portfolio[Owner: " + owner + ", Total Value: " + calculateTotalValue() + "]";
    }
}

// RiskAnalyzer.java
public class RiskAnalyzer {

    public double calculateRisk(Portfolio portfolio) {
        double riskScore = 0;
        for (Asset asset : portfolio.getAssets()) {
            double volatility = Math.random(); // Simulated volatility metric
            riskScore += volatility * asset.getCurrentValue() * asset.getQuantity();
        }
        return riskScore;
    }
}

// PortfolioOptimizer.java
public class PortfolioOptimizer {

    public void optimizePortfolio(Portfolio portfolio) {
        // Simple optimization by rebalancing asset holdings
        double totalValue = portfolio.calculateTotalValue();
        for (Asset asset : portfolio.getAssets()) {
            double proportion = (asset.getCurrentValue() * asset.getQuantity()) / totalValue;
            if (proportion > 0.3) { // Example threshold for rebalancing
                asset.updateValue(asset.getCurrentValue() * 0.95); // Reduce value by 5%
            }
        }
    }
}

// FinancialManagementApp.java
public class FinancialManagementApp {
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio("John Doe");
        portfolio.addAsset(new Asset("AAPL", 150.0, 10));
        portfolio.addAsset(new Asset("GOOG", 2800.0, 5));
        portfolio.addAsset(new Asset("TSLA", 720.0, 8));
        portfolio.addAsset(new Asset("AMZN", 3400.0, 2));

        System.out.println("Initial Portfolio:");
        System.out.println(portfolio);

        RiskAnalyzer riskAnalyzer = new RiskAnalyzer();
        double riskScore = riskAnalyzer.calculateRisk(portfolio);
        System.out.println("Portfolio Risk Score: " + riskScore);

        PortfolioOptimizer optimizer = new PortfolioOptimizer();
        optimizer.optimizePortfolio(portfolio);

        System.out.println("Optimized Portfolio:");
        System.out.println(portfolio);
    }
}