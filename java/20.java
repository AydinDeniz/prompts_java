import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Table {
    private String name;
    private List<Map<String, String>> rows;

    public Table(String name) {
        this.name = name;
        this.rows = new ArrayList<>();
    }

    public void insert(Map<String, String> row) {
        rows.add(row);
        System.out.println("Inserted into " + name + ": " + row);
    }

    public List<Map<String, String>> selectAll() {
        return rows;
    }

    public List<Map<String, String>> query(String key, String value) {
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> row : rows) {
            if (value.equals(row.get(key))) {
                result.add(new HashMap<>(row));
            }
        }
        return result;
    }
}

class Database {
    private Map<String, Table> tables;

    public Database() {
        this.tables = new HashMap<>();
    }

    public void createTable(String tableName) {
        tables.put(tableName, new Table(tableName));
        System.out.println("Created table: " + tableName);
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
}

public class InMemoryDatabaseEngine {
    public static void main(String[] args) {
        Database db = new Database();
        db.createTable("Users");

        Table users = db.getTable("Users");
        Map<String, String> user1 = new HashMap<>();
        user1.put("id", "1");
        user1.put("name", "Alice");
        user1.put("email", "alice@example.com");

        Map<String, String> user2 = new HashMap<>();
        user2.put("id", "2");
        user2.put("name", "Bob");
        user2.put("email", "bob@example.com");

        users.insert(user1);
        users.insert(user2);

        System.out.println("All Users:");
        for (Map<String, String> row : users.selectAll()) {
            System.out.println(row);
        }

        System.out.println("Query Users with name 'Alice':");
        for (Map<String, String> row : users.query("name", "Alice")) {
            System.out.println(row);
        }
    }
}