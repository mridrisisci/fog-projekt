package app.controllers;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderController
{

    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/createquery", ctx -> ctx.render("createquery.html"));
        app.post("/customcarportquery", ctx -> createQuery(ctx, dBConnection));
    }

    private static void createQuery(Context ctx, ConnectionPool dbConnection)
    {
        String carportWidth = ctx.formParam("chooseWidth");
        String carportHeight = ctx.formParam("chooseHeight");
        String trapeztag = ctx.formParam("chooseRoof");
        String shedWidth = ctx.formParam("chooseShedWidth");
        String shedLength = ctx.formParam("chooseShedLength");
        String specialRequests = ctx.formParam("specialWishes");

        String name = ctx.formParam("customername");
        String address = ctx.formParam("chooseAddress");
        String postalCode = ctx.formParam("choosePostalCode");
        String city = ctx.formParam("chooseCity");
        String phone = ctx.formParam("choosePhoneNumber");
        String email = ctx.formParam("chooseEmail");
        String consent = ctx.formParam("chooseConsent");


        // TODO: check at form-parameetrene ikke er null
        // TODO: validere kundens telefon nummer
        validatePhoneNumber(ctx, "choosePhoneNumber");
        // TODO: validere kundens email
        validateEmail(ctx, "chooseEmail");
        // TODO: validere kundens postnummer
        validatePostalCode(ctx, "choosePostalCode");
        // TODO: Oprette kundens ordre i 'orders'tabellen


        List<Order> carportOrder = new ArrayList<>();

        List<String> params = new ArrayList<>(Arrays.asList(
            carportWidth, carportHeight, trapeztag, shedWidth, shedLength, specialRequests,
            name, address, postalCode, city, phone, email, consent
        ));




    }

    private static boolean validatePostalCode(Context ctx, String postalCode)
    {
        int p = Integer.parseInt(postalCode); // does it need to be parsed ?

        if (postalCode.length() != 4 || postalCode.length() < 4 || postalCode.length() > 4)
        {
            return false;
        }
        if (postalCode.length() == 4)
        {
            return true;
        }
        return false;
    }

    private static boolean validateEmail(Context ctx, String email)
    {
        String chars = "@.";
        boolean hasChar = email.chars().anyMatch(ch -> chars.indexOf(ch) >= 0);
        if (!email.equals(hasChar))
        {
            return false;
        }
        return false;

    }

    private static void validatePostalCode()
    {

    }

    private static boolean validatePhoneNumber(Context ctx, String number)
    {
        String numbers = "1234567890";
        boolean hasNumber = number.chars().anyMatch(ch -> numbers.indexOf(ch) >= 0);

        if (number.length() < 8)
        {
            ctx.attribute("message", "Dit telefonnummer er ugyldigt");
            return false;
        } else if (number.length() == 8 && hasNumber)
        {
            return true;
        }
        return false;
    }
    private static boolean validateParams(String... params)
    {
        for (String p : params)
        {
            if (p.isEmpty())
            {
                return false;
            }
        }
        return true;
    }


    private static boolean validateOrderIsPaid()
    {
        return false;
    }

    private static void requestPaymentByID()
    {
    }

    private static void confirmPaymentByID()
    {
    }

    private static void sendBOM()
    {
    }

    private static void sendPickList()
    {
    }

    private static void sendInvoice()
    {
    }

}
