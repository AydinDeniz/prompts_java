
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Annotations
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Entity {
    String tableName();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

// ORM Class
public class SimpleORM<T> {
    private final Connection connection;

    public SimpleORM(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void save(T entity) throws SQLException, IllegalAccessException {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Missing @Entity annotation");
        }
        Entity entityAnnotation = clazz.getAnnotation(Entity.class);
        String tableName = entityAnnotation.tableName();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columns.append(column.name()).append(",");
                values.append("'").append(field.get(entity)).append("',");
            }
        }

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName,
                columns.substring(0, columns.length() - 1),
                values.substring(0, values.length() - 1));
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        }
    }

    public List<T> findAll(Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Missing @Entity annotation");
        }
        Entity entityAnnotation = clazz.getAnnotation(Entity.class);
        String tableName = entityAnnotation.tableName();
        String query = "SELECT * FROM " + tableName;

        List<T> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                T entity = clazz.newInstance();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Column.class)) {
                        Column column = field.getAnnotation(Column.class);
                        field.set(entity, rs.getObject(column.name()));
                    }
                }
                result.add(entity);
            }
        }
        return result;
    }

    public void deleteById(Class<T> clazz, Object idValue) throws SQLException {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Missing @Entity annotation");
        }
        Entity entityAnnotation = clazz.getAnnotation(Entity.class);
        String tableName = entityAnnotation.tableName();
        String idColumn = null;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                Column column = field.getAnnotation(Column.class);
                idColumn = column.name();
                break;
            }
        }
        if (idColumn == null) {
            throw new IllegalArgumentException("No @Id field found");
        }
        String query = String.format("DELETE FROM %s WHERE %s = '%s'", tableName, idColumn, idValue);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        }
    }

    public void close() throws SQLException {
        connection.close();
    }

    public static void main(String[] args) throws Exception {
        // Example usage
        SimpleORM<User> orm = new SimpleORM<>("jdbc:h2:mem:testdb", "sa", "");
        
        try (Statement stmt = orm.connection.createStatement()) {
            stmt.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(100), age INT)");
        }
        
        User user = new User(1, "Alice", 25);
        orm.save(user);

        List<User> users = orm.findAll(User.class);
        users.forEach(u -> System.out.println("User: " + u.getName() + ", Age: " + u.getAge()));

        orm.deleteById(User.class, 1);
        orm.close();
    }
}

// Entity example
@Entity(tableName = "users")
class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public User() {}

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
