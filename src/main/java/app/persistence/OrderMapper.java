package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.utilities.Calculator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper
{

    public static int createQueryInOrders(String carportID, String status, Timestamp orderPlaced, boolean orderPaid, int length, int width, boolean hasShed, String roofType, int accountID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO orders (carport_id, status, " +
                "order_placed, order_paid, length, width, has_shed, roof_type, account_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?);";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, carportID);
            ps.setString(2, status);
            ps.setTimestamp(3, orderPlaced);
            ps.setBoolean(4, orderPaid);
            ps.setInt(5, length);
            ps.setInt(6, width);
            ps.setBoolean(7, hasShed);
            ps.setString(8, roofType);
            ps.setInt(9, accountID);

            // Execute the query and retrieve the generated key
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
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

    public static List<Order> getOrderDetails(int orderID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT " +
                "o.length, " +
                "o.width, " +
                "o.has_shed, " +
                "o.roof_type, " +
                "o.price, " +
                "o.sales_price, " +
                "o.coverage_ratio_percentage," +
                "a.account_id, " +
                "a.username, " +
                "a.email, " +
                "a.telephone, " +
                "a.role " +
                "FROM orders as o " +
                "INNER JOIN accounts a ON o.account_id = a.account_id " +
                "WHERE o.order_id = ?";

        String shed = "";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            List<Order> orderDetails = new ArrayList<>();

            while (rs.next())
            {
                boolean hasShed = rs.getBoolean("has_shed");
                String roofType = rs.getString("roof_type");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                int price = rs.getInt("price");
                int salesPrice = rs.getInt("sales_price");
                int accountID = rs.getInt("account_id");
                String name = rs.getString("username");
                String email = rs.getString("email");
                int telephone = rs.getInt("telephone");
                String role = rs.getString("role");
                int coverageRatioPercentage = rs.getInt("coverage_ratio_percentage");
                orderDetails.add(new Order(orderID, width, length, shed, RoofType.FLAT, salesPrice, coverageRatioPercentage, price, new Account(accountID, name, email, telephone, role)));
            }
            return orderDetails;

        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static Order getOrderByID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT order_id, order_placed, carport_id, length, width, has_shed, roof_type, status, sales_price FROM orders WHERE order_id = ?";

        Timestamp orderPlaced;
        String status;
        String shed = "Uden skur";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int orderId = rs.getInt("order_id");
                orderPlaced = rs.getTimestamp("order_placed");
                int length = rs.getInt("length");
                int width = rs.getInt("width");
                String carportID = rs.getString("carport_id");
                boolean hasShed = rs.getBoolean("has_shed");
                String roofType = rs.getString("roof_type");
                status = rs.getString("status");
                int salesPrice = rs.getInt("sales_price");
                return new Order(orderId, orderPlaced, status, carportID, length, width, shed, salesPrice, RoofType.FLAT);
            } else
            {
                throw new DatabaseException("Der findes ingen ordre med ID: " + orderID);
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static List<Order> getOrderHistory(String sortBy, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT " +
                "o.order_id," +
                "o.status, " +
                "o.carport_id, " +
                "o.order_placed," +
                "o.order_paid," +
                "o.length," +
                "o.width," +
                "o.account_id," +
                "a.email," +
                "a.username," +
                "a.telephone, " +
                "a.role " +
                "FROM orders o " +
                "LEFT JOIN accounts a ON o.account_id = a.account_id ORDER BY order_id";


        int orderID;
        String status;
        Timestamp orderPlaced;
        boolean orderPaid;
        int width;
        int length;
        int accountID;
        String carportID = "";
        String name;
        String email;
        int telephone;
        String paymentStatus = "";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next())
            {
                orderID = rs.getInt("order_id");
                status = rs.getString("status");
                orderPlaced = rs.getTimestamp("order_placed");
                orderPaid = rs.getBoolean("order_paid");
                width = rs.getInt("width");
                length = rs.getInt("length");
                carportID = rs.getString("carport_id");
                name = rs.getString("username");
                accountID = rs.getInt("account_id");
                email = rs.getString("email");
                telephone = rs.getInt("telephone");
                String role = rs.getString("role");
                if (orderPaid)
                {
                    paymentStatus = "Ordren er betalt";
                } else
                {
                    paymentStatus = "Ordren afventer betaling";
                }
                orders.add(new Order(orderID, status.toString(), carportID, orderPlaced, paymentStatus, width, length,
                        new Account(accountID, name, email, telephone, role)));
            }
            return orders;
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static int[] getLengthAndWidthByOrderID(int order_ID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT account_id, length, width FROM orders WHERE order_id = ?;";

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

        String sql = "SELECT price FROM orders WHERE order_id = ?;";

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

    public static int setSalesPriceAndCoverageDefault(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        List<Material> pickList = MaterialMapper.createPickList(carport, pool);
        int pickListPrice = Calculator.calcPickListPrice(pickList);
        double coverageRatio = 0.35;
        int coverageRatioPercentage = (int) Math.ceil(coverageRatio * 100);
        int salesPrice = Calculator.calcSalesPrice(pickListPrice, coverageRatio);
        int orderID = carport.getOrderID();

        //Dækningsgrad = Salgspris/Kostpris - 1 * 100 for at få procent

        String sql = "UPDATE orders SET sales_price = ?, coverage_ratio_percentage = ? WHERE order_id = ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, salesPrice);
            ps.setInt(2, coverageRatioPercentage);
            ps.setInt(3, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Failed to set default sales price for order with ID: " + orderID);
            }

            return salesPrice;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public static int updatePickListPrice(Carport carport, ConnectionPool pool) throws DatabaseException
    {
        String sql = "UPDATE orders SET price = ? WHERE order_id = ?;";

        int orderID = carport.getOrderID();
        List<Material> pickList = carport.getMaterialList();
        int pickListPrice = Calculator.calcPickListPrice(pickList);

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, pickListPrice);
            ps.setInt(2, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Failed to update picklist price for order with ID: " + orderID);
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

        return pickListPrice;
    }

    public static void updateSalesPriceByOrderID(int newSalesPrice, int orderID, ConnectionPool pool) throws DatabaseException
    {
        double newCoverageRatio = ((double)newSalesPrice / (double)(getPickListPriceByOrderID(orderID, pool)))-1;
        double newCoverageRatioInPercentage = newCoverageRatio * 100;
        int newCoverageRatioInPercentageInInt = (int) Math.ceil(newCoverageRatioInPercentage);
        //Dækningsgrad = Salgspris/Kostpris - 1 * 100 for at få procent

        String sql = "UPDATE orders SET sales_price = ?, coverage_ratio_percentage = ? WHERE order_id = ?;";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, newSalesPrice);
            ps.setInt(2, newCoverageRatioInPercentageInInt);
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

    public static void updateOrderStatusAfterPayment(int orderID, StatusType status, ConnectionPool pool) throws DatabaseException
    {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, status.toString());
            ps.setInt(2, orderID);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1)
            {
                System.out.println("fejl");
                throw new DatabaseException("Kunne ikke slette ordren med ordre id: " + orderID);
            }


        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static void setPaymentStatusToPaid(int orderID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "UPDATE orders SET order_paid = 'true' WHERE order_id = ?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Kunne ikke slette ordren med ordre id: " + orderID);
            }


        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }

    }

    public static void deleteOrderByID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "DELETE FROM orders WHERE order_id = ? ";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Kunne ikke slette ordren med ordre id: " + orderID);
            }


        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }

    }

    public static void updateSVG(int orderID, String svg, ConnectionPool pool) throws DatabaseException
    {
        String sql = "UPDATE orders SET svg = ? WHERE order_id = ?";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, svg);
            ps.setInt(2, orderID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Kunne ikke opdatere SVG med ordre id: " + orderID);
            }

        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }

    }

    public static String getSVGFromDatabase(int orderID, ConnectionPool pool) throws DatabaseException
    {
        String svg = "";
        try (Connection connection = pool.getConnection())
        {
            String sql = "SELECT svg FROM orders WHERE order_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderID);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    svg = rs.getString("svg");
                }

            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Failed to retrieve SVG drawing from database", e);
        }
        return svg;
    }

}
