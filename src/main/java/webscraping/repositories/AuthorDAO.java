package webscraping.repositories;

import webscraping.database.DBConnectionManager;
import webscraping.model.DTOs.AuthorDTO;
import webscraping.model.entities.Author;
import webscraping.repositories.interfaces.DAO;

import java.sql.*;

public class AuthorDAO implements DAO<Author, AuthorDTO> {

    private final Connection conn;

    public AuthorDAO(DBConnectionManager dbConnectionManager) {
        this.conn = dbConnectionManager.getConnection();
    }

    @Override
    public Author getById(int id) {
        String query = "SELECT * FROM authors WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildAuthor(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get author from the database", e);
        }

        return null;
    }

    public Author getByName(String name) {
        String query = "SELECT * FROM authors WHERE name = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, name);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildAuthor(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get author from the database", e);
        }

        return null;
    }

    private Author buildAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setName(rs.getString("name"));
        author.setUrl(rs.getString("url"));

        return author;
    }

    @Override
    public int save(AuthorDTO authorDTO) {
        String query = "INSERT INTO authors (name, url) VALUES (?, ?) RETURNING id";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, authorDTO.getName());
            stmt.setString(2, authorDTO.getUrl());

            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Could not get the author ID");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not save author to the database", e);
        }
    }
}
