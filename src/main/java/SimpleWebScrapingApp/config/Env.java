package SimpleWebScrapingApp.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Env {

    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String DB_DRIVER;
    private static final String DB_SCHEMA;

    private static final String SCRAPING_URL;

    static {
        Yaml yaml = new Yaml();
        Map<String, Map<String, String>> config = null;

        try (InputStream applicationConfigs = yaml.getClass().getClassLoader().getResourceAsStream("application.yaml")) {
            config = yaml.load(applicationConfigs);

            System.out.println(config);
        } catch (Exception ignored) {
            System.exit(1);
        }

        DB_URL = config.get("db").get("url");
        DB_USER = config.get("db").get("user");
        DB_PASSWORD = config.get("db").get("password");
        DB_DRIVER = config.get("db").get("driver");
        DB_SCHEMA = config.get("db").get("schema");

        SCRAPING_URL = config.get("scraping").get("url");

    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

    public static String getDbDriver() {
        return DB_DRIVER;
    }

    public static String getDbSchema() {
        return DB_SCHEMA;
    }

    public static String getScrapingUrl() {
        return SCRAPING_URL;
    }
}
