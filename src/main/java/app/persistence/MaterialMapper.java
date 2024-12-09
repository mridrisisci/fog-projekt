package app.persistence;

import app.entities.Carport;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.utilities.Calculator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MaterialMapper
{

    public static List<Material> createMaterialList(int[] materialQuantity, ConnectionPool pool) throws DatabaseException
    {
        // TODO: Muligvis en join her? eller alternativt en SQL der henter fra forbindelsestabellen?
        String sql = "SELECT name, unit, description FROM materials";

        String name;
        String unit;
        String description;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            List<Material> materialList = new ArrayList<>();
            while (rs.next())
            {
                // TODO: Mangler en if-statement, der checker om material er == post
                // TODO: brug 'materialQuantity' (antal posts) til at bestemme antallet af materiale der hentes fra db
                // hvordan checker vi typen af et materiale?
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialList.add(new Material(name, unit, description));
            }
            return materialList;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: Test
    public static void insertNewMaterial(String name, String unit, int price, int length, int height, int width, String type, String description, ConnectionPool pool) throws DatabaseException
    {

        String sql = "INSERT INTO materials (name, unit, price, length, height, width, type, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, name);
            ps.setString(2, unit);
            ps.setInt(3, price);
            ps.setInt(4, length);
            ps.setInt(5, height);
            ps.setInt(6, width);
            ps.setString(7, type);
            ps.setString(8, description);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Kunne ikke oprette materialet");
            }
            ResultSet rs = ps.getGeneratedKeys();
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: Lave pickList som kalder på alle de metoder der udregner materiale, længder og antal


    public static List<Material> createPickList(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        List<Material> pickList = new ArrayList<>();

        pickList.add(getPosts(carport, pool));
        pickList.add(getBeams(carport, pool));
        pickList.add(getSideUnderFasciaBoard(carport, pool));
        pickList.add(getSideOverFasciaBoard(carport, pool));
        pickList.add(getFrontAndBackUnderFasciaBoard(carport, pool));
        pickList.add(getFrontAndBackOverFasciaBoard(carport, pool));
        pickList.add(getRafters(carport, pool));
        pickList.add(getBoardBolt(carport, pool));
        pickList.add(getHardwareForRaftersLeft(carport, pool));
        pickList.add(getHardwareForRaftersRight(carport, pool));
        pickList.add(getHardwareScrews(carport, pool));
        pickList.add(getStandardScrews(carport, pool));
        pickList.add(getRollForWindCross(carport, pool));
        pickList.add(getScrewsForRoofing(carport, pool));
        pickList.add(getSquareWasher(carport, pool));
        pickList.add(getRoofPlatesLong(carport, pool));
        pickList.add(getRoofPlatesShort(carport, pool));

        //TODO: tjek om materialer faktisk fjernes
        pickList.removeIf(material -> material.getQuantity() == 0);

        return pickList;
    }

    public static List<Material> getPickList(int orderID, ConnectionPool pool) throws DatabaseException
    {
        List<Material> pickList = new ArrayList<>();

        String sql = "SELECT material.material_id, material.type, material.length, material.name, material.unit, material.price, material.description, orders_materials.order_id, orders_materials.quantity\n" +
                "                FROM orders_materials\n" +
                "                INNER JOIN materials material ON orders_materials.material_id = material.material_id\n" +
                "                WHERE order_id = ?";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            ps.setInt(1, orderID);
            while (rs.next())
            {
                int materialID = rs.getInt("material_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                int price = rs.getInt("price");
                int length = rs.getInt("length");
                String type = rs.getString("type");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");

                Material material = new Material(materialID, name, description, price, unit, quantity, length, type);
                pickList.add(material);

            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Error fetching materials from the database", e.getMessage());
        }
        return pickList;
    }

    public static void insertPickListInDB(List<Material> pickList, Carport carport, ConnectionPool pool) throws DatabaseException
    {
        for (Material material : pickList)
        {
            String sql = "INSERT INTO orders_materials(order_id, material_id, quantity) VALUES (?, ?, ?);";

            int orderID = carport.getOrderID();
            int materialID = material.getMaterialID();
            int quantity = material.getQuantity();

            try (Connection connection = pool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderID);
                ps.setInt(2, materialID);
                ps.setInt(3, quantity);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1)
                {
                    throw new DatabaseException("Failed to update price for the material with ID: " + materialID);
                }
            } catch (SQLException | DatabaseException e)
            {
                System.out.println(e.getMessage());
                throw new DatabaseException(e.getMessage());
            }
        }
    }


    //TODO: MANGLER TEST!
    public static Material getPosts(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcPosts(carport);
        int materialID;
        int length;
        int price;
        String type = "Stolpe";
        Material material = null;

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                length = rs.getInt("length");
                type = rs.getString("type");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material getBeams(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcBeams(carport)[0];
        int length = Calculator.calcBeams(carport)[1];
        int materialID;
        int price;
        String type = "Rem";
        Material beams = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                type = rs.getString("type");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                beams = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return beams;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: LAVE CARPORT OBJEKT når der er ny ordre, som bruges i parametrene
    //TODO: MANGLER TEST!
    public static Material getSideUnderFasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int price;
        int quantity = Calculator.calcSidesFasciaBoard(carport)[0];
        int length = Calculator.calcSidesFasciaBoard(carport)[1];
        String type = "Understernbrædt";
        int materialID;
        Material underFasciaBoard = null;

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                type = rs.getString("type");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                underFasciaBoard = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material getSideOverFasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcSidesFasciaBoard(carport)[0];
        int length = Calculator.calcSidesFasciaBoard(carport)[1];
        int materialID;
        int price;
        String type = "Oversternbrædt";
        Material overFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                type = rs.getString("type");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                overFasciaBoard = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return overFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material getFrontAndBackUnderFasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcFrontAndBackFasciaBoard(carport)[0];
        int length = Calculator.calcFrontAndBackFasciaBoard(carport)[1];
        int materialID;
        String type = "Understernbrædt";
        int price;
        Material underFasciaBoard = null;

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                type = rs.getString("type");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                underFasciaBoard = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material getFrontAndBackOverFasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcFrontAndBackFasciaBoard(carport)[0];
        int length = Calculator.calcFrontAndBackFasciaBoard(carport)[1];
        int materialID;
        int price;
        String type = "Oversternbrædt";
        Material material = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                type = rs.getString("type");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material getRafters(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcRafters(carport)[0];
        int length = Calculator.calcRafters(carport)[1];
        int materialID;
        int price;
        String type = "Spær";
        Material material = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                type = rs.getString("type");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Material getStandardScrews(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcStandardScrews();
        int materialID;
        int price;
        String type = "Skruer";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Material getScrewsForRoofing(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ? ;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcScrewsForRoofing(carport);
        int materialID;
        int price;
        String type = "Bundskruer";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Material getRollForWindCross(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ? ;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcRollForWindCross();
        int materialID;
        int price;
        String type = "Hulbånd";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Material getSquareWasher(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ? ;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcSquareWasher(carport);
        int materialID;
        int price;
        String type = "Firkantskiver";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Material getHardwareForRaftersLeft(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ? ;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcHardwareForRaftersLeft(carport);
        int materialID;
        int price;
        String type = "Beslag - Venstre";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Material getHardwareForRaftersRight(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ? ;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcHardwareForRaftersRight(carport);
        int materialID;
        int price;
        String type = "Beslag - Højre";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Material getHardwareScrews(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ? ;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcHardwareScrews(carport);
        int materialID;
        int price;
        String type = "Beslagskruer";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }


    public static Material getBoardBolt(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, type FROM materials WHERE type = ? ;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcBoardBolt(carport);
        int materialID;
        int price;
        String type = "Bræddebolt";
        Material material = null;

        //TODO: Skal lige fikses
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                material = new Material(materialID, name, description, price, unit, quantity, type);
            }
            return material;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }


    public static Material getRoofPlatesLong(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int price;
        int quantity = Calculator.calcRoofPlates(carport)[0];
        int length = 600;
        String type = "Tagplade";
        int materialID;
        Material roofing = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                type = rs.getString("type");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                roofing = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return roofing;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public static Material getRoofPlatesShort(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int price;
        int quantity = Calculator.calcRoofPlates(carport)[1];
        int length = 360;
        String type = "Tagplade";
        int materialID;
        Material roofing = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, type);
            ps.setInt(2, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                type = rs.getString("type");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                roofing = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return roofing;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }


    public static Material getMaterial()
    {
        return null;
    }

    public static Material getMaterialByID()
    {
        return null;
    }

    public static void updateMaterial()
    {
    }

    public static void updateMaterialByPrice()
    {
    }

    public static void deleteMaterial()
    {
    }

    public static List<Material> getAllMaterials(ConnectionPool pool) throws DatabaseException
    {

        String sql = "SELECT * FROM materials";
        List<Material> materials = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {

            while (rs.next())
            {

                int id = rs.getInt("material_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                int price = rs.getInt("price");
                int length = rs.getInt("length");
                int height = rs.getInt("height");
                int width = rs.getInt("width");
                String type = rs.getString("type");
                String description = rs.getString("description");

                Material material = new Material(id, name, description, price, unit, length, height, width, type);
                materials.add(material);

            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Error fetching materials from the database", e.getMessage());
        }
        return materials;
    }
}
