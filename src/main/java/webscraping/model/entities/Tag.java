package webscraping.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Tag {
    private int id;
    private String name;
    private String url;
    private Set<Quote> quotes;

    public Tag() {}

    public Tag(int id, String name, String url, Set<Quote> quotes) {
        this.id = id;
        this.name = name;
        this.url = url;

        this.quotes = quotes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
    }

    public void addQuote(Quote quote) {
        if (this.quotes == null) {
            this.quotes = new HashSet<>();
        }

        this.quotes.add(quote);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
