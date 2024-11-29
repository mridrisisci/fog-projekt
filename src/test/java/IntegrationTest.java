
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest

{
//    private Connection connection;
//    boolean actual;
//    boolean expected;
//    IntegrationTest it = new IntegrationTest();
//    BigDecimal bd = new BigDecimal(500.00);
//    User u1 = new User(1, "Casper", "Gervig", "gervig91", "gervig@gmail.com", "1234", bd, 2400, "customer");

    ConnectionPool connectionPoolTest = ConnectionPool.getInstance("postgres", "postgres", "jdbc:postgresql://localhost:5432/%s?currentSchema=test_schema", "carport");

    @Before
    public void setUp() throws SQLException
    {
        clearDatabase();
        initializeTestData();
    }

    @AfterEach
    public void tearDown()
    {
        // Close the connection pool after tests
        connectionPoolTest.close();
    }

    //TODO add tables back in when you run the tests
    private void clearDatabase() throws SQLException
    {
        try (Connection conn = connectionPoolTest.getConnection(); Statement stmt = conn.createStatement())
        {
            stmt.execute("DROP SCHEMA IF EXISTS test_schema CASCADE;");
            stmt.execute("CREATE SCHEMA test_schema;");
        }
    }

    private void initializeTestData() throws SQLException {
        try (Connection conn = connectionPoolTest.getConnection(); Statement stmt = conn.createStatement()) {

            // Drop the schema if it exists
            stmt.execute("DROP SCHEMA IF EXISTS test_schema CASCADE;");

            // Create the test_schema
            stmt.execute("CREATE SCHEMA IF NOT EXISTS test_schema;");

            // Create
        }
    }

    @Test
    public void calcRoofPlates() throws DatabaseException
    {
        
    }

}
