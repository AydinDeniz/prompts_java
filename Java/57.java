// Article.java
public class Article {
    private String title;
    private String content;
    private String category;

    public Article(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Article[Title: " + title + ", Category: " + category + "]";
    }
}

// UserPreference.java
import java.util.HashMap;
import java.util.Map;

public class UserPreference {
    private Map<String, Integer> categoryPreferences;

    public UserPreference() {
        this.categoryPreferences = new HashMap<>();
    }

    public void likeCategory(String category) {
        categoryPreferences.put(category, categoryPreferences.getOrDefault(category, 0) + 1);
    }

    public void dislikeCategory(String category) {
        categoryPreferences.put(category, categoryPreferences.getOrDefault(category, 0) - 1);
    }

    public Map<String, Integer> getCategoryPreferences() {
        return categoryPreferences;
    }
}

// NewsAggregator.java
import java.util.*;

public class NewsAggregator {
    private List<Article> articles;

    public NewsAggregator() {
        this.articles = new ArrayList<>();
        loadSampleArticles();
    }

    private void loadSampleArticles() {
        articles.add(new Article("Economy Boosts in 2023", "The economy boosted greatly this year...", "Economy"));
        articles.add(new Article("Tech Giants Lead Innovations", "Leading innovations in tech are causing ripples...", "Technology"));
        articles.add(new Article("Political Stability Questioned", "Recent events challenge the status quo in politics...", "Politics"));
        articles.add(new Article("Health Benefits of a Balanced Diet", "Maintaining a balanced diet is crucial for health...", "Health"));
    }

    public List<Article> getArticlesByCategory(String category) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articles) {
            if (article.getCategory().equalsIgnoreCase(category)) {
                filteredArticles.add(article);
            }
        }
        return filteredArticles;
    }

    public List<Article> recommendArticles(UserPreference preferences) {
        List<Article> recommendedArticles = new ArrayList<>();
        Map<String, Integer> prefs = preferences.getCategoryPreferences();
        for (String category : prefs.keySet()) {
            if (prefs.get(category) > 0) {
                recommendedArticles.addAll(getArticlesByCategory(category));
            }
        }
        return recommendedArticles;
    }
}

// NewsAggregatorApp.java
import java.util.List;
import java.util.Scanner;

public class NewsAggregatorApp {
    public static void main(String[] args) {
        NewsAggregator aggregator = new NewsAggregator();
        UserPreference userPreference = new UserPreference();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nNews Aggregator System:");
            System.out.println("1. View Articles by Category");
            System.out.println("2. Like a Category");
            System.out.println("3. Dislike a Category");
            System.out.println("4. Get Recommended Articles");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    List<Article> articles = aggregator.getArticlesByCategory(category);
                    System.out.println("Articles in " + category + ": " + articles);
                    break;
                case 2:
                    System.out.print("Enter category to like: ");
                    String likeCategory = scanner.nextLine();
                    userPreference.likeCategory(likeCategory);
                    System.out.println("Liked category: " + likeCategory);
                    break;
                case 3:
                    System.out.print("Enter category to dislike: ");
                    String dislikeCategory = scanner.nextLine();
                    userPreference.dislikeCategory(dislikeCategory);
                    System.out.println("Disliked category: " + dislikeCategory);
                    break;
                case 4:
                    List<Article> recommended = aggregator.recommendArticles(userPreference);
                    System.out.println("Recommended Articles: " + recommended);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}