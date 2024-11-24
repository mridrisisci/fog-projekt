package app.controllers;

import app.entities.Account;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AccountController
{
    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
    }

    public static int createAccount(Context ctx, String role, int telephone, String email, Account account, ConnectionPool pool)
    {
        int accountID = 0;

        try
        {
            AccountMapper.createAccount(role, telephone, email, account, pool);
        } catch (DatabaseException e)
        {
            ctx.attribute("message", "fejl ved oprettelse af konto");
        }
        return accountID;

    }

    private static Account login(){
        return null;
    }

    private static void logout()
    {

    }
}
