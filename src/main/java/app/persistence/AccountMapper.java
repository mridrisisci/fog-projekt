package app.persistence;

import app.entities.Account;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.List;

public class AccountMapper
{

    public static int createAccount(String role, String username, int telephone, String email, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO accounts (role, username, telephone, email) VALUES (?,?,?,?)";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS))
        {

            ps.setString(1, role);
            ps.setString(2, username);
            ps.setInt(3, telephone);
            ps.setString(4, email);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny konto...");
            }
            System.out.println("db opdateret");

            // retrieves PK fra DB
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                // returns generated account_id
                return rs.getInt(1);
            } else
            {
                throw new DatabaseException("Kunne ikke hente autogenereret account_id...");
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
