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
        app.get("/order/acceptoffer/{orderID}", ctx -> showOrderOnOfferPage(ctx, dBConnection));
        app.post("/acceptordecline", ctx -> acceptOrDeclineOffer(ctx, dBConnection) );
        app.post("/order/sendoffer/{id}", ctx -> sendOffer(ctx, dBConnection) );
        app.get("/order/details/{id}", ctx -> showOrderDetails(ctx, dBConnection) );
        app.get("/order/billOfMaterials/{id}", ctx -> billOfMaterials(ctx, dBConnection) );
        app.post("/updatesalesprice", ctx -> updateSalesPriceByOrderID(ctx, dBConnection));
    }

    private static void updateSalesPriceByOrderID(Context ctx, ConnectionPool pool)
    {
        String orderIDString = ctx.formParam("orderid");
        String newSalesPriceString = ctx.formParam("nysalgspris");
        int newSalesPrice = Integer.parseInt(Objects.requireNonNull(newSalesPriceString));
        int orderID = Integer.parseInt(Objects.requireNonNull(orderIDString));
        try
        {
            OrderMapper.updateSalesPriceByOrderID(newSalesPrice, orderID, pool);
            ctx.attribute("message", "Prisen er opdateret for den givne ordre");
            ctx.redirect("/orderhistory");

        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.redirect("orderhistory");
        }

    }

    private static void showOrderOnOfferPage(Context ctx, ConnectionPool pool)
    {
        String orderIDString = ctx.pathParam("orderID");
        int orderID = Integer.parseInt(orderIDString);

        try
        {
            Order order = OrderMapper.getOrderByID(Objects.requireNonNull(orderID), pool);
            Account account = AccountMapper.getAccountByOrderID(Integer.parseInt(Objects.requireNonNull(orderIDString)), pool);
            ctx.attribute("order", order);
            ctx.attribute("account", account);
            ctx.render("acceptoffer.html");

        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("acceptoffer.html");
        }
    }

    private static void acceptOrDeclineOffer(Context ctx, ConnectionPool pool)
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
                OrderMapper.updateOrderStatusAfterPayment(Integer.parseInt(Objects.requireNonNull(orderID)), StatusType.TILBUD_GODKENDT, pool);
                ctx.attribute("message", "Tak for at have handlet hos Fog - byggemarked.");
                ctx.redirect("/"); // opdater denne side ?½
            } else if ("reject".equals(action)) // if customer declines order, customer data is deleted
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
        int carportWidth = Integer.parseInt(Objects.requireNonNull(carportWidthString));
        int carportLength = Integer.parseInt(Objects.requireNonNull(carportLengthString));

        String trapeztag = ctx.formParam("chooseRoof");
        String specialWishes = ctx.formParam("specialWishes");

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
        String carportId = "CFU";
        boolean orderPaid = false;
        Order order;
        boolean hasShed = false;

        try
        {

            int cityID = AccountMapper.createRecordInCities(city, pool);
            int postalCodeID = AccountMapper.createRecordInPostalCode(postalCode, pool);
            int addressID = AccountMapper.createRecordInAddresses(cityID, postalCodeID, address, pool);
            int accountID = AccountMapper.createCustomerAccount(role, username, telephone, email, addressID, pool);

            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp orderPlaced = Timestamp.valueOf(localDateTime);
            int orderID = OrderMapper.createQueryInOrders(carportId, StatusType.AFVENTER_BEHANDLING.toString(), orderPlaced,
                orderPaid, carportLength, carportWidth, hasShed, RoofType.FLAT.toString(), accountID, pool);

            createCarport(orderID, ctx, pool);
            order = OrderMapper.getOrderByID(orderID, pool);

            // SVG
            order.setSvg(OrderMapper.getSVGFromDatabase(orderID, pool));

            SendGrid.sendReceipt(email,"Ordrebekræftelse", Objects.requireNonNull(order));
            SendGrid.notifySalesPersonOfNewOrder("sales.person.fog@gmail.com", "Ny bestilling af et pristilbud"); // INDSÆT SÆLGERMAIL KORREKT
            ctx.attribute("order", order);
            ctx.render("receipt.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
        } catch (NumberFormatException e)
        {
            throw new IllegalArgumentException(e);
        } catch (IOException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("receipt.html");
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

            // Picklist
            List<Material> pickList = MaterialMapper.createPickList(carport, pool);
            MaterialMapper.insertPickListInDB(pickList, carport, pool);
            carport.setMaterialList(pickList);

            // Price
            OrderMapper.updatePickListPrice(carport, pool);
            OrderMapper.setSalesPriceAndCoverageDefault(carport, pool);

            // SVG
            String svgStart = SVGCreation.generateSVGString(pickList, carport);
            String svg = SVGCreation.generateCarportSVGFromTemplate("", svgStart);
            // creates SVG in orders
            OrderMapper.updateSVG(orderID, svg, pool);


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
            String orderID = ctx.formParam("sendOfferID"); // to remove order from DB
            String accountID = ctx.formParam("accountid"); // to retrieve account from accounts
            Account account = AccountMapper.getAccountByOrderID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
            String email = account.getEmail();

            if ("send".equals(action))
            {
                Order order = OrderMapper.getOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
                OrderMapper.updateOrderStatusAfterPayment(Integer.parseInt(Objects.requireNonNull(orderID)), StatusType.TILBUD_SENDT, pool);
                SendGrid.sendOffer(email, "Pristilbud", order);
                ctx.attribute("message", "Dit pristilbud er sendt til kunden");
                showOrderHistory(ctx,pool);
            }
            else if ("afvis".equals(action))
            {
                OrderMapper.deleteOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
                ctx.attribute("message", "Bestilte pristilbud er afvist og kundens data er slettet fra systemet");
                showOrderHistory(ctx,pool);
            }

        } catch (DatabaseException | IOException e )
        {
            System.out.println(e.getMessage() + " DB-exception");
            ctx.attribute("message", e.getMessage());
            showOrderHistory(ctx, pool);
        }
    }

    private static void showOrderDetails(Context ctx, ConnectionPool pool)
    {
        try
        {
            String orderID = ctx.pathParam("id");
            int orderIDInt = Integer.parseInt(Objects.requireNonNull(orderID));
            List<Order> orderDetails;
            orderDetails = OrderMapper.getOrderDetails(orderIDInt, pool);

            // get Account object from orderdetails
            Order accountIndex = Objects.requireNonNull(orderDetails).getLast();
            Account account = accountIndex.getAccount();

            Order order = orderDetails.removeLast(); // removes Account object
            String orderSVG = OrderMapper.getSVGFromDatabase(orderIDInt, pool);
            order.setSvg(orderSVG);
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
            String sortBy = ctx.formParam("query");
            try
            {
                if (!(sortBy == null || sortBy.equals("username") || sortBy.equals("status") || sortBy.equals("date_placed") || sortBy.equals("order_paid")))
                {
                    sortBy = "order_id";
                }
                orders = OrderMapper.getOrderHistory(sortBy, pool);
            } catch (DatabaseException e)
            {
                ctx.attribute("message", e.getMessage());
            }
            // Render Thymeleaf-skabelonen
            ctx.attribute("orders", orders);
            ctx.render("/orderhistory.html");
        }

    }

    private static void billOfMaterials(Context ctx, ConnectionPool pool)
    {
        try
        {
            String orderID = ctx.pathParam("id");
            Order orderDetails = OrderMapper.getOrderByID(Integer.parseInt(Objects.requireNonNull(orderID)), pool);
            List<Material> pickList = MaterialMapper.getPickList(Integer.parseInt(Objects.requireNonNull(orderID)), pool);

            ctx.attribute("pickList", pickList);
            ctx.attribute("orderDetails", orderDetails);
            ctx.render("billOfMaterials.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            showOrderHistory(ctx, pool);
        }

    }
}
