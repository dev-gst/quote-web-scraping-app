package webscraping.services.interfaces;

public interface Service<Entity, DTO> {

    Entity getById(int id);
    int save(DTO dto);
}
