package app.persistence;

import app.entities.Carport;
import app.entities.Order;
import app.entities.OrderStatus;
import app.entities.RoofType;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.List;

public class OrderMapper
{

    public static void createQueryInOrders(int accountID, int carportID, int salesPersonID, String status, Timestamp orderPlaced,
                                           int height, int width, boolean hasShed, String roofType, ConnectionPool pool) throws DatabaseException
    {

        //TODO: fix hasShed
        String sql = "INSERT INTO orders (account_id, carport_id, salesperson_id, status, " +
                "order_placed, height, width, hasShed, roof_type, account_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?);";


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, accountID);
            ps.setInt(2, carportID);
            ps.setInt(3, salesPersonID);
            ps.setString(4, status);
            ps.setTimestamp(5, orderPlaced);
            ps.setInt(6, height);
            ps.setInt(7, width);
            ps.setBoolean(8, hasShed);
            ps.setString(9, roofType);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("fejl");
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }


    }

    public static Order getOrder()
    {
        return null;
    }

    public static List<Order> getOrders()
    {
        return null;
    }

    public static void deleteOrderByID()
    {

    }

    public static void updateOrderByUserID()
    {

    }

    public static void addOrderToDB()
    {

    }

    //TODO lav metode med return type Carport,
    // der henter data fra orders table i DB for et given orderID
    public static Carport getCarportByOrderID(int orderID, ConnectionPool connectionPool) throws DatabaseException
    {
        Carport carport = null;
        String sql = "";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                boolean hasShed = rs.getBoolean("hasShed");
                //TODO: lav String om til RoofType objekt
//                RoofType roofType = rs.getString("roof_type");
                carport = new Carport(roofType, hasShed, length, width);
            }

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("fejl");
            }
        } catch (SQLException | DatabaseException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

        return carport;
    }
}
