
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

        try (Connection connectionTest = connectionPoolTest.getConnection(); PreparedStatement ps = connectionTest.prepareStatement(sql))
        {
            ps.executeQuery();
        }
    }

    private void initializeTestData() throws SQLException
    {


        String sqlAccounts = "INSERT INTO test_schema.accounts (role, username, email, password, telephone) VALUES(?,?,?,?,?)";

        String sqlOrders = "INSERT INTO test_schema.orders (carport_id, salesperson_id, status, price, sales_price, coverage_ratio_percentage, order_placed, order_paid, height, width, length, hasShed, roof_type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String sqlMaterials = "INSERT INTO test_schema.materials (name, unit, price, length, height, width, type, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String sql = "" + sqlAccounts + sqlOrders + sqlMaterials;

        try (Connection connection = connectionPoolTest.getConnection(); PreparedStatement ps = connection.prepareStatement(sql))
        {

            connection.setAutoCommit(false); // starter transaction

            // Add materials to batch
            addMaterialToBatch(ps, "25x200 mm. trykimp. Brædt", "Stk", 177, 360, 25, 200, "Understernbrædt", "understernbrædder til for & bag ende");
            addMaterialToBatch(ps, "25x200 mm. trykimp. Brædt", "Stk", 265, 540, 25, 200, "Understernbrædt", "understernbrædder til siderne");
            addMaterialToBatch(ps, "25x125mm. trykimp. Brædt", "Stk", 119, 360, 25, 125, "Oversternbrædt", "oversternbrædder til forenden");
            addMaterialToBatch(ps, "25x125mm. trykimp. Brædt", "Stk", 178, 540, 25, 125, "Oversternbrædt", "oversternbrædder til siderne");
            addMaterialToBatch(ps, "38x73 mm. Lægte ubh.", "Stk", 63, 420, 38, 73, "Lægte", "z på bagside af dør");
            addMaterialToBatch(ps, "45x95 mm. Reglar ub.", "Stk", 35, 270, 45, 95, "Reglar", "løsholter til skur gavle");
            addMaterialToBatch(ps, "45x95 mm. Reglar ub.", "Stk", 30, 240, 45, 95, "Reglar", "løsholter til skur sider");
            addMaterialToBatch(ps, "45x195 mm. spærtræ ubh.", "Stk", 274, 480, 45, 195, "Rem", "Remme i sider, sadles ned i stolper (skur del, deles)");
            addMaterialToBatch(ps, "45x195 mm. spærtræ ubh.", "Stk", 342, 600, 45, 195, "Rem", "Remme i sider, sadles ned i stolper (skur del, deles)");
            addMaterialToBatch(ps, "45x195 mm. spærtræ ubh.", "Stk", 274, 480, 45, 195, "Spær", "Spær, monteres på rem");
            addMaterialToBatch(ps, "45x195 mm. spærtræ ubh.", "Stk", 342, 600, 45, 195, "Spær", "Spær, monteres på rem");
            addMaterialToBatch(ps, "97x97 mm. trykimp. Stolpe", "Stk", 266, 300, 97, 97, "Stolpe", "stolper nedgraves 90 cm i jord");
            addMaterialToBatch(ps, "19x100 mm. trykimp. Brædt", "Stk", 19, 210, 19, 100, "Beklædning", "beklædning af skur 1 på 2");
            addMaterialToBatch(ps, "19x100 mm. trykimp. Brædt", "Stk", 48, 540, 19, 100, "Vandbrædt", "vandbrædt på stern i sider");
            addMaterialToBatch(ps, "19x100 mm. trykimp. Brædt", "Stk", 32, 360, 19, 100, "Vandbrædt", "vandbrædt på stern i forende");
            addMaterialToBatch(ps, "Plastmo Ecolite blåtonet", "Stk", 339, 600, 2, 109, "Tagplade", "tagplader monteres på spær");
            addMaterialToBatch(ps, "Plastmo Ecolite blåtonet", "Stk", 199, 360, 2, 109, "Tagplade", "tagplader monteres på spær");
            addMaterialToBatch(ps, "plastmo bundskruer 200 stk", "Pakke", 429, 2, 1, 1, "Bundskruer", "skruer til tagplader");
            addMaterialToBatch(ps, "hulbånd 1x20 mm. 10 mtr.", "Rulle", 349, 1000, 1, 20, "Hulbånd", "vindkryds på spær");
            addMaterialToBatch(ps, "universal 190 mm højre", "Stk", 50, 5, 150, 5, "Beslag - Højre", "Til montering af spær på rem");
            addMaterialToBatch(ps, "universal 190 mm venstre", "Stk", 50, 5, 150, 5, "Beslag - Venstre", "Til montering af spær på rem");
            addMaterialToBatch(ps, "4,5 x 60 mm. skruer 200 stk.", "Pakke", 169, 6, 1, 1, "Skruer", "Til montering af stern & vandbrædt");
            addMaterialToBatch(ps, "4,0 x 50 mm. beslagskruer", "Pakke", 139, 5, 1, 1, "Beslagskruer", "Til montering af universalbeslag + hulbånd");
            addMaterialToBatch(ps, "bræddebolt 10 x 120 mm.", "Stk", 409, 1, 1, 1, "Bræddebolt", "Til montering af rem på stolper");
            addMaterialToBatch(ps, "firkantskiver 40x40x11mm", "Stk", 9, 1, 1, 1, "Firkantskiver", "Til montering af rem på stolper");
            addMaterialToBatch(ps, "4,5 x 70 mm. Skruer 400 stk.", "Pakke", 165, 7, 1, 1, "Beklædningsskruer", "Til montering af yderste beklædning");
            addMaterialToBatch(ps, "4,5 x 50 mm. Skruer 300 stk.", "Pakke", 90, 5, 1, 1, "Beklædningsskruer", "Til montering af inderste beklædning");
            addMaterialToBatch(ps, "stalddørsgreb 50x75", "Sæt", 269, 3, 1, 1, "Stalddørsgreb", "Til lås på dør i skur");
            addMaterialToBatch(ps, "t hængsel 390 mm", "Stk", 139, 4, 1, 1, "Hængsel", "Til skurdør");
            addMaterialToBatch(ps, "vinkelbeslag 35", "Stk", 1, 5, 5, 4, "Vinkelbeslag", "Til montering af løsholter i skur");

            ps.executeBatch();
            connection.commit();
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    private void addMaterialToBatch(PreparedStatement ps, String name, String unit, int price, int length, int height, int width, String type, String description) throws SQLException {
        ps.setString(1, name);
        ps.setString(2, unit);
        ps.setInt(3, price);
        ps.setInt(4, length);
        ps.setInt(5, height);
        ps.setInt(6, width);
        ps.setString(7, type);
        ps.setString(8, description);
        ps.addBatch();
    }

    @Test
    public void calcRoofPlates() throws DatabaseException
    {

    }

}
