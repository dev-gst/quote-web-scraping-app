package webscraping.repositories.interfaces;

public interface DAO<Entity, DTO> {

    Entity getById(int id);
    int save(DTO dto);
}
