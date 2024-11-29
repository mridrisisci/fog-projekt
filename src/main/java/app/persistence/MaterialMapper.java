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

    //TODO: Lave pickList som kalder på alle de metoder der udregner materiale, længder og antal
    public static List<Material> createPickList(int orderID, ConnectionPool pool) throws DatabaseException
    {
        List<Material> pickList = new ArrayList<>();

        //TODO: sørg for carport ikke er null
        Carport carport = null;
        pickList.add(getPosts(carport, pool));
        pickList.add(getBeams(carport, pool));
        pickList.add(getSideUnderfasciaBoard(carport, pool));
        pickList.add(getSideOverfasciaBoard(carport, pool));
        pickList.add(getFrontAndBackUnderfasciaBoard(carport, pool));
        pickList.add(getFrontAndBackOverfasciaBoard(carport, pool));
        pickList.add(getRafters(carport, pool));
        pickList.add(getBoardBolt(carport, pool);
        pickList.add(getHardwareForRaftersLeft(carport, pool));
        pickList.add(getHardwareForRaftersRight(carport, pool));
        pickList.add(getHardwareScrews(carport, pool));
        pickList.add(getStandardScrews(carport, pool));
        pickList.add(getRollForWindCross(carport, pool));
        pickList.add(getScrewsForRoofing(carport, pool));
        pickList.add(getSquareWasher(carport, pool));


        //TODO: tjek om materialer faktisk fjernes
        for (Material material : pickList)
        {
            if (material.getQuantity() == 0)
            {
                pickList.remove(material);
            }
        }

        for (Material material : pickList)
        {
            String sql = "INSERT INTO public.orders_materials(material_id,quantity) WHERE order_id = ? VALUES(?,?);";

            int materialID = material.getMaterialID();
            //TODO: orderID bliver ikke hentet nogle steder fra - endnu
            int quantity = material.getQuantity();
            //TODO: Mangler at få fikset type i get-metoderne og denne metode
            int type;

            try (Connection connection = pool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderID);
                ps.setInt(2, materialID);
                ps.setInt(3, quantity);

            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
                throw new DatabaseException(e.getMessage());
            }
        }

        return pickList;
    }

    public static int updatePickListPrice(int orderID, ConnectionPool pool) throws DatabaseException
    {

        String sql = "UPDATE public.orders SET price = ? WHERE order_id = ?;";

        List<Material> pickList = createPickList(orderID, pool);
        int pickListPrice = Calculator.calcPickListPrice(pickList);

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, pickListPrice);
            ps.setInt(2, orderID);

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

        return pickListPrice;
    }


    //TODO: MANGLER TEST!
    public static Material getPosts(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcPosts(carport);
        int materialID;
        int length;
        int price;
        String type = "Stolpe";
        Material posts = null;


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
                posts = new Material(materialID, name, description, price, unit, quantity, length, type);
            }
            return posts;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material getBeams(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ? AND length = ?;";

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
    public static Material getSideUnderfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ? AND length = ?;";

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
    public static Material getSideOverfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ? AND length = ?;";

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
    public static Material getFrontAndBackUnderfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ? AND length = ?;";

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
    public static Material getFrontAndBackOverfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ? AND length = ?;";

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
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ? AND length = ?";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ?;";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ? ;";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ? ;";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ? ;";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ? ;";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ? ;";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ? ;";

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
        String sql = "SELECT material_id, name, unit, description, price, type FROM public.materials WHERE type = ? ;";

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


    public static Material getRoofing(Carport carport, ConnectionPool pool)
    {
        String sql = "SELECT material_id, name, unit, description, price, length, type FROM public.materials WHERE type = ? AND length = ?;";

        String name;
        String unit;
        String description;
        int price;
        int quantity = Calculator.calcRoofPlates(carport)[0];
        int length = Calculator.calcRoofPlates(carport)[1];
        String type = "Tagplade";
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

    public static void addMaterialToDB()
    {
    }

    public static void deleteMaterial()
    {
    }

    public static List<Material> getAllMaterials()
    {
        return null;
    }
}
