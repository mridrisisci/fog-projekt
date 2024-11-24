package app.controllers;

import app.entities.Account;
import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        String roofType = ctx.formParam("chooseRoof");
        String specialRequests = ctx.formParam("specialWishes");

        String shedWidthString = ctx.formParam("chooseShedWidth");
        Integer shedWidth = Integer.parseInt(shedWidthString);
        String shedLengthString = ctx.formParam("chooseShedLength");
        Integer shedLength = Integer.parseInt(shedLengthString);

        // customer info
        String name = ctx.formParam("customername");
        String address = ctx.formParam("chooseAddress");
        String postalCode = ctx.formParam("choosePostalCode");
        String city = ctx.formParam("chooseCity");
        String phone = ctx.formParam("choosePhoneNumber");
        String email = ctx.formParam("chooseEmail");
        String consent = ctx.formParam("chooseConsent");


        // TODO: check at form-parameetrene ikke er null

        validatePhoneNumber(ctx, "choosePhoneNumber");
        validateEmail(ctx, "chooseEmail");
        validatePostalCode(ctx, "choosePostalCode");

        // TODO: Oprette kundens ordre i 'orders'tabellen

        int customerId = 0;
        int carportId = 0;
        int salesPersonId = 0;

        boolean hasShed = false;
        // TODO: check at kunden har valgt mål til redskabsrummet

        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp orderPlaced = Timestamp.valueOf(localDateTime);

        Account account = ctx.sessionAttribute("currentAccount");
        AccountController.createAccount(1);

        OrderMapper.createQueryInOrders(customerId, carportId, salesPersonId, status, orderPlaced,
                                      carportHeight, carportWidth ,hasShed, roofType, accountId);



        List<Order> carportOrder = new ArrayList<>();

        List<String> params = new ArrayList<>(Arrays.asList(
            carportWidth, carportHeight, roofType, shedWidth, shedLength, specialRequests,
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
