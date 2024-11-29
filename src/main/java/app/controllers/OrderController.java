package app.controllers;

import app.entities.Order;
import app.entities.RoofType;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
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


    private static void createQuery(Context ctx, ConnectionPool pool)
    {
        String carportLengthString = ctx.formParam("chooseLength");
        String carportWidthString = ctx.formParam("chooseWidth");
        int carportWidth = Integer.parseInt(carportLengthString);
        int carportHeight = Integer.parseInt(carportWidthString);

        String trapeztag = ctx.formParam("chooseRoof");
        String specialWishes = ctx.formParam("specialWishes");

        String shedWidthString = ctx.formParam("chooseShedWidth");
        Integer shedWidth = Integer.parseInt(shedWidthString);
        String shedLengthString = ctx.formParam("chooseShedLength");
        Integer shedLength = Integer.parseInt(shedLengthString);

        // customer info
        String username = ctx.formParam("customerName");
        String address = ctx.formParam("chooseAdress");
        String postalCodeString = ctx.formParam("choosePostalCode");
        int postalCode = Integer.parseInt(postalCodeString);
        String city = ctx.formParam("chooseCity");
        String telephoneString = ctx.formParam("choosePhoneNumber"); //
        int telephone = Integer.parseInt(telephoneString);
        String email = ctx.formParam("chooseEmail");
        String consent = ctx.formParam("chooseConsent");
        String role = "customer";

        // TODO: Færdiggør valideringsmetoderne
        //validatePhoneNumber(ctx, "choosePhoneNumber");
        //validateEmail(ctx, "chooseEmail");
        //validatePostalCode(ctx, "choosePostalCode");

        // TODO: tag stilling til validateParams()

        String carportId = "";
        int salesPersonId = 0;
        String status = "Under behandling";
        RoofType roofType = RoofType.FLAT;
        boolean orderPaid = false;
        Order order;


        boolean hasShed = true;
        // TODO: Lav carport-objekt efter ordren er oprettet
        try
        {

            int cityID = AccountMapper.createRecordInCities(city, pool);
            int postalCodeID = AccountMapper.createRecordInPostalCode(postalCode, pool);
            int addressID = AccountMapper.createRecordInAddresses(cityID, postalCodeID, address, pool);
            int accountID = AccountMapper.createAccount(role, username, telephone, email, addressID, pool);

            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp orderPlaced = Timestamp.valueOf(localDateTime);
            int orderID = OrderMapper.createQueryInOrders(carportId, salesPersonId, status, orderPlaced,
                orderPaid, carportHeight, carportWidth, hasShed, roofType.toString(), accountID, pool);

            createCarport(orderID, ctx, pool);
            order = getOrderByID(orderID, ctx, pool);
            ctx.attribute("order", order);
            ctx.render("kvittering.html");
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

        private static void createCarport(int orderID, Context ctx, ConnectionPool pool)
    {
        // hardcoded for at teste
        int materialID = 1;
        int quantity = 1;

        try
        {
            OrderMapper.createCarportInOrdersMaterials(orderID, materialID, quantity, pool);

        } catch (DatabaseException e)
        {
            ctx.attribute("kunne ikke oprette carporten i forbindelsestabellen", e.getMessage());
        }
    }

    private static Order getOrderByID(int orderID, Context ctx, ConnectionPool pool)
    {
        Order order;
        try
        {
            //String orderIDString = ctx.formParam("orderID");
            order = OrderMapper.getOrderByID(orderID, pool);
            return order;
            //ctx.attribute("order", order);
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("kvittering.html");
            return null;
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
