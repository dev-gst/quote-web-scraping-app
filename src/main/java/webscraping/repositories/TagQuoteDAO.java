package webscraping.repositories;

import webscraping.database.DBConnectionManager;
import webscraping.model.DTOs.TagQuoteDTO;
import webscraping.model.entities.TagQuote;
import webscraping.repositories.interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TagQuoteDAO implements DAO<TagQuote, TagQuoteDTO> {

    private final Connection conn;

    public TagQuoteDAO(DBConnectionManager dbConnectionManager) {
        this.conn = dbConnectionManager.getConnection();
    }

    public TagQuote getById(int id) {
        String query = "SELECT * FROM tag_quote WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildTagQuote(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get the TagQuote from the Database", e);
        }

        return null;
    }

    public Set<TagQuote> getByQuoteId(int id) {
        String query = "SELECT * FROM tag_quote WHERE quote_id = ?";

        return getTagQuotes(id, query);
    }

    public Set<TagQuote> getByTagId(int id) {
        String query = "SELECT * FROM tag_quote WHERE tag_id = ?";

        return getTagQuotes(id, query);
    }

    public TagQuote getByTagAndQuote(TagQuoteDTO tagQuoteDTO) {
        String query = "SELECT * FROM tag_quote WHERE tag_id = ? AND quote_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tagQuoteDTO.getTagId());
            stmt.setInt(2, tagQuoteDTO.getQuoteId());

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildTagQuote(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get the TagQuote from the Database", e);
        }

        return null;
    }

    private Set<TagQuote> getTagQuotes(int id, String query) {
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            Set<TagQuote> tagQuotes = new HashSet<>();
            while (rs.next()) {
                tagQuotes.add(buildTagQuote(rs));
            }

            return tagQuotes;

        } catch (SQLException e) {
            throw new RuntimeException("Could not get the TagQuote from the Database", e);
        }
    }

    private TagQuote buildTagQuote(ResultSet rs) throws SQLException {
        TagQuote tagQuote = new TagQuote();

        tagQuote.setId(rs.getInt("id"));
        tagQuote.setQuoteId(rs.getInt("quote_id"));
        tagQuote.setTagId(rs.getInt("tag_id"));

        return tagQuote;
    }

    @Override
    public int save(TagQuoteDTO tagQuoteDTO) {
        String query = "INSERT INTO tag_quote(quote_id, tag_id) VALUES(?, ?) RETURNING id";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tagQuoteDTO.getQuoteId());
            stmt.setInt(2, tagQuoteDTO.getTagId());

            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Could not get TagQuote id");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not save TagQuote into Database", e);
        }
    }
}
