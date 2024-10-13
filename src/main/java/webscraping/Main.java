package webscraping;

import webscraping.config.DependencyInjector;
import webscraping.services.ScraperMainService;

public class Main {
    public static void main(String[] args) {
        DependencyInjector injector = DependencyInjector.getInstance();
        injector.registerDefaultClasses();

        ScraperMainService scraper = injector.getInstanceOf(ScraperMainService.class);
    }
}