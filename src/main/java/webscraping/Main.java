package webscraping;

import webscraping.config.DependencyInjector;
import webscraping.database.DBConnectionManager;
import webscraping.services.ScraperMainService;

public class Main {
    public static void main(String[] args) {
        DependencyInjector injector = DependencyInjector.getInstance();
        ScraperMainService scraper = injector.getInstanceOf(ScraperMainService.class);
    }
}