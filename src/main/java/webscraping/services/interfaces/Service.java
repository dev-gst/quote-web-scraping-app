package webscraping.services.interfaces;

import webscraping.model.DTOs.AuthorDTO;

public interface Service<Entity, DTO> {

    public Entity getById(int id);
    public int save(DTO dto);
}
