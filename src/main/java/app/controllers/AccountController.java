package app.controllers;

import app.entities.Account;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AccountController
{
    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
        app.get("/seequeries", ctx -> ctx.render("seequeries.html") );

    }

    private static void getAllAccounts(Context ctx, ConnectionPool pool)
    {
        try
        {
            List<Account> allCustomers = AccountMapper.getAllCustomers(pool);
            ctx.attribute("allcustomers", allCustomers);
            ctx.render("seequeries.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage())
            ctx.render("seequeries.html");
        }
    }


}
