
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String sql = "DROP SCHEMA IF EXISTS test_schema CASCADE; " +
                "CREATE SCHEMA test_schema;";

        try (Connection connection = connectionPoolTest.getConnection(); PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.executeQuery();
        }
    }

    private void initializeTestData() throws SQLException {

        String sql = "";

        try (Connection connection = connectionPoolTest.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            
        }
    }

    @Test
    public void calcRoofPlates() throws DatabaseException
    {
        
    }

}
