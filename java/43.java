// Stock.java
public class Stock {
    private String symbol;
    private double price;
    private int quantityAvailable;

    public Stock(String symbol, double price, int quantityAvailable) {
        this.symbol = symbol;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    public void updateQuantityAvailable(int change) {
        this.quantityAvailable += change;
    }

    @Override
    public String toString() {
        return "Stock[" + symbol + ", Price: " + price + ", Available: " + quantityAvailable + "]";
    }
}

// Player.java
import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private double balance;
    private Map<String, Integer> portfolio;

    public Player(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost <= balance && quantity <= stock.getQuantityAvailable()) {
            balance -= cost;
            stock.updateQuantityAvailable(-quantity);
            portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println(name + " bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Transaction failed. Check balance or stock availability.");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        if (portfolio.getOrDefault(stock.getSymbol(), 0) >= quantity) {
            double revenue = stock.getPrice() * quantity;
            balance += revenue;
            stock.updateQuantityAvailable(quantity);
            portfolio.put(stock.getSymbol(), portfolio.get(stock.getSymbol()) - quantity);
            System.out.println(name + " sold " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Transaction failed. Check stock holdings.");
        }
    }

    public void displayPortfolio() {
        System.out.println(name + "'s Portfolio:");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " shares");
        }
    }
}

// StockMarketSimulator.java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StockMarketSimulator {
    private List<Stock> stocks;
    private Random random;

    public StockMarketSimulator() {
        this.stocks = new ArrayList<>();
        this.random = new Random();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void updateMarket() {
        for (Stock stock : stocks) {
            double changePercent = (random.nextDouble() - 0.5) * 0.1; // Random change between -5% and +5%
            double newPrice = stock.getPrice() * (1 + changePercent);
            stock.updatePrice(newPrice);
            System.out.println("Updated " + stock);
        }
    }

    public Stock findStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }
}

// MarketSimulationApp.java
public class MarketSimulationApp {
    public static void main(String[] args) {
        StockMarketSimulator simulator = new StockMarketSimulator();

        Stock apple = new Stock("AAPL", 150.0, 100);
        Stock google = new Stock("GOOG", 2800.0, 50);

        simulator.addStock(apple);
        simulator.addStock(google);

        Player player = new Player("Alice", 10000.0);

        player.buyStock(apple, 10);
        player.sellStock(apple, 5);
        player.displayPortfolio();

        simulator.updateMarket();

        player.buyStock(google, 2);
        player.displayPortfolio();
    }
}