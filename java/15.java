import java.util.ArrayList;
import java.util.List;

class Trade {
    private String symbol;
    private int quantity;
    private double price;
    private String action; // "BUY" or "SELL"

    public Trade(String symbol, int quantity, double price, String action) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.action = action;
    }

    @Override
    public String toString() {
        return action + " " + quantity + " of " + symbol + " at $" + price;
    }
}

class Strategy {
    private List<Double> priceHistory;
    private double movingAverage;
    private int period;

    public Strategy(int period) {
        this.priceHistory = new ArrayList<>();
        this.period = period;
    }

    public void addPrice(double price) {
        priceHistory.add(price);
        if (priceHistory.size() > period) {
            priceHistory.remove(0);
        }
        updateMovingAverage();
    }

    private void updateMovingAverage() {
        if (priceHistory.size() == period) {
            double sum = 0.0;
            for (double price : priceHistory) {
                sum += price;
            }
            movingAverage = sum / period;
        }
    }

    public Trade generateTradeSignal(String symbol, double currentPrice) {
        if (currentPrice > movingAverage) {
            return new Trade(symbol, 100, currentPrice, "BUY");
        } else if (currentPrice < movingAverage) {
            return new Trade(symbol, 100, currentPrice, "SELL");
        }
        return null;
    }
}

public class TradingBot {
    public static void main(String[] args) {
        Strategy strategy = new Strategy(5);

        double[] prices = {100, 102, 101, 104, 98, 97, 105};
        String symbol = "AAPL";

        for (double price : prices) {
            strategy.addPrice(price);
            Trade trade = strategy.generateTradeSignal(symbol, price);
            if (trade != null) {
                System.out.println(trade);
            }
        }
    }
}