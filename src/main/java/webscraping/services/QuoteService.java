package webscraping.services;

import webscraping.config.DependencyInjector;
import webscraping.model.DTOs.QuoteDTO;
import webscraping.model.entities.Author;
import webscraping.model.entities.Quote;
import webscraping.model.entities.Tag;
import webscraping.model.entities.TagQuote;
import webscraping.repositories.QuoteDAO;
import webscraping.services.interfaces.Service;

import java.util.Set;

public class QuoteService implements Service<Quote, QuoteDTO> {

    private final QuoteDAO quoteDAO;

    public QuoteService(QuoteDAO quoteDAO) {
        this.quoteDAO = quoteDAO;
    }

    @Override
    public Quote getById(int id) {
        Quote quote = quoteDAO.getById(id);
        if (quote == null) {
            return null;
        }

        assembleQuote(quote);

        return quote;
    }

    public Quote getByText(String quoteText) {
        Quote quote = quoteDAO.getByText(quoteText);
        if (quote == null) {
            return null;
        }

        assembleQuote(quote);

        return quote;
    }

    private static void assembleQuote(Quote quote) {
        AuthorService authorService = DependencyInjector.getInstance().getInstanceOf(AuthorService.class);
        TagQuoteService tagQuoteService = DependencyInjector.getInstance().getInstanceOf(TagQuoteService.class);
        TagService tagService = DependencyInjector.getInstance().getInstanceOf(TagService.class);

        Author author = authorService.getById(quote.getAuthor().getId());
        quote.setAuthor(author);

        Set<TagQuote> tagQuotes = tagQuoteService.getByQuoteId(quote.getId());
        tagQuotes.forEach(tagQuote -> {
            Tag tag = tagService.getById(tagQuote.getTagId());
            quote.addTag(tag);
        });
    }

    @Override
    public int save(QuoteDTO quoteDTO) {
        return quoteDAO.save(quoteDTO);
    }
}
