package webscraping.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Quote {

    private int id;
    private String text;
    private Author author;
    private Set<Tag> tags;

    public Quote() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }

        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
