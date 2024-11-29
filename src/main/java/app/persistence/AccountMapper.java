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
                throw new DatabaseException("Kunne ikke hente autogenerert id");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }

    }

    public static Account login(String username, String password, ConnectionPool pool) throws DatabaseException
    {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String storedHashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, storedHashedPassword))
                {
                    int id = rs.getInt("user_id");
                    String role = rs.getString("role");
                    return new Account(id, username, role);
                }
                else
                {
                    // Catching wrong passwords.
                    throw new DatabaseException("Kodeord matcher ikke. Prøv igen");
                }
            }
            else
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

    public static int createRecordInCities(String city, ConnectionPool pool) throws DatabaseException
    {

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
                throw new DatabaseException("Kunne ikke hente autogenerert id");
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }

    }

    public static int createAccount(String role, String username, int telephone, String email, int addressID, ConnectionPool pool) throws DatabaseException
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
                throw new DatabaseException("kunne ikke hente autogenereret ID");
            }


        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static void addAccount()
    {

    }
    public static void deleteAccount(Account account)
    {

    }
    public static void updateAccount(Account account)
    {

    }
    public static Account getAccountByID(Account account)
    {
        return null;
    }
    public static List<Account> getAllCustomers(ConnectionPool pool) throws DatabaseException
    {

        String sql = "SELECT username, email, telephone FROM accounts WHERE role = 'customer'";
        String username;
        String email;
        int telephone;

        try (Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql))
        {
            List<Account> accounts = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                username = rs.getString("username");
                email = rs.getString("email");
                telephone = rs.getInt("telephone");
                accounts.add(new Account(username, email, telephone) );
                return accounts;
            }
        return null;
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }
    public static void login()
    {

    }
}
