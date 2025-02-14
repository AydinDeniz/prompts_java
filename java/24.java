import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class StockMarket {
    private List<Stock> stocks;

    public StockMarket() {
        stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void simulateMarket() {
        Random random = new Random();
        for (Stock stock : stocks) {
            double changePercent = (random.nextDouble() - 0.5) * 0.1; // random change between -5% to +5%
            stock.setPrice(stock.getPrice() * (1 + changePercent));
        }
    }

    public void printMarketStatus() {
        for (Stock stock : stocks) {
            System.out.printf("Stock: %s, Price: %.2f\n", stock.getSymbol(), stock.getPrice());
        }
    }
}

class HistoricalDataAnalyzer {
    private List<Double> priceHistory;

    public HistoricalDataAnalyzer() {
        priceHistory = new ArrayList<>();
    }

    public void addPriceRecord(double price) {
        priceHistory.add(price);
    }

    public double calculateAveragePrice() {
        double sum = 0;
        for (double price : priceHistory) {
            sum += price;
        }
        return sum / priceHistory.size();
    }
}

public class StockSimulator {
    public static void main(String[] args) {
        StockMarket market = new StockMarket();

        Stock stockA = new Stock("AAPL", 150.00);
        Stock stockB = new Stock("GOOGL", 2800.00);

        market.addStock(stockA);
        market.addStock(stockB);

        HistoricalDataAnalyzer analyzer = new HistoricalDataAnalyzer();
        
        // Simulate for 10 days
        for (int day = 0; day < 10; day++) {
            market.simulateMarket();
            market.printMarketStatus();
            analyzer.addPriceRecord(stockA.getPrice());
        }

        System.out.printf("Average price of AAPL over 10 days: %.2f\n", analyzer.calculateAveragePrice());
    }
}