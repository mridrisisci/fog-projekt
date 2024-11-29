
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
        String sql = "-- This script was generated by the ERD tool in pgAdmin 4.\n" +
                "-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.\n" +
                "BEGIN;\n" +
                "\n" +
                "-- Drop tables if they exist\n" +
                "DROP TABLE IF EXISTS test_schemaaddresses CASCADE;\n" +
                "DROP TABLE IF EXISTS test_schemaaccounts CASCADE;\n" +
                "DROP TABLE IF EXISTS test_schemacities CASCADE;\n" +
                "DROP TABLE IF EXISTS test_schemaorders CASCADE;\n" +
                "DROP TABLE IF EXISTS test_schemapostal_code CASCADE;\n" +
                "DROP TABLE IF EXISTS test_schemamaterials CASCADE;\n" +
                "DROP TABLE IF EXISTS test_schemaorders_materials CASCADE;\n" +
                "\n" +
                "-- Old tables no longer used\n" +
                "DROP TABLE IF EXISTS test_schemamaterial_variants CASCADE;\n" +
                "DROP TABLE IF EXISTS test_schemaorders_material_variants CASCADE;\n" +
                "\n" +
                "-- Create tables\n" +
                "CREATE TABLE IF NOT EXISTS test_schemaaccounts\n" +
                "(\n" +
                "    account_id serial NOT NULL,\n" +
                "    role character varying(11) NOT NULL,\n" +
                "    username character varying(64) NOT NULL,\n" +
                "    email character varying(100),\n" +
                "    password character varying(100),\n" +
                "    telephone integer,\n" +
                "    addresses_id integer NOT NULL,\n" +
                "    CONSTRAINT account_pk PRIMARY KEY (account_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS test_schemaaddresses\n" +
                "(\n" +
                "    addresses_id serial NOT NULL,\n" +
                "    address character varying(64) NOT NULL,\n" +
                "    postal_code_id integer NOT NULL,\n" +
                "    city_id integer NOT NULL,\n" +
                "    CONSTRAINT addresses_pkey PRIMARY KEY (addresses_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS test_schemacities\n" +
                "(\n" +
                "    city_id serial NOT NULL,\n" +
                "    city character varying(50) NOT NULL,\n" +
                "    CONSTRAINT cities_pkey PRIMARY KEY (city_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS test_schemamaterials\n" +
                "(\n" +
                "    material_id serial NOT NULL,\n" +
                "    name character varying(100) NOT NULL,\n" +
                "    unit character varying(10) NOT NULL,\n" +
                "    price integer NOT NULL,\n" +
                "    length integer,\n" +
                "    height integer,\n" +
                "    width integer,\n" +
                "    type character varying(50),\n" +
                "    description character varying(100),\n" +
                "    CONSTRAINT material_pk PRIMARY KEY (material_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS test_schemaorders\n" +
                "(\n" +
                "    order_id serial NOT NULL,\n" +
                "    carport_id character varying(8) NOT NULL,\n" +
                "    salesperson_id integer NOT NULL,\n" +
                "    status character varying(10) NOT NULL,\n" +
                "    price integer,\n" +
                "    sales_price integer,\n" +
                "    coverage_ratio_percentage integer,\n" +
                "    order_placed timestamp with time zone,\n" +
                "    order_paid boolean NOT NULL,\n" +
                "    height integer NOT NULL,\n" +
                "    width integer NOT NULL,\n" +
                "    length integer NOT NULL,\n" +
                "    \"hasShed\" boolean,\n" +
                "    roof_type character varying(6) NOT NULL,\n" +
                "    account_id integer NOT NULL,\n" +
                "    CONSTRAINT orders_pk PRIMARY KEY (order_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS test_schemaorders_materials\n" +
                "(\n" +
                "    orders_materials_id serial NOT NULL,\n" +
                "    order_id integer NOT NULL,\n" +
                "    material_id integer NOT NULL,\n" +
                "    quantity integer NOT NULL,\n" +
                "    CONSTRAINT orders_materials_pk PRIMARY KEY (orders_materials_id),\n" +
                "    CONSTRAINT orders_materials_material_fk FOREIGN KEY (material_id)\n" +
                "        REFERENCES test_schemamaterials (material_id) MATCH SIMPLE\n" +
                "        ON UPDATE CASCADE\n" +
                "        ON DELETE CASCADE,\n" +
                "    CONSTRAINT orders_materials_order_fk FOREIGN KEY (order_id)\n" +
                "        REFERENCES test_schemaorders (order_id) MATCH SIMPLE\n" +
                "        ON UPDATE CASCADE\n" +
                "        ON DELETE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS test_schemapostal_code\n" +
                "(\n" +
                "    postal_code_id serial NOT NULL,\n" +
                "    postal_code integer NOT NULL,\n" +
                "    CONSTRAINT postal_code_pkey PRIMARY KEY (postal_code_id)\n" +
                ");\n" +
                "\n" +
                "-- Add foreign key constraints\n" +
                "ALTER TABLE test_schemaaccounts\n" +
                "    ADD CONSTRAINT accounts_addresses_fk FOREIGN KEY (addresses_id)\n" +
                "        REFERENCES test_schemaaddresses (addresses_id) MATCH SIMPLE\n" +
                "        ON UPDATE CASCADE\n" +
                "        ON DELETE CASCADE;\n" +
                "\n" +
                "ALTER TABLE test_schemaaddresses\n" +
                "    ADD CONSTRAINT addresses_cities_fk FOREIGN KEY (city_id)\n" +
                "        REFERENCES test_schemacities (city_id) MATCH SIMPLE\n" +
                "        ON UPDATE CASCADE\n" +
                "        ON DELETE CASCADE;\n" +
                "\n" +
                "ALTER TABLE test_schemaaddresses\n" +
                "    ADD CONSTRAINT addresses_postal_code_fk FOREIGN KEY (postal_code_id)\n" +
                "        REFERENCES test_schemapostal_code (postal_code_id) MATCH SIMPLE\n" +
                "        ON UPDATE CASCADE\n" +
                "        ON DELETE CASCADE;\n" +
                "\n" +
                "ALTER TABLE test_schemaorders\n" +
                "    ADD CONSTRAINT orders_account_fk FOREIGN KEY (account_id)\n" +
                "        REFERENCES test_schemaaccounts (account_id) MATCH SIMPLE\n" +
                "        ON UPDATE CASCADE\n" +
                "        ON DELETE CASCADE;\n" +
                "\n" +
                "\n" +
                "-- Insert data into materials\n" +
                "INSERT INTO test_schemamaterials (name, unit, price, length, height, width, type, description)\n" +
                "VALUES\n" +
                "    ('25x200 mm. trykimp. Brædt', 'Stk', 177, 360, 25, 200, 'Understernbrædt', 'understernbrædder til for & bag ende'),\n" +
                "    ('25x200 mm. trykimp. Brædt', 'Stk', 265, 540, 25, 200, 'Understernbrædt', 'understernbrædder til siderne'),\n" +
                "    ('25x125mm. trykimp. Brædt', 'Stk', 119, 360, 25, 125, 'Oversternbrædt' , 'oversternbrædder til forenden'),\n" +
                "    ('25x125mm. trykimp. Brædt', 'Stk', 178, 540, 25, 125, 'Oversternbrædt', 'oversternbrædder til siderne'),\n" +
                "    ('38x73 mm. Lægte ubh.', 'Stk', 63, 420, 38, 73, 'Lægte', 'z på bagside af dør'),\n" +
                "    ('45x95 mm. Reglar ub.', 'Stk', 35, 270, 45, 95, 'Reglar', 'løsholter til skur gavle'),\n" +
                "    ('45x95 mm. Reglar ub.', 'Stk', 30, 240, 45, 95, 'Reglar', 'løsholter til skur sider'),\n" +
                "    ('45x195 mm. spærtræ ubh.', 'Stk', 274, 480, 45, 195, 'Rem', 'Remme i sider, sadles ned i stolper (skur del, deles)'),\n" +
                "    ('45x195 mm. spærtræ ubh.', 'Stk', 342, 600, 45, 195, 'Rem', 'Remme i sider, sadles ned i stolper (skur del, deles)'),\n" +
                "    ('45x195 mm. spærtræ ubh.', 'Stk', 274, 480, 45, 195, 'Spær', 'Spær, monteres på rem'),\n" +
                "    ('45x195 mm. spærtræ ubh.', 'Stk', 342, 600, 45, 195, 'Spær', 'Spær, monteres på rem'),\n" +
                "    ('97x97 mm. trykimp. Stolpe', 'Stk', 266, 300, 97, 97, 'Stolpe', 'stolper nedgraves 90 cm i jord'),\n" +
                "    ('19x100 mm. trykimp. Brædt', 'Stk', 19, 210, 19, 100, 'Beklædning', 'beklædning af skur 1 på 2'),\n" +
                "    ('19x100 mm. trykimp. Brædt', 'Stk', 48, 540, 19, 100, 'Vandbrædt', 'vandbrædt på stern i sider'),\n" +
                "    ('19x100 mm. trykimp. Brædt', 'Stk', 32, 360, 19, 100, 'Vandbrædt', 'vandbrædt på stern i forende'),\n" +
                "    ('Plastmo Ecolite blåtonet', 'Stk', 339, 600, 2, 109, 'Tagplade', 'tagplader monteres på spær'),\n" +
                "    ('Plastmo Ecolite blåtonet', 'Stk', 199, 360, 2, 109, 'Tagplade', 'tagplader monteres på spær'),\n" +
                "    ('plastmo bundskruer 200 stk', 'Pakke', 429, 2, 1, 1, 'Bundskruer', 'skruer til tagplader'),\n" +
                "    ('hulbånd 1x20 mm. 10 mtr.', 'Rulle', 349, 1000, 1, 20, 'Hulbånd', 'vindkryds på spær'),\n" +
                "    ('universal 190 mm højre', 'Stk', 50, 5, 150, 5, 'Beslag - Højre', 'Til montering af spær på rem'),\n" +
                "    ('universal 190 mm venstre', 'Stk', 50, 5, 150, 5, 'Beslag - Venstre', 'Til montering af spær på rem'),\n" +
                "    ('4,5 x 60 mm. skruer 200 stk.', 'Pakke', 169, 6, 1, 1, 'Skruer', 'Til montering af stern & vandbrædt'),\n" +
                "    ('4,0 x 50 mm. beslagskruer', 'Pakke', 139, 5, 1, 1, 'Beslagskruer', 'Til montering af universalbeslag + hulbånd'),\n" +
                "    ('bræddebolt 10 x 120 mm.', 'Stk', 409, 1, 1, 1, 'Bræddebolt', 'Til montering af rem på stolper'),\n" +
                "    ('firkantskiver 40x40x11mm', 'Stk', 9, 1, 1, 1, 'Firkantskiver','Til montering af rem på stolper'),\n" +
                "    ('4,5 x 70 mm. Skruer 400 stk.', 'Pakke', 165, 7, 1, 1, 'Beklædningsskruer', 'Til montering af yderste beklædning'),\n" +
                "    ('4,5 x 50 mm. Skruer 300 stk.', 'Pakke', 90, 5, 1, 1, 'Beklædningsskruer', 'Til montering af inderste beklædning'),\n" +
                "    ('stalddørsgreb 50x75', 'Sæt', 269, 3, 1, 1, 'Stalddørsgreb', 'Til lås på dør i skur'),\n" +
                "    ('t hængsel 390 mm', 'Stk', 139, 4, 1, 1, 'Hængsel', 'Til skurdør'),\n" +
                "    ('vinkelbeslag 35', 'Stk', 1, 5, 5, 4, 'Vinkelbeslag', 'Til montering af løsholter i skur');\n" +
                "\n" +
                "\n" +
                "-- End transaction\n" +
                "END;\n";

        try (Connection connection = connectionPoolTest.getConnection(); PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }
    

}
