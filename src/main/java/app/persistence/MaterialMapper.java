package app.persistence;

import app.entities.Carport;
import app.entities.Material;
import app.exceptions.DatabaseException;

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
    public static List<Material> createPickList(ConnectionPool pool) throws DatabaseException
    {
        List<Material> pickList = new ArrayList<>();
        return pickList;
    }

    //TODO: MANGLER TEST!
    public static Material calcSideUnderfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, length FROM public.materials WHERE material_id IN (1,2) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = carport.calcSidesFasciaBoard()[0];
        int length = carport.calcSidesFasciaBoard()[1];
        int materialID;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                // hvordan checker vi typen af et materiale?
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                underFasciaBoard = new Material(materialID, name, description, unit, quantity, length);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material calcSideOverfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, length FROM public.materials WHERE material_id IN (3,4) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = carport.calcSidesFasciaBoard()[0];
        int length = carport.calcSidesFasciaBoard()[1];
        int materialID;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                // hvordan checker vi typen af et materiale?
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                underFasciaBoard = new Material(materialID, name, description, unit, quantity, length);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material calcFrontAndBackUnderfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, length FROM public.materials WHERE material_id IN (1,2) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = carport.calcFrontAndBackFasciaBoard()[0];
        int length = carport.calcFrontAndBackFasciaBoard()[1];
        int materialID;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                // hvordan checker vi typen af et materiale?
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                underFasciaBoard = new Material(materialID, name, description, unit, quantity, length);
            }
            return underFasciaBoard;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    //TODO: MANGLER TEST!
    public static Material calcFrontAndBackOverfasciaBoard(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT material_id, name, unit, description, length FROM public.materials WHERE material_id IN (3,4) AND length = ?;";

        String name;
        String unit;
        String description;
        int quantity = carport.calcFrontAndBackFasciaBoard()[0];
        int length = carport.calcFrontAndBackFasciaBoard()[1];
        int materialID;
        Material underFasciaBoard = null;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, length);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                // hvordan checker vi typen af et materiale?
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                length = rs.getInt("length");
                materialID = rs.getInt("material_id");
                underFasciaBoard = new Material(materialID, name, description, unit, quantity, length);
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
