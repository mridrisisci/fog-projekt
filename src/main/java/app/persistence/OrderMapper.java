package app.persistence;
import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper
{

    public static void createQueryInOrders(String carportID, int salesPersonID, String status, Timestamp orderPlaced,
                                           boolean orderPaid, int height, int width, boolean hasShed, String roofType, int accountID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO orders (carport_id, salesperson_id, status, " +
            "order_placed, order_paid, height, width, has_shed, roof_type, account_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?);";


        try (Connection connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, carportID);
            ps.setInt(2, salesPersonID);
            ps.setString(3, status);
            ps.setTimestamp(4, orderPlaced);
            ps.setBoolean(5, orderPaid);
            ps.setInt(6, height);
            ps.setInt(7, width);
            ps.setBoolean(8, hasShed);
            ps.setString(9, roofType);
            ps.setInt(10, accountID);

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

    public static Order getOrderByID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT order_placed, status, price, carport_id FROM orders WHERE order_id = ?";

        Timestamp orderPlaced;
        String status;
        String carportID;
        int price;
        try (Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                orderPlaced = rs.getTimestamp("order_placed");
                status = rs.getString("status");
                carportID = rs.getString("carport_id");
                price = rs.getInt("price");
                return new Order(orderID, orderPlaced, status, carportID, price);
            } else
            {
                throw new DatabaseException("Der findes ingen ordre med ID: " + orderID);
            }
        } catch (SQLException e)
        {
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
}
