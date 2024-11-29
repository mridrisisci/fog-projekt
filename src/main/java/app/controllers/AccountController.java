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
        app.get("/login", ctx -> ctx.render("login.html") );
        app.post("/login", ctx -> doLogin(ctx, dBConnection));

    }

    public static void doLogin(Context ctx, ConnectionPool pool)
    {
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");
        if (name !=null )
        {
            name = name.toLowerCase(); // Avoiding potential nullPointerExceptions
        }
        try
        {
            Account account = AccountMapper.login(name, password, pool);
            ctx.sessionAttribute("currentUser", account);
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
        }
        //CupcakeController.showFrontpage(ctx, dbConnection);

    }

    public static void doLogout(Context ctx)
    {
        //Invalidate session
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
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
            ctx.attribute("message", e.getMessage());
            ctx.render("seequeries.html");
        }
    }


}
