package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.utilities.Calculator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper
{


    public static int createQueryInOrders(String carportID, int salesPersonID, String status, Timestamp orderPlaced,
                                          boolean orderPaid, int height, int width, boolean hasShed, String roofType, int accountID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO orders (carport_id, salesperson_id, status, " +
            "order_placed, order_paid, height, width, has_shed, roof_type, account_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?);";


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
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

            // Execute the query and retrieve the generated key
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected != 1)
            {
                throw new DatabaseException("kunne ikke oprette ...");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                return rs.getInt(1);
            } else
            {
                throw new DatabaseException("kunne ikke hente autogenereret ID");
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public static void createCarportInOrdersMaterials(int orderID, int materialID, int quantity, ConnectionPool pool) throws DatabaseException
    {

        String sql = "INSERT INTO orders_materials VALUES (?,?,?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql) )
        {
            int rowsAffected = ps.executeUpdate();
            ps.setInt(1, orderID);
            ps.setInt(2, materialID);
            ps.setInt(3, quantity);
            if (rowsAffected != 1)
            {
                throw new DatabaseException("kunne ikke oprette carport");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }

    }

    public static List<Order> getAllOrders(ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM public.orders;";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                String carportID = rs.getString("carport_id");
                int salespersonID = rs.getInt("salesperson_id");
                String status = rs.getString("status");
                int price = rs.getInt("price");
                int salesPrice = rs.getInt("sales_price");
                int coverageRatioPercentage = rs.getInt("coverage_ratio_percentage");
                Timestamp orderPlaced = rs.getTimestamp("order_placed");
                String roofType = rs.getString("roof_type");
                int accountID = rs.getInt("account_id");
                Order order = new Order(orderID, carportID, salespersonID, price, salesPrice, coverageRatioPercentage, status, orderPlaced, RoofType.FLAT, accountID);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching orders from the database", e.getMessage());
        }
        return orders;
    }

    //TODO: tilføj hasShed på denne
    /*public static List<Order> getAllOrdersWithoutSalesperson(ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM public.orders WHERE salesperson_id = null;";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                String carportID = rs.getString("carport_id");
                String status = rs.getString("status");
                int price = rs.getInt("price");
                int salesPrice = rs.getInt("sales_price");
                int coverageRatioPercentage = rs.getInt("coverage_ratio_percentage");
                Timestamp orderPlaced = rs.getTimestamp("order_placed");
                String roofType = rs.getString("roof_type");
                int accountID = rs.getInt("account_id");

                Order order = new Order(orderID, carportID, price, salesPrice, coverageRatioPercentage, status, orderPlaced, roofType, accountID);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching paid orders from the database", e.getMessage());
        }
        return orders;
    }*/


    //TODO: Test om den virker når salesperson står både som ? og null
    public static void updateSalespersonAssignedByOrderID(int salespersonID, int orderID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "UPDATE public.orders SET salesperson_id = ? WHERE salesperson_id = null AND order_id = ?;";


        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ps.setInt(1, salespersonID);
            ps.setInt(2, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Failed to update salesperson ID for the order with ID: " + orderID);
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Database error while updating salesperson data", e.getMessage());
        }
    }


    public static List<Order> getAllPaidOrders(ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM public.orders WHERE status = 'Betalt';";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                String carportID = rs.getString("carport_id");
                int salespersonID = rs.getInt("salesperson_id");
                String status = rs.getString("status");
                int price = rs.getInt("price");
                int salesPrice = rs.getInt("sales_price");
                int coverageRatioPercentage = rs.getInt("coverage_ratio_percentage");
                Timestamp orderPlaced = rs.getTimestamp("order_placed");
                String roofType = rs.getString("roof_type");
                int accountID = rs.getInt("account_id");
                Order order = new Order(orderID, carportID, salespersonID, price, salesPrice, coverageRatioPercentage, status, orderPlaced, RoofType.FLAT, accountID);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching paid orders from the database", e.getMessage());
        }
        return orders;
    }



    public static Order getOrderDetails(int orderID, Account account, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT " +
            "o.length " +
            "o.width " +
            "o.has_shed " +
            "o.roof_type " +
            "o.price " +
            "a.username " +
            "a.email " +
            "a.telephone " +
            "FROM orders as o " +
            "INNER JOIN accounts a ON o.account_id = a.account_id " +
            "ORDER BY ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
             ps.setInt(1, account.getAccountID());
             ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                boolean hasShed = rs.getBoolean("has_shed");
                String roofType = rs.getString("roof_type");
                int width = rs.getInt("width");
                int length = rs.getInt("height"); // CHANGE TO LENGTH
                int price = rs.getInt("price"); // NULL FOR NOW?
                if (rs.getInt("account_id") != 0)
                {
                    account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("email"), rs.getInt("telephone"));
                }
                return new Order(orderID, account);
            }


        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }



    public static Order getOrderByID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        // CHANGE HEIGHT TO LENGTH HERE
        String sql = "SELECT order_id, order_placed, height, width, has_shed, roof_type, status FROM orders WHERE order_id = ?";

        Timestamp orderPlaced;
        String status;
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int orderId = rs.getInt("order_id");
                orderPlaced = rs.getTimestamp("order_placed");
                int length = rs.getInt("height"); // CHANGE TO LENGTH
                int width = rs.getInt("width");
                boolean hasShed = rs.getBoolean("has_shed");
                String roofType = rs.getString("roof_type");
                status = rs.getString("status");
                return new Order(orderId, orderPlaced, status, length, width, hasShed, RoofType.FLAT);
            } else
            {
                throw new DatabaseException("Der findes ingen ordre med ID: " + orderID);
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static List<Order> showOrderHistory(String sortby, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT " +
            "o.order_id," +
            "o.status, " +
            "o.order_placed," +
            "o.order_paid," +
            "o.height," +
            "o.width," + // change to length
            "o.account_id," +
             "a.email," +
            "a.username," +
            "a.telephone " +
            "FROM orders o " +
            "LEFT JOIN accounts a ON o.account_id = a.account_id " +
            "ORDER BY ?;";

        int orderID;
        String status;
        Timestamp orderPlaced;
        boolean orderPaid;
        int height;
        int length;
        int accountID;

        String name;
        String email;
        int telephone;

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, sortby);
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while(rs.next())
            {
                orderID = rs.getInt("order_id");
                status = rs.getString("status");
                orderPlaced = rs.getTimestamp("order_placed");
                orderPaid = rs.getBoolean("order_paid");
                height = rs.getInt("height");
                length = rs.getInt("width"); // change to length (length is null)
                name = rs.getString("username");
                accountID = rs.getInt("account_id");
                email = rs.getString("email");
                telephone = rs.getInt("telephone");
                orders.add(new Order(orderID, status, orderPlaced, orderPaid, height, length,
                    new Account(accountID, name, email, telephone)));
            }
            return orders;
        } catch (SQLException e)
        {
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

    public static int getCoverageRatioByOrderID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        int coverageRatio = 0;

        String sql = "SELECT coverage_ratio_percentage FROM public.orders WHERE order_id = ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                coverageRatio = rs.getInt("coverage_ratio_percentage");
            }
            return coverageRatio;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public static int setDefaultSalesPriceByOrderID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        int pickListPrice = getPickListPriceByOrderID(orderID, pool);
        double coverageRatio = getCoverageRatioByOrderID(orderID, pool) / 100;
        int salesPrice = Calculator.calcSalesPrice(pickListPrice, coverageRatio);

        //Dækningsgrad = Salgspris/Kostpris - 1 * 100 for at få procent

        String sql = "UPDATE public.orders SET sales_price = ? WHERE order_id = ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, salesPrice);
            ps.setInt(3, orderID);
            return salesPrice;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    //TODO: TEST om dækningsgraden også bliver ændret
    public static void updateSalesPriceByOrderID(int newSalesPrice, int orderID, ConnectionPool pool) throws DatabaseException
    {
        int newCoverageRatio = ((newSalesPrice / (getPickListPriceByOrderID(orderID, pool))) - 1) * 100;
        //Dækningsgrad = Salgspris/Kostpris - 1 * 100 for at få procent

        String sql = "UPDATE public.orders SET sales_price = ? AND coverage_ratio_percentage = ? WHERE order_id = ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, newSalesPrice);
            ps.setInt(2, newCoverageRatio);
            ps.setInt(3, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Failed to update salesprice for order with ID: " + orderID);
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Database error while updating balance", e.getMessage());
        }

    }


    //TODO: TEST om salgsprisen også bliver ændret
    public static void updateCoverageRatioPercentageByOrderID(int newCoverageRatio, int orderID, ConnectionPool pool) throws DatabaseException
    {
        int newSalesPrice = ((newCoverageRatio / 100) * getPickListPriceByOrderID(orderID,pool)) + getPickListPriceByOrderID(orderID,pool);
        //Salgspris = ((dækningsgrad/100) * kostpris) + kostpris

        String sql = "UPDATE public.orders SET sales_price = ? AND coverage_ratio_percentage = ? WHERE order_id = ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, newSalesPrice);
            ps.setInt(2, newCoverageRatio);
            ps.setInt(3, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Failed to update coverage ratio for order with ID: " + orderID);
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Database error while updating balance", e.getMessage());
        }

    }

    public static List<Material> getBOM(int orderId, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT " +
            "m.material_id " +
            "m.name " +
            "m.description " +
            "m.unit " +
            "om.quantity " +
            "m.type " +
            "FROM materials m " +
            "LEFT JOIN orders_materials om ON m.material_id = om.material_id " +
            "WHERE m.order_id = ?" +
            "ORDER BY m.material_id"
            ;
        List<Material> materials = new ArrayList<>();

        try (Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                materials.add(new Material(
                    rs.getInt("material_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("unit"),
                    rs.getInt("quantity"),
                    rs.getString("type")
                    ));
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        return materials;
    }




    public static List<Order> getOrders()
    {
        return null;
    }

    public static void deleteOrderByID(int orderId)
    {

    }

    public static void updateOrderByUserID()
    {

    }


}
