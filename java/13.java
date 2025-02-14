import java.util.ArrayList;
import java.util.List;

// Define a Plugin interface
interface Plugin {
    void execute();
}

// Sample Plugin Implementation 1
class HelloWorldPlugin implements Plugin {
    @Override
    public void execute() {
        System.out.println("Hello from HelloWorldPlugin!");
    }
}

// Sample Plugin Implementation 2
class GoodbyeWorldPlugin implements Plugin {
    @Override
    public void execute() {
        System.out.println("Goodbye from GoodbyeWorldPlugin!");
    }
}

// PluginManager to manage and execute plugins
class PluginManager {
    private List<Plugin> plugins;

    public PluginManager() {
        plugins = new ArrayList<>();
    }

    public void loadPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    public void executePlugins() {
        for (Plugin plugin : plugins) {
            plugin.execute();
        }
    }
}

// Main class to demonstrate the pluggable architecture
public class PluggableArchitectureDemo {
    public static void main(String[] args) {
        PluginManager manager = new PluginManager();

        // Dynamically load plugins (simulated here by manually instantiating them)
        manager.loadPlugin(new HelloWorldPlugin());
        manager.loadPlugin(new GoodbyeWorldPlugin());

        // Execute all loaded plugins
        manager.executePlugins();
    }
}