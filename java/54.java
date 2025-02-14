// Stock.java
public class Stock {
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

    @Override
    public String toString() {
        return "Stock[Symbol: " + symbol + ", Price: " + price + "]";
    }
}

// StockMarketDataProvider.java
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StockMarketDataProvider {
    private Map<String, Stock> stockData;
    private Random random;

    public StockMarketDataProvider() {
        this.stockData = new HashMap<>();
        this.random = new Random();
        initializeSampleData();
    }

    private void initializeSampleData() {
        stockData.put("AAPL", new Stock("AAPL", 150.0));
        stockData.put("GOOG", new Stock("GOOG", 2800.0));
        stockData.put("AMZN", new Stock("AMZN", 3400.0));
        stockData.put("TSLA", new Stock("TSLA", 720.0));
    }

    public Stock getStockInfo(String symbol) {
        return stockData.get(symbol);
    }

    public void updateStockPrices() {
        for (Stock stock : stockData.values()) {
            double priceChange = -5 + (10) * random.nextDouble();
            stock.setPrice(stock.getPrice() + priceChange);
        }
    }
}

// TradingStrategy.java
public interface TradingStrategy {
    String execute(Stock stock);
}

// SimpleMovingAverageStrategy.java
import java.util.LinkedList;
import java.util.Queue;

public class SimpleMovingAverageStrategy implements TradingStrategy {
    private static final int PERIOD = 5;
    private Queue<Double> priceWindow;
    private double total;

    public SimpleMovingAverageStrategy() {
        this.priceWindow = new LinkedList<>();
        this.total = 0;
    }

    @Override
    public String execute(Stock stock) {
        double price = stock.getPrice();
        if (priceWindow.size() >= PERIOD) {
            total -= priceWindow.poll();
        }
        priceWindow.add(price);
        total += price;

        if (priceWindow.size() == PERIOD) {
            double averagePrice = total / PERIOD;
            if (price > averagePrice) {
                return "SELL";
            } else if (price < averagePrice) {
                return "BUY";
            }
        }
        return "HOLD";
    }
}

// TradingBot.java
public class TradingBot {
    private StockMarketDataProvider dataProvider;
    private TradingStrategy strategy;

    public TradingBot(StockMarketDataProvider dataProvider, TradingStrategy strategy) {
        this.dataProvider = dataProvider;
        this.strategy = strategy;
    }

    public void analyzeAndTrade(String stockSymbol) {
        Stock stock = dataProvider.getStockInfo(stockSymbol);
        String action = strategy.execute(stock);
        System.out.println("Trading decision for " + stockSymbol + ": " + action);
    }
}

// StockMarketSimulationApp.java
public class StockMarketSimulationApp {
    public static void main(String[] args) {
        StockMarketDataProvider dataProvider = new StockMarketDataProvider();
        TradingStrategy strategy = new SimpleMovingAverageStrategy();
        TradingBot tradingBot = new TradingBot(dataProvider, strategy);

        String[] stockSymbols = {"AAPL", "GOOG", "AMZN", "TSLA"};

        for (int i = 0; i < 10; i++) {
            System.out.println("Market Update Cycle: " + (i + 1));
            dataProvider.updateStockPrices();
            
            for (String symbol : stockSymbols) {
                Stock stock = dataProvider.getStockInfo(symbol);
                System.out.println(stock);
                tradingBot.analyzeAndTrade(symbol);
            }
            System.out.println();
        }
    }
}