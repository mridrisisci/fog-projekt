package app.persistence;
import app.entities.Order;
import app.entities.OrderStatus;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderMapper
{

    public static void createQueryInOrders(int customerID, int carportID, int salesPersonID, String status, Timestamp orderPlaced,
                                           int height, int width, boolean hasShed, String roofType, int accountID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO orders (customer_id, carport_id, salesperson_id, status, " +
            "order_placed, height, width, has_shed, roof_type, account_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?);";



        try (Connection connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, customerID);
            ps.setInt(2, carportID);
            ps.setInt(3, salesPersonID);
            ps.setString(4, status);
            ps.setTimestamp(5, orderPlaced);
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
