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
        app.get("/createaccount", ctx -> ctx.render("createaccount.html") );
        app.post("/createaccount", ctx -> createSalesAccount(ctx, dBConnection) );

    }

    private static boolean passwordCheck(Context ctx, String password, String confirmPassword)
    {
        String specialCharacters = "!#¤%&/()=?$§£€-_[]{}";
        String numbers = "1234567890";
        boolean hasSpecialChar = password.chars().anyMatch(ch -> specialCharacters.indexOf(ch) >= 0);
        boolean hasNumber = password.chars().anyMatch(ch -> numbers.indexOf(ch) >= 0);
        //Checks if the passwords match at all. Proceeds with code if true.
        if (!password.equals(confirmPassword))
        {
            ctx.attribute("message", "Kodeord matcher ikke. Prøv igen");
            return false;
        }
        // Check password length and character requirements
        if (password.length() >= 8 && hasNumber && hasSpecialChar)
        {
            return true; // Password meets all criteria
        }
        else
        {
            ctx.attribute("message", "Kodeordet følger ikke op til krav. Check venligst: <br>" +
                "Minimumslængde på 8 tegn, " +
                "Inkluder et tal i dit kodeord, " +
                "Inkluder et special tegn ");
        }
        return false;
    }

    private static void createSalesAccount(Context ctx, ConnectionPool pool)
    { //TODO: mangler at færdiggøres. Husk kryptering bruges også.
        String username = "Jimmy";
        String role = "salesperson";
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String confirmPassword = ctx.formParam("confirmpassword");
        // Simple email check
        if (email == null || !email.contains("@") || !email.contains("."))
        {
            ctx.attribute("message", "Venligst indtast en gyldig e-mail-adresse.");
            ctx.render("createuser.html");
        } else if (password == null || confirmPassword == null)
        {
            ctx.attribute("message", "Venligst udfyld dit kodeord i begge felter.");
            ctx.render("createuser.html");
        } else if (passwordCheck(ctx, password, confirmPassword))
        {
            try
            {
                email = email.toLowerCase();
                AccountMapper.createSalesAccount(role, username, email, password, pool);
                ctx.attribute("message", "du er nu oprettet");
                OrderController.showFrontpage(ctx, pool);
            } catch (DatabaseException e)
            {
                if (e.getMessage().contains("duplicate key value violates unique constraint"))
                {
                    ctx.attribute("message", "Brugernavnet er allerede i brug. Prøv et andet.");
                }
                else
                {
                    ctx.attribute("message", e.getMessage());
                }
                ctx.render("createuser.html");
            }
        } else
        {
            ctx.render("createuser.html");
        }
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
        OrderController.showFrontpage(ctx, pool);

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
            List<Account> allCustomers = AccountMapper.getAllCustomerQueries(pool);
            ctx.attribute("allcustomers", allCustomers);
            ctx.render("seequeries.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("seequeries.html");
        }
    }


}
