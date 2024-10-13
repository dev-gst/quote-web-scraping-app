package webscraping.config;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
    private static final DependencyInjector instance = new DependencyInjector();

    private final Map<Class<?>, Object> dependencies;

    private DependencyInjector() {
        dependencies = new HashMap<>();
    }

    public static DependencyInjector getInstance() {
        return instance;
    }

    public <T> T getInstanceOf(Class<T> clazz) {
        Object dependency = dependencies.get(clazz);
        if (dependency == null) {
            throw new IllegalArgumentException("No dependency found for " + clazz.getName());
        }

       return clazz.cast(dependency);
    }

    public <T> void register (Class<T> clazz, T dependency) {
        dependencies.put(clazz, dependency);
    }
}
