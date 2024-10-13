package webscraping.services;

import webscraping.model.DTOs.AuthorDTO;
import webscraping.model.entities.Author;
import webscraping.repositories.AuthorDAO;
import webscraping.services.interfaces.Service;

public class AuthorService implements Service<Author, AuthorDTO> {

    private final AuthorDAO authorDAO;

    public AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public Author getById(int Id) {
        return authorDAO.getById(Id);
    }

    public Author getByName(String authorName) {
        return authorDAO.getByName(authorName);
    }

    @Override
    public int save(AuthorDTO authorDTO) {
        return authorDAO.save(authorDTO);
    }
}
