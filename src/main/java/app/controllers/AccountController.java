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
        app.get("/seequeries", ctx -> ctx.render("orderhistory.html") );
        app.get("/login", ctx -> ctx.render("login.html") );
        app.post("/login", ctx -> doLogin(ctx, dBConnection));
        app.get("/createaccount", ctx -> ctx.render("createaccount.html") );
        app.post("/createaccount", ctx -> createSalesAccount(ctx, dBConnection) );
        app.post("/logout", ctx -> doLogout(ctx));

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
    {
        String username = ctx.formParam("chooseName");
        String role = "salesperson";
        String email = ctx.formParam("chooseEmail");
        String telephoneString = ctx.formParam("choosePhoneNumber");
        int telephone = Integer.parseInt(telephoneString);
        String password = ctx.formParam("choosePassword");
        String confirmPassword = ctx.formParam("confirmPassword");

        String zipString = ctx.formParam("choosePostalCode");
        int zip = Integer.parseInt(zipString);
        String city = ctx.formParam("chooseCity");
        String address = ctx.formParam("chooseAddress"); // chooseAdress

        // Simple email check
        if (email == null || !email.contains("@") || !email.contains("."))
        {
            ctx.attribute("message", "Venligst indtast en gyldig e-mail-adresse.");
            ctx.render("createaccount.html");
        } else if (password == null || confirmPassword == null)
        {
            ctx.attribute("message", "Venligst udfyld dit kodeord i begge felter.");
            ctx.render("createaccount.html");
        } else if (passwordCheck(ctx, password, confirmPassword))
        {
            try
            {
                email = email.toLowerCase();
                int cityID = AccountMapper.createRecordInCities(city, pool);
                int zipID = AccountMapper.createRecordInPostalCode(zip, pool);
                int addressID = AccountMapper.createRecordInAddresses(cityID, zipID, address, pool);
                AccountMapper.createSalesAccount(role, username, email, password, telephone, addressID, pool);
                ctx.attribute("message", "Din sælgerprofil er oprettet");
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
                ctx.render("createaccount.html");
            } catch (NumberFormatException e)
            {
                ctx.attribute("message", e.getMessage());
            }
        } else
        {
            ctx.render("createaccount.html");
        }
    }

    public static void doLogin(Context ctx, ConnectionPool pool)
    {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        if (email !=null )
        {
            email = email.toLowerCase(); // Avoiding potential nullPointerExceptions
        }
        try
        {
            Account account = AccountMapper.login(email, password, pool);
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
            ctx.render("orderhistory.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("orderhistory.html");
        }
    }


}
