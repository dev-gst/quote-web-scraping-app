package webscraping;

import webscraping.config.DependencyInjector;
import webscraping.database.DBConnectionManager;
import webscraping.repositories.AuthorDAO;
import webscraping.repositories.QuoteDAO;
import webscraping.repositories.TagDAO;
import webscraping.repositories.TagQuoteDAO;
import webscraping.services.*;

public class Main {
    public static void main(String[] args) {
        DependencyInjector injector = DependencyInjector.getInstance();
        registerDefaultClasses(injector);

        ScraperMainService scraperMainService = injector.getInstanceOf(ScraperMainService.class);
        scraperMainService.scrape();
    }

    private static void registerDefaultClasses(DependencyInjector injector) {
        DBConnectionManager dbConnectionManager = new DBConnectionManager();

        AuthorDAO authorDAO = new AuthorDAO(dbConnectionManager);
        QuoteDAO quoteDAO = new QuoteDAO(dbConnectionManager);
        TagDAO tagDAO = new TagDAO(dbConnectionManager);
        TagQuoteDAO tagQuoteDAO = new TagQuoteDAO(dbConnectionManager);

        AuthorService authorService = new AuthorService(authorDAO);
        QuoteService quoteService = new QuoteService(quoteDAO);
        TagService tagService = new TagService(tagDAO);
        TagQuoteService tagQuoteService = new TagQuoteService(tagQuoteDAO);

        injector.register(DBConnectionManager.class, dbConnectionManager);

        injector.register(AuthorDAO.class, authorDAO);
        injector.register(QuoteDAO.class, quoteDAO);
        injector.register(TagDAO.class, tagDAO);
        injector.register(TagQuoteDAO.class, tagQuoteDAO);

        injector.register(AuthorService.class, authorService);
        injector.register(QuoteService.class, quoteService);
        injector.register(TagService.class, tagService);
        injector.register(TagQuoteService.class, tagQuoteService);

        injector.register(ScraperMainService.class, new ScraperMainService());
    }
}