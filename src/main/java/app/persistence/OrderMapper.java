package app.persistence;
import app.controllers.OrderController;
import app.entities.Material;
import app.entities.Order;
import app.entities.OrderStatus;
import app.exceptions.DatabaseException;
import app.utilities.Calculator;

import java.sql.*;
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

    public static int[] getLengthAndWidthByOrderID(int order_ID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT account_id, length, width FROM public.orders WHERE order_id = ?;";

        int[] carportLengthAndWidth = new int[2];
        int account_ID;
        int length;
        int width;


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, order_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                length = rs.getInt("length");
                width = rs.getInt("width");
                //Account_ID bruges ikke indtil videre, men kunne være brugbar senere hen
                account_ID = rs.getInt("account_id");
                carportLengthAndWidth[0] = length;
                carportLengthAndWidth[1] = width;
            }
            return carportLengthAndWidth;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public static int getPickListPriceByOrderID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        int pickListPrice = 0;

        String sql = "SELECT price FROM public.orders WHERE order_id = ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                pickListPrice = rs.getInt("price");
            }
            return pickListPrice;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public static int insertSalesPriceByOrderID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        int pickListPrice = getPickListPriceByOrderID(orderID, pool);
        int salesPrice = pickListPrice;

        String sql = "INSERT INTO public.orders(sales_price) WHERE order_id = ? VALUES(?);";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ps.setInt(2, salesPrice);
            return salesPrice;
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
