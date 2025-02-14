// User.java
public class User {
    private String userId;
    private String name;
    private String email;
    
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User[ID: " + userId + ", Name: " + name + ", Email: " + email + "]";
    }
}

// Product.java
public class Product {
    private String productId;
    private String name;
    private double price;
    private int quantityAvailable;
    
    public Product(String productId, String name, double price, int quantityAvailable) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void reduceQuantity(int quantity) {
        this.quantityAvailable -= quantity;
    }

    @Override
    public String toString() {
        return "Product[ID: " + productId + ", Name: " + name + ", Price: " + price + ", Available: " + quantityAvailable + "]";
    }
}

// UserService.java
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users = new HashMap<>();

    public User registerUser(String userId, String name, String email) {
        User user = new User(userId, name, email);
        users.put(userId, user);
        return user;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }
}

// ProductCatalogService.java
import java.util.HashMap;
import java.util.Map;

public class ProductCatalogService {
    private Map<String, Product> productCatalog = new HashMap<>();

    public Product addProduct(String productId, String name, double price, int quantityAvailable) {
        Product product = new Product(productId, name, price, quantityAvailable);
        productCatalog.put(productId, product);
        return product;
    }

    public Product getProduct(String productId) {
        return productCatalog.get(productId);
    }
}

// Order.java
public class Order {
    private String orderId;
    private User user;
    private Product product;
    private int quantity;
    private double totalPrice;

    public Order(String orderId, User user, Product product, int quantity) {
        this.orderId = orderId;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Order[ID: " + orderId + ", User: " + user.getName() + ", Product: " + product.getName() + ", Quantity: " + quantity + ", Total Price: " + totalPrice + "]";
    }
}

// OrderProcessingService.java
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderProcessingService {
    private Map<String, Order> orders = new HashMap<>();

    public Order placeOrder(User user, Product product, int quantity) {
        if (product.getQuantityAvailable() >= quantity) {
            product.reduceQuantity(quantity);
            String orderId = UUID.randomUUID().toString();
            Order order = new Order(orderId, user, product, quantity);
            orders.put(orderId, order);
            return order;
        } else {
            throw new IllegalStateException("Insufficient product quantity available");
        }
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }
}

// PaymentService.java
public class PaymentService {
    public boolean processPayment(User user, double amount) {
        // Simulate payment processing
        System.out.println("Processing payment for " + user.getName() + ": $" + amount);
        return true; // assume successful payment
    }
}

// EcommercePlatformApp.java
public class EcommercePlatformApp {
    public static void main(String[] args) {
        UserService userService = new UserService();
        ProductCatalogService productCatalogService = new ProductCatalogService();
        OrderProcessingService orderProcessingService = new OrderProcessingService();
        PaymentService paymentService = new PaymentService();

        // Register Users
        User user1 = userService.registerUser("user1", "Alice Smith", "alice@example.com");
        User user2 = userService.registerUser("user2", "Bob Jones", "bob@example.com");

        // Add Products to Catalog
        Product product1 = productCatalogService.addProduct("prod1", "Laptop", 999.99, 10);
        Product product2 = productCatalogService.addProduct("prod2", "Smartphone", 499.99, 20);

        // Place an Order
        try {
            Order order1 = orderProcessingService.placeOrder(user1, product1, 2);
            System.out.println("Order placed: " + order1);
            paymentService.processPayment(user1, order1.getTotalPrice());

            Order order2 = orderProcessingService.placeOrder(user2, product2, 1);
            System.out.println("Order placed: " + order2);
            paymentService.processPayment(user2, order2.getTotalPrice());
        } catch (IllegalStateException e) {
            System.err.println("Failed to place order: " + e.getMessage());
        }
    }
}