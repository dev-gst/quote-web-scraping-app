package webscraping.repositories;

import webscraping.database.DBConnectionManager;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import webscraping.model.entities.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorDAOTest {

    private AutoCloseable closeable;

    private AuthorDAO authorDAO;

    @Mock
    private DBConnectionManager dbConnectionManager;

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        when(dbConnectionManager.getConnection()).thenReturn(conn);

        authorDAO = new AuthorDAO(dbConnectionManager);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getAuthorById() throws  SQLException {
        when(conn.prepareStatement("SELECT * FROM authors WHERE id = ?")).thenReturn(stmt);
        when(stmt.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("Author Name");
        when(rs.getString("url")).thenReturn("http://author.test");

        Author author = authorDAO.getById(1);

        verify(stmt).setInt(1, 1);
        Assertions.assertEquals(1, author.getId());
        Assertions.assertEquals("Author Name", author.getName());
        Assertions.assertEquals("http://author.test", author.getUrl());
    }
}
