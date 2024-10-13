package webscraping.model.entities;

public class TagQuote {

    int id;
    Quote quote;
    Tag tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
