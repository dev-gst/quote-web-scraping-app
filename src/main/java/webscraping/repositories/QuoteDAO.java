package webscraping.repositories;

import webscraping.database.DBConnectionManager;
import webscraping.model.DTOs.QuoteDTO;
import webscraping.model.entities.Author;
import webscraping.model.entities.Quote;
import webscraping.repositories.interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuoteDAO implements DAO<Quote, QuoteDTO> {

    private final Connection conn;

    public QuoteDAO(DBConnectionManager dbConnectionManager) {
        this.conn = dbConnectionManager.getConnection();
    }

    @Override
    public Quote getById(int id) {
        String query = "SELECT * FROM quotes WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildQuote(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get the quote from the Database", e);
        }

        return null;
    }

    public Quote getByText(String quoteText) {
        String query = "SELECT * FROM quotes WHERE q_text = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, quoteText);

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return buildQuote(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not get the quote from the Database", e);
        }

        return null;
    }

    private Quote buildQuote(ResultSet rs) throws SQLException {
        Quote quote = new Quote();
        quote.setId(rs.getInt("id"));
        quote.setText(rs.getString("q_text"));

        Author author = new Author();
        author.setId(rs.getInt("author_id"));

        quote.setAuthor(author);

        return quote;
    }

    @Override
    public int save(QuoteDTO quoteDTO) {
        String query = "INSERT INTO quotes(q_text, author_id) VALUES (?, ?) RETURNING id";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, quoteDTO.getText());
            stmt.setInt(2, quoteDTO.getAuthorId());

            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Could not get the quote ID");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not save quote into the Database", e);
        }
    }
}
