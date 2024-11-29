package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderController
{

    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/createquery", ctx -> ctx.render("createquery.html"));
        app.post("/createquery", ctx -> createQuery(ctx, dBConnection));
    }

    private static void createQuery(Context ctx, ConnectionPool dbConnection)
    {
        String carportWidthString = ctx.formParam("chooseWidth");
        String carportHeightString = ctx.formParam("chooseHeight");
        int carportWidth = Integer.parseInt(carportWidthString);
        int carportHeight = Integer.parseInt(carportHeightString);

        String trapeztag = ctx.formParam("chooseRoof");
        String specialWishes = ctx.formParam("specialWishes");

        String shedWidthString = ctx.formParam("chooseShedWidth");
        Integer shedWidth = Integer.parseInt(shedWidthString);
        String shedLengthString = ctx.formParam("chooseShedLength");
        Integer shedLength = Integer.parseInt(shedLengthString);

        // customer info
        String username = ctx.formParam("customerName");
        String address = ctx.formParam("chooseAdress");
        String postalCode = ctx.formParam("choosePostalCode");
        String city = ctx.formParam("chooseCity");
        String telephoneString = ctx.formParam("choosePhoneNumber"); //
        int telephone = Integer.parseInt(telephoneString);
        String email = ctx.formParam("chooseEmail");
        String consent = ctx.formParam("chooseConsent");
        String role = "customer";


        // TODO: check at form-parameetrene ikke er null


        //validatePhoneNumber(ctx, "choosePhoneNumber");
        //validateEmail(ctx, "chooseEmail");
        //validatePostalCode(ctx, "choosePostalCode");

        // TODO: Oprette kundens ordre i 'orders'tabellen
        int customerId = 0;
        int carportId = 0;
        int salesPersonId = 0;
        OrderStatus status = OrderStatus.NOT_PAID;
        RoofType roofType = RoofType.FLAT;
        String description = "";

        // TODO: check at kunden har valgt mål til redskabsrummet
        boolean hasShed = true;

        try
        {
            Account account = ctx.sessionAttribute("currentAccount");
            int accountID = AccountMapper.createAccount(role, username, telephone, email, dbConnection);
            ctx.attribute("message", "Din kundekonto er nu oprettet og dit pristilbud er sendt.");



            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp orderPlaced = Timestamp.valueOf(localDateTime);

            int orderID = OrderMapper.createQueryInOrders(accountID, carportId, salesPersonId, status.NOT_PAID.toString(), orderPlaced,
                    carportHeight, carportWidth, hasShed, roofType.toString(), dbConnection);

            // laver et carport objekt
            createCarport(orderID, ctx, dbConnection);

            ctx.render("createquery.html");

        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
        } catch (NumberFormatException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    //TODO: metode der skal lave et carport objekt, så vores calculator kan modtage længde og bredde
    // det skal bruges i vores mappers som så kan return et materiale object (som også har et antal på sig)
    // vores mappers laver så styklisten som vi så kan beregne en pris på hele carporten

    private static void createCarport(int orderID, Context ctx, ConnectionPool dbConnection)
    {
        //instantiere carport objekt med data fra formular
        try{
            Carport carport = OrderMapper.getCarportByOrderID(orderID, dbConnection);

        } catch (DatabaseException e){
            ctx.attribute("message", e.getMessage());
        }


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
