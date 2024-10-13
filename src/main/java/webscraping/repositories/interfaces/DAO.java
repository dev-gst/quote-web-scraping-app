package webscraping.repositories.interfaces;

public interface DAO<Entity, DTO> {

    public Entity getById(int id);
    public int save(DTO dto);
}
