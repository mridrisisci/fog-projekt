package app.persistence;

import app.entities.Account;
import app.exceptions.DatabaseException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
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
    public static List<Account> getAllAccounts(Account account)
    {
        return null;
    }
    public static void login()
    {

    }
}
