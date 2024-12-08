package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;
import app.utilities.SendGrid;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderController
{

    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
        app.get("/createquery", ctx -> ctx.render("createquery.html"));
        app.post("/createquery", ctx -> createQuery(ctx, dBConnection));
        app.get("/", ctx -> showFrontpage(ctx, dBConnection));
        app.get("/orderhistory", ctx -> showOrderHistory(ctx, dBConnection));
        app.get("/order/acceptoffer/{id}", ctx -> showOrderOnOfferPage(ctx, dBConnection));
        app.post("/acceptordecline", ctx -> acceptOrtDeclineOffer(ctx, dBConnection) );
        app.post("/order/sendoffer/{id}", ctx -> sendOffer(ctx, dBConnection) );
        app.get("/order/details/{id}", ctx -> showOrderDetails(ctx, dBConnection) );
    }

    private static void showOrderOnOfferPage(Context ctx, ConnectionPool pool)
    {
        try
        {
            String orderID = ctx.pathParam("id");
            Order order = OrderMapper.getOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
            Account account = AccountMapper.getAccountByOrderID(Integer.parseInt(Objects.requireNonNull(orderID)),pool);
            ctx.attribute("order", order);
            ctx.attribute("account", account);
            ctx.render("acceptoffer.html");

        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("/order/{id}/acceptoffer");
        }
    }



    private static void acceptOrtDeclineOffer(Context ctx, ConnectionPool pool)
    {
        String action = ctx.formParam("action");
        String email = ctx.formParam("email");

        try
        {

            String orderID = ctx.formParam("offerid");
            Order order = OrderMapper.getOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
            if ("accept".equals(action)) // if customer pays for order, BOM is sent
            {
                SendGrid.sendBOM(email, "Stykliste", order);
                OrderMapper.setPaymentStatusToPaid(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
                OrderMapper.updateOrderStatusAfterPayment(Integer.parseInt(Objects.requireNonNull(orderID)), StatusType.TILDBUD_GODKENDT, pool);
                ctx.attribute("message", "Tak for at have handlet hos Fog - byggemarked.");
                ctx.redirect("/"); // opdater denne side ?½
            }
            else if ("reject".equals(action)) // if customer declines order, customer data is deleted
            {
                OrderMapper.deleteOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
                ctx.attribute("message", "Din ordre er slettet. ");
                ctx.redirect("/");
            }
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("/order/{id}/acceptoffer");
        } catch (IOException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("/order/{id}/acceptoffer");
        }
        ctx.render("acceptoffer.html");

    }

    public static void showFrontpage(Context ctx, ConnectionPool pool)
    {
        ctx.render("index.html");
    }


    private static void createQuery(Context ctx, ConnectionPool pool)
    {
        String carportLengthString = ctx.formParam("chooseLength");
        String carportWidthString = ctx.formParam("chooseWidth");
        int carportWidth = Integer.parseInt(Objects.requireNonNull(carportLengthString));
        int carportLength = Integer.parseInt(Objects.requireNonNull(carportWidthString));

        String trapeztag = ctx.formParam("chooseRoof");
        String specialWishes = ctx.formParam("specialWishes");

        String shedWidthString = ctx.formParam("chooseShedWidth");
        Integer shedWidth = Integer.parseInt(Objects.requireNonNull(shedWidthString));
        String shedLengthString = ctx.formParam("chooseShedLength");
        Integer shedLength = Integer.parseInt(Objects.requireNonNull(shedLengthString));

        // customer info
        String username = ctx.formParam("customerName");
        String address = ctx.formParam("chooseAdress");
        String postalCodeString = ctx.formParam("choosePostalCode");
        int postalCode = Integer.parseInt(Objects.requireNonNull(postalCodeString));
        String city = ctx.formParam("chooseCity");
        String telephoneString = ctx.formParam("choosePhoneNumber"); //
        int telephone = Integer.parseInt(Objects.requireNonNull(telephoneString));
        String email = ctx.formParam("chooseEmail");
        String consent = ctx.formParam("chooseConsent");
        String role = "customer";

        // TODO: Færdiggør valideringsmetoderne
        //validatePhoneNumber(ctx, "choosePhoneNumber");
        //validatePostalCode(ctx, "choosePostalCode");


        String carportId = "CFUS";
        int salesPersonId = 0;
        boolean orderPaid = false;
        Order order;
        boolean hasShed = true;

        try
        {

            int cityID = AccountMapper.createRecordInCities(city, pool);
            int postalCodeID = AccountMapper.createRecordInPostalCode(postalCode, pool);
            int addressID = AccountMapper.createRecordInAddresses(cityID, postalCodeID, address, pool);
            int accountID = AccountMapper.createCustomerAccount(role, username, telephone, email, addressID, pool);

            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp orderPlaced = Timestamp.valueOf(localDateTime);
            int orderID = OrderMapper.createQueryInOrders(carportId, salesPersonId, StatusType.AFVENTER_BEHANDLING.toString(), orderPlaced,
                orderPaid, carportLength, carportWidth, hasShed, RoofType.FLAT.toString(), accountID, pool);

            createCarport(orderID, ctx, pool);
            order = OrderMapper.getOrderByID(orderID, pool);
            SendGrid.sendReceipt(email,"Ordrebekræftelse", Objects.requireNonNull(order));
            ctx.attribute("order", order);
            ctx.render("kvittering.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
        } catch (NumberFormatException e)
        {
            throw new IllegalArgumentException(e);
        } catch (IOException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("kvittering.html");
        }
    }

    private static void createCarport(int orderID, Context ctx, ConnectionPool pool) throws DatabaseException
    {
        try
        {
            final int[] LENGTH_AND_WIDTH = OrderMapper.getLengthAndWidthByOrderID(orderID, pool);
            final int LENGTH = LENGTH_AND_WIDTH[0];
            final int WIDTH = LENGTH_AND_WIDTH[1];

            Carport carport = new Carport(orderID, LENGTH, WIDTH);
            List<Material> pickList = MaterialMapper.createPickList(carport, pool);
            carport.setMaterialList(pickList);
            OrderMapper.updatePickListPrice(carport, pool);
            OrderMapper.setDefaultSalesPriceAndCoverageRatioByOrderID(carport.getOrderID(), pool);


        } catch (DatabaseException e)
        {
            ctx.attribute("kunne ikke oprette carporten i forbindelsestabellen", e.getMessage());
        }
    }

    private static void sendOffer(Context ctx, ConnectionPool pool)
    {
        try
        {
            String action = ctx.formParam("action");
            String orderID = ctx.pathParam("id"); // to remove order from DB
            String accountID = ctx.formParam("accountid"); // to retrieve account from accounts
            Account account = AccountMapper.getAccountByOrderID(Integer.parseInt(Objects.requireNonNull(accountID)), pool);
            String email = account.getEmail();

            if ("send".equals(action))
            {
                Order order = OrderMapper.getOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
                SendGrid.sendOffer(email, "Pristilbud", order);
                OrderMapper.updateOrderStatusAfterPayment(Integer.parseInt(Objects.requireNonNull(orderID)), StatusType.TILBUD_SENDT, pool);
                ctx.attribute("message", "Dit pristilbud er sendt til kunden");
                showOrderHistory(ctx,pool);
            }
            else if ("afvis".equals(action))
            {
                OrderMapper.deleteOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
                ctx.attribute("message", "Bestilte pristilbud er afvist og kundens data er slettet fra systemet");
                showOrderHistory(ctx,pool);
            }

        } catch (IOException | DatabaseException e )
        {
            System.out.println(e.getMessage());
            ctx.attribute("message", e.getMessage());
            showOrderHistory(ctx, pool);
        }
    }

    private static void showOrderDetails(Context ctx, ConnectionPool pool)
    {
        try
        {
            String orderID = ctx.pathParam("id");
            List<Order> orderDetails;
            orderDetails = OrderMapper.getOrderDetails(Integer.parseInt(Objects.requireNonNull(orderID)), pool);

            // get Account object from orderdetails
            Order accountIndex = Objects.requireNonNull(orderDetails).getLast();
            Account account = accountIndex.getAccount();

            Order order = orderDetails.removeLast(); // removes Account object
            ctx.attribute("orderdetails", order);
            ctx.attribute("account", account);
            ctx.render("/orderdetails.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            showOrderHistory(ctx, pool);
        }

    }


    private static void showOrderHistory(Context ctx, ConnectionPool pool)
    {
        if (ctx.sessionAttribute("currentUser") == null)
        {
            ctx.attribute("message", "Du skal være logget ind for at se dette indhold.");
            ctx.render("login.html");
            return;
        }
        Account account = ctx.sessionAttribute("currentUser");
        String role = Objects.requireNonNull(account).getRole();
        String username = Objects.requireNonNull(account).getUsername();

        if ("salesperson".equals(role))
        {
            List<Order> orders = new ArrayList<>();
            String sortby = ctx.formParam("query");
            try
            {
                if (!(sortby == null || sortby.equals("username") || sortby.equals("status") || sortby.equals("date_placed") || sortby.equals("order_paid")))
                {
                    sortby = "order_id";
                }
                orders = OrderMapper.getOrderHistory(sortby, pool);
            } catch (DatabaseException e)
            {
                ctx.attribute("message", e.getMessage());
            }
            // Render Thymeleaf-skabelonen
            ctx.attribute("orders", orders);
            ctx.render("/orderhistory.html");
        }



    }


//TODO: metode der skal lave et carport objekt, så vores calculator kan modtage længde og bredde
// det skal bruges i vores mappers som så kan return et materiale object (som også har et antal på sig)
// vores mappers laver så styklisten som vi så kan beregne en pris på hele carporten




    private static Order getOrderByID(int orderID, Context ctx, ConnectionPool pool)
    {
        Order order;
        try
        {
            order = OrderMapper.getOrderByID(orderID, pool);
            return order;
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
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


    private static boolean validateOrderIsPaid()
    {
        return false;
    }





}
