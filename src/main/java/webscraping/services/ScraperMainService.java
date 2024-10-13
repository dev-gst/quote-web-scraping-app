package webscraping.services;

import webscraping.config.Env;
import webscraping.model.DTOs.AuthorDTO;
import webscraping.model.DTOs.QuoteDTO;
import webscraping.model.DTOs.TagDTO;
import webscraping.model.DTOs.TagQuoteDTO;
import webscraping.model.entities.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import webscraping.model.entities.Quote;
import webscraping.model.entities.Tag;
import webscraping.model.entities.TagQuote;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class ScraperMainService {

    private static final int WAITING_TIME = 2;

    private final AuthorService authorService;
    private final QuoteService quoteService;
    private final TagService tagService;
    private final TagQuoteService tagQuoteService;

    public ScraperMainService(
            AuthorService authorService,
            QuoteService quoteService,
            TagService tagService,
            TagQuoteService tagQuoteService
    ) {
        this.authorService = authorService;
        this.quoteService = quoteService;
        this.tagService = tagService;
        this.tagQuoteService = tagQuoteService;
    }

    public void start() {
        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {

            scheduler.schedule(this::scrape, WAITING_TIME, TimeUnit.SECONDS);
            scheduler.shutdown();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void scrape() {
        try {
            int i = 1;
            while(true) {
                Document doc = Jsoup.connect(Env.getScrapingUrl() + "/page/" + i).get();

                Elements allElements = doc.getElementsByClass("quote");
                Elements nextButton = doc.getElementsByClass("next");
                processElements(allElements);

                if (nextButton.isEmpty()) {
                    break;
                }

                i++;
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void processElements(Elements elements) {
        elements.forEach(e -> {
            String authorName = e.getElementsByClass("author").getFirst().text();
            String authorUrl = e.getElementsByTag("a").getFirst().getElementsByAttribute("href").attr("href");

            int authorId = saveAuthorToDB(authorName, authorUrl);

            String quoteText = e.getElementsByClass("text").getFirst().text();

            int quoteId = saveQuoteToDB(quoteText, authorId);

            e.getElementsByClass("tag").forEach(tag -> {

                String tagName = tag.text();
                String tagUrl = tag.attr("href");

                int tagId = saveTagToDB(tagName, tagUrl);

                saveTagQuoteToDB(quoteId, tagId);
            });
        });
    }

    private int saveAuthorToDB(String authorName, String authorUrl) {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(authorName);
        authorDTO.setUrl(authorUrl);

        Author author = authorService.getByName(authorName);
        if (author != null && author.getId() != 0) {
            return author.getId();
        }

        return authorService.save(authorDTO);
    }

    private int saveQuoteToDB(String quoteText, int authorId) {
        QuoteDTO quoteDTO = new QuoteDTO();
        quoteDTO.setText(quoteText);
        quoteDTO.setAuthorId(authorId);

        Quote quote = quoteService.getByText(quoteText);
        if (quote != null && quote.getId() != 0) {
            return quote.getId();
        }

        return quoteService.save(quoteDTO);
    }

    private int saveTagToDB(String tagName, String tagUrl) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName(tagName);
        tagDTO.setUrl(tagUrl);

        Tag tag = tagService.getByName(tagName);
        if (tag != null && tag.getId() != 0) {
            return tag.getId();
        }

        return tagService.save(tagDTO);
    }

    private void saveTagQuoteToDB(int quoteId, int tagId) {
        TagQuoteDTO tagQuoteDTO = new TagQuoteDTO();
        tagQuoteDTO.setQuoteId(quoteId);
        tagQuoteDTO.setTagId(tagId);

        TagQuote tagQuote = tagQuoteService.getByTagAndQuote(tagQuoteDTO);
        if (tagQuote != null && tagQuote.getId() != 0) {
            return;
        }

        tagQuoteService.save(tagQuoteDTO);
    }
}
