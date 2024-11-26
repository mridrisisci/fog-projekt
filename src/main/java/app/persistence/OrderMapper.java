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

    public static void createQueryInOrders(String carportID, int salesPersonID, String status, Timestamp orderPlaced,
                                           int height, int width, boolean hasShed, String roofType, int accountID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO orders (carport_id, salesperson_id, status, " +
            "order_placed, height, width, has_shed, roof_type, account_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?);";


        try (Connection connection = pool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, carportID);
            ps.setInt(2, salesPersonID);
            ps.setString(3, status);
            ps.setTimestamp(4, orderPlaced);
            ps.setInt(5, height);
            ps.setInt(6, width);
            ps.setBoolean(7, hasShed);
            ps.setString(8, roofType);
            ps.setInt(9, accountID);

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
