package app.persistence;

import app.entities.Material;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class MaterialMapper
{

    public static List<Material> createMaterialList(int materialQuantity, Material material, ConnectionPool pool) throws DatabaseException
    {
        // TODO: Muligvis en join her? eller alternativt en SQL der henter fra forbindelsestabellen?
        String sql = "SELECT name, unit, description, type FROM materials";

        String name;
        String unit;
        String description;
        String type = material.getType();




        try (Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            List<Material> materialList = new ArrayList<>();
            while(rs.next())
            {
                // TODO: Mangler en if-statement, der checker om material er == post
                // TODO: brug 'materialQuantity' (antal posts) til at bestemme antallet af materiale der hentes fra db
                // hvordan checker vi typen af et materiale?
                name = rs.getString("name");
                unit = rs.getString("unit");
                description = rs.getString("description");
                String materialType = rs.getString("type");
                int quantity = materialQuantity;
                if (materialType == type)
                {
                    while (quantity != materialQuantity)
                    {
                        materialList.add(new Material(name, unit, type, description, quantity));
                    }

                }
            }
            return materialList;
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
