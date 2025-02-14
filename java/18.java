import java.util.ArrayList;
import java.util.List;

class Product {
    private int id;
    private String name;
    private double price;
    private int stockCount;

    public Product(int id, String name, double price, int stockCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockCount = stockCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void reduceStock(int quantity) {
        this.stockCount -= quantity;
    }
}

class Order {
    private List<OrderItem> items;
    private double totalAmount;

    public Order() {
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public void addItem(Product product, int quantity) {
        if (quantity <= product.getStockCount()) {
            OrderItem item = new OrderItem(product, quantity);
            items.add(item);
            totalAmount += item.getTotalPrice();
            product.reduceStock(quantity);
            System.out.println("Added " + quantity + " of " + product.getName() + " to the order.");
        } else {
            System.out.println("Insufficient stock for " + product.getName());
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

class OrderItem {
    private Product product;
    private int quantity;
    private double totalPrice;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}

public class ECommercePlatform {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct(new Product(1, "Laptop", 1200.00, 10));
        inventory.addProduct(new Product(2, "Smartphone", 800.00, 20));
        inventory.addProduct(new Product(3, "Headphones", 150.00, 15));

        Order order = new Order();
        order.addItem(inventory.getProductById(1), 2);
        order.addItem(inventory.getProductById(3), 1);

        System.out.println("Total order amount: $" + order.getTotalAmount());
    }
}