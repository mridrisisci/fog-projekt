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
        //String sql = "SELECT * FROM public.orders_materials WHERE order_Id= ?;";

        List<Material> pickList = new ArrayList<>();
        //
        Carport carport = null;
        pickList.add(getPosts(carport, pool));
        pickList.add(getBeams(carport, pool));
        pickList.add(getSideUnderfasciaBoard(carport, pool));
        pickList.add(getSideOverfasciaBoard(carport, pool));
        pickList.add(getFrontAndBackUnderfasciaBoard(carport, pool));
        pickList.add(getFrontAndBackOverfasciaBoard(carport, pool));

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

    public static int insertPickListPrice(int orderID, ConnectionPool pool) throws DatabaseException
    {

        String sql = "INSERT INTO public.orders(price) WHERE order_id = ? VALUES(?);";

        List<Material> pickList = createPickList(orderID, pool);
        int pickListPrice = Calculator.calcPickListPrice(pickList);

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ps.setInt(2, pickListPrice);

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
        //HUSK AT MATERIAL_ID ER HARDCODED MED VILJE!!!!!!!
        //TODO: GØR MATERIAL_ID = 10 DET DYNAMISK
        String sql = "SELECT material_id, name, unit, description, price, length FROM public.materials WHERE material_id = 10;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcPosts(carport);
        int materialID;
        int length;
        int price;
        Material posts = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                materialID = rs.getInt("material_id");
                length = rs.getInt("length");
                price = rs.getInt("price");
                posts = new Material(materialID, name, description, price, unit, quantity, length);
            }
            return posts;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    //TODO: Evt. tilføj i metoden vedr. type (String type - SQL og tilføjes i constructor i Material)
    public static Material getBeams(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        //HUSK AT MATERIAL_ID ER HARDCODED MED VILJE!!!!!!!
        //TODO: GØR MATERIAL_ID = 10 DET DYNAMISK
        String sql = "SELECT material_id, name, unit, description, price, length FROM public.materials WHERE material_id IN (8,9) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcBeams(carport)[0];
        int length = Calculator.calcBeams(carport)[1];
        int materialID;
        int price;
        Material beams = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                beams = new Material(materialID, name, description, price, unit, quantity, length);
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
    //TODO: Evt. tilføj i metoden vedr. type (String type - SQL og tilføjes i constructor i Material)
    public static Material getSideUnderfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        //HUSK AT MATERIAL_ID ER HARDCODED MED VILJE!!!!!!!
        //TODO: GØR MATERIAL_ID = 10 DET DYNAMISK
        String sql = "SELECT material_id, name, unit, description, price, length FROM public.materials WHERE material_id IN (1,2) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcSidesFasciaBoard(carport)[0];
        int length = Calculator.calcSidesFasciaBoard(carport)[1];
        int materialID;
        int price;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                underFasciaBoard = new Material(materialID, name, description, price, unit, quantity, length);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    //TODO: Evt. tilføj i metoden vedr. type (String type - SQL og tilføjes i constructor i Material)
    public static Material getSideOverfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        //HUSK AT MATERIAL_ID ER HARDCODED MED VILJE!!!!!!!
        //TODO: GØR MATERIAL_ID = 10 DET DYNAMISK
        String sql = "SELECT material_id, name, unit, description, price, length FROM public.materials WHERE material_id IN (3,4) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcSidesFasciaBoard(carport)[0];
        int length = Calculator.calcSidesFasciaBoard(carport)[1];
        int materialID;
        int price;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                underFasciaBoard = new Material(materialID, name, description, price, unit, quantity, length);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    //TODO: Evt. tilføj i metoden vedr. type (String type - SQL og tilføjes i constructor i Material)
    public static Material getFrontAndBackUnderfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        //HUSK AT MATERIAL_ID ER HARDCODED MED VILJE!!!!!!!
        //TODO: GØR MATERIAL_ID = 10 DET DYNAMISK
        String sql = "SELECT material_id, name, unit, description, price, length FROM public.materials WHERE material_id IN (1,2) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcFrontAndBackFasciaBoard(carport)[0];
        int length = Calculator.calcFrontAndBackFasciaBoard(carport)[1];
        int materialID;
        int price;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                underFasciaBoard = new Material(materialID, name, description, price, unit, quantity, length);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    //TODO: Evt. tilføj i metoden vedr. type (String type - SQL og tilføjes i constructor i Material)
    public static Material getFrontAndBackOverfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        //HUSK AT MATERIAL_ID ER HARDCODED MED VILJE!!!!!!!
        //TODO: GØR MATERIAL_ID = 10 DET DYNAMISK
        String sql = "SELECT material_id, name, unit, description, price, length FROM public.materials WHERE material_id IN (3,4) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcFrontAndBackFasciaBoard(carport)[0];
        int length = Calculator.calcFrontAndBackFasciaBoard(carport)[1];
        int materialID;
        int price;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                underFasciaBoard = new Material(materialID, name, description, price, unit, quantity, length);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    //TODO: Evt. tilføj i metoden vedr. type (String type - SQL og tilføjes i constructor i Material)
    public static Material getRafters(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        //HUSK AT MATERIAL_ID ER HARDCODED MED VILJE!!!!!!!
        //TODO: GØR MATERIAL_ID = 10 DET DYNAMISK
        String sql = "SELECT material_id, name, unit, description, price, length FROM public.materials WHERE material_id = 9;";

        String name;
        String unit;
        String description;
        int quantity = Calculator.calcRafters(carport);
        int length;
        int materialID;
        int price;
        Material rafters = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                price = rs.getInt("price");
                rafters = new Material(materialID, name, description, price, unit, quantity, length);
            }
            return rafters;
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
