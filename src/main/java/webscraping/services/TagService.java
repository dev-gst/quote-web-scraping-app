package webscraping.services;

import webscraping.model.DTOs.TagDTO;
import webscraping.model.entities.Tag;
import webscraping.repositories.TagDAO;
import webscraping.services.interfaces.Service;

public class TagService implements Service<Tag, TagDTO> {

    private final TagDAO tagDAO;

    public TagService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Tag getById(int id) {
        return tagDAO.getById(id);
    }

    public Tag getByName(String tagName) {
        return tagDAO.getByName(tagName);
    }

    @Override
    public int save(TagDTO tagDTO) {
        return tagDAO.save(tagDTO);
    }
}
