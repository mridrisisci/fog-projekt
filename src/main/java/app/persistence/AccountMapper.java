package app.persistence;

import app.entities.Account;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.List;

public class AccountMapper
{

    public static int createAccount(String role, int telephone, String email, ConnectionPool pool) throws DatabaseException
    {
        String sql = "INSERT INTO accounts (role, telephone, email) VALUES (?,?,?)";
        try (
                Connection connection = pool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, role);
            ps.setInt(2, telephone);
            ps.setString(3, email);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny konto...");
            }
            {
                throw new DatabaseException("Kunne ikke hente autogenereret account_id...");
            }

        } catch (SQLException e)
        {
            String msg = "Der er sket en fejl, prøv igen";
            throw new DatabaseException(msg, e.getMessage());
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
