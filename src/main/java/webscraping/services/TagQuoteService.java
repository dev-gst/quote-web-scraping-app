package webscraping.services;

import webscraping.model.DTOs.TagQuoteDTO;
import webscraping.model.entities.TagQuote;
import webscraping.repositories.TagQuoteDAO;
import webscraping.services.interfaces.Service;

import java.util.Set;

public class TagQuoteService implements Service<TagQuote, TagQuoteDTO> {

    public TagQuoteDAO tagQuoteDAO;

    public TagQuoteService(TagQuoteDAO tagQuoteDAO) {
        this.tagQuoteDAO = tagQuoteDAO;
    }

    @Override
    public TagQuote getById(int id) {
        return tagQuoteDAO.getById(id);
    }

    public Set<TagQuote> getByQuoteId(int id) {
        return tagQuoteDAO.getByQuoteId(id);
    }

    public Set<TagQuote> getByTagId(int id) {
        return tagQuoteDAO.getByTagId(id);
    }

    public TagQuote getByTagAndQuote(TagQuoteDTO tagQuoteDTO) {
        return tagQuoteDAO.getByTagAndQuote(tagQuoteDTO);
    }

    @Override
    public int save(TagQuoteDTO tagQuoteDTO) {
        return tagQuoteDAO.save(tagQuoteDTO);
    }
}
