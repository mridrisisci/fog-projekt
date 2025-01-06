package app.persistence;

import app.entities.Account;
import app.exceptions.DatabaseException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountMapper
{

    public static int createRecordInAddresses(int cityID, int postalCodeID, String address, ConnectionPool pool) throws DatabaseException
    {
        int addressID = checkRecordInAddresses(address, pool);
        if(addressID != 0)
        {
            return addressID;
        }

        String sql = "INSERT INTO addresses (city_id, postal_code_id, address) VALUES (?,?,?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, cityID);
            ps.setInt(2, postalCodeID);
            ps.setString(3, address);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettele af record i City");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                return rs.getInt(1);
            } else
            {
                throw new DatabaseException("Kunne ikke hente autogenereret id");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    private static int checkRecordInAddresses(String address, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM addresses WHERE address=?";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, address);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getInt("address_id");
            } else
            {
                throw new DatabaseException("Kunne ikke hente adresse fra databasen");
            }

        } catch (SQLException e)
        {
            throw new DatabaseException("Kunne ikke hente adresse fra databasen");
        }
    }

    public static void createSalesAccount(String role, String username, String email, String password, int telephone, int adressesID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO accounts (role, username, email, password, telephone, addresses_id) VALUES (?,?,?,?,?,?);";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection connection = pool.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, role);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, hashedPassword);
            ps.setInt(5, telephone);
            ps.setInt(6, adressesID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af konto");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static Account login(String email, String password, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM accounts WHERE email=?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String storedHashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, storedHashedPassword))
                {
                    int id = rs.getInt("account_id");
                    String username = rs.getString("username");
                    int telephone = rs.getInt("telephone");
                    String role = rs.getString("role");
                    return new Account(id, username, email, telephone, role);
                } else
                {
                    // Catching wrong passwords.
                    throw new DatabaseException("Kodeord matcher ikke. Prøv igen");
                }
            } else
            {
                // Catching wrong usernames.
                throw new DatabaseException("Brugernavn matcher ikke. Prøv igen");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }


    public static int createRecordInPostalCode(int postalCode, ConnectionPool pool) throws DatabaseException
    {
        int postalCodeID = checkRecordInPostalCode(postalCode, pool);
        if(postalCodeID != 0)
        {
            return postalCodeID;
        }

        String sql = "INSERT INTO postal_code (postal_code) VALUES (?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, postalCode);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af record i City");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                return rs.getInt(1);
            } else
            {
                throw new DatabaseException("Kunne ikke hente autogenerert id");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    private static int checkRecordInPostalCode(int postalCode, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM postal_code WHERE postal_code=?";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, postalCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getInt("postal_code_id");
            } else
            {
                throw new DatabaseException("Kunne ikke hente postkode fra databasen");
            }

        } catch (SQLException e)
        {
            throw new DatabaseException("Kunne ikke hente postkode fra databasen");
        }
    }

    public static int createRecordInCities(String city, ConnectionPool pool) throws DatabaseException
    {
        int cityID = checkRecordInCities(city, pool);
        if(cityID != 0)
        {
            return cityID;
        }

        String sql = "INSERT INTO cities (city) VALUES (?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, city);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af record i City");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                return rs.getInt(1);
            } else
            {
                throw new DatabaseException("Kunne ikke hente autogenerert ID");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    private static int checkRecordInCities(String city, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM cities WHERE city=?";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getInt("city_id");
            } else
            {
                throw new DatabaseException("Kunne ikke hente bynavn fra databasen");
            }

        } catch (SQLException e)
        {
            throw new DatabaseException("Kunne ikke hente bynavn fra databasen");
        }
    }

    public static int createCustomerAccount(String role, String username, int telephone, String email, int addressID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO accounts (role, username, telephone, email, addresses_id) VALUES (?,?,?,?,?)";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setString(1, role);
            ps.setString(2, username);
            ps.setInt(3, telephone);
            ps.setString(4, email);
            ps.setInt(5, addressID);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny konto...");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                return rs.getInt(1);
            } else
            {
                throw new DatabaseException("Kunne ikke hente autogenereret ID");
            }

        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static Account getAccountByOrderID(int orderID, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT o.order_id, a.account_id, a.username, a.telephone, a.role, a.email, a.password, o.account_id " +
                "FROM orders o " +
                "INNER JOIN accounts a ON o.account_id = a.account_id " +
                "WHERE o.order_id = ?";
        String username;
        String email;
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("account_id");
                username = rs.getString("username");
                email = rs.getString("email");
                int telephone = rs.getInt("telephone");
                String role = rs.getString("role");
                return new Account(id, username, email, telephone, role);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }
}
