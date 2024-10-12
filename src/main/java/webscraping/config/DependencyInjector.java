package webscraping.config;

import webscraping.database.DBConnectionManager;
import webscraping.repositories.AuthorDAO;
import webscraping.repositories.QuoteDAO;
import webscraping.repositories.TagDAO;
import webscraping.services.AuthorService;
import webscraping.services.QuoteService;
import webscraping.services.ScraperMainService;
import webscraping.services.TagService;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
    private static final DependencyInjector instance = new DependencyInjector();

    private final Map<Class<?>, Object> dependencies;

    private DependencyInjector() {
        dependencies = new HashMap<>();

        registerDefaultClasses();
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

    private void registerDefaultClasses() {
        register(DBConnectionManager.class, new DBConnectionManager());
        register(AuthorDAO.class, new AuthorDAO());
        register(QuoteDAO.class, new QuoteDAO());
        register(TagDAO.class, new TagDAO());

        register(AuthorService.class, new AuthorService());
        register(QuoteService.class, new QuoteService());
        register(TagService.class, new TagService());

        register(ScraperMainService.class, new ScraperMainService());
    }
}
