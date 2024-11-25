package app.exceptions;

import java.sql.SQLException;

public class DatabaseException extends Exception
{
    public DatabaseException(String errorMessage)
    {
        super(errorMessage);
    }

    public DatabaseException(String userMessage, String systemMessage)
    {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
        System.out.println("errorMessage: " + systemMessage);
    }

    public DatabaseException(String errorMessage, SQLException exception)
    {
        super(errorMessage, exception);
    }
}