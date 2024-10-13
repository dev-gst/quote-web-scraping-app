package webscraping.repositories;

import webscraping.database.DBConnectionManager;
import webscraping.model.DTOs.TagDTO;
import webscraping.model.entities.Tag;
import webscraping.repositories.interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagDAO implements DAO<Tag, TagDTO> {

    private final Connection conn;

    public TagDAO(DBConnectionManager dbConnectionManager) {
        this.conn = dbConnectionManager.getConnection();
    }

    @Override
    public Tag getById(int id) {
        String query = "SELECT * FROM tags WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildTag(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get the tag from the Database", e);
        }

        return null;
    }


    public Tag getByName(String tagName) {
        String query = "SELECT * FROM tags WHERE name = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tagName);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildTag(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get the tag from the Database", e);
        }

        return null;
    }

    private Tag buildTag(ResultSet rs) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));
        tag.setUrl(rs.getString("url"));

        return tag;
    }

    @Override
    public int save(TagDTO tagDTO) {
        String query = "INSERT INTO tags(name, url) VALUES(?, ?) RETURNING id";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tagDTO.getName());
            stmt.setString(2, tagDTO.getUrl());

            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Could not get tag id");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not save tag into Database", e);
        }
    }
}
