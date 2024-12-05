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

public class OrderController
{

    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
        app.get("/createquery", ctx -> ctx.render("createquery.html"));
        app.post("/createquery", ctx -> createQuery(ctx, dBConnection));
        app.get("/", ctx -> showFrontpage(ctx, dBConnection) );
        app.get("/seeallqueries", ctx -> seeAllQueries(ctx, dBConnection) );
        // TODO find ud af routing til denne her
        // app.get("/svg/carport", ctx -> generateSVG());
    }

    public static void showFrontpage(Context ctx, ConnectionPool pool)
    {
        ctx.render("index.html" );
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
            int accountID = AccountMapper.createCustomerAccount(role, username, telephone, email, addressID, pool);

            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp orderPlaced = Timestamp.valueOf(localDateTime);
            int orderID = OrderMapper.createQueryInOrders(carportId, salesPersonId, status, orderPlaced,
                orderPaid, carportHeight, carportWidth, hasShed, roofType.toString(), accountID, pool);

            createCarport(orderID, ctx, pool);
            generateSVG(orderID, ctx, pool);

            order = getOrderOnReceipt(orderID, ctx, pool);
            SendGrid.sendEmail(email);
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
            throw new RuntimeException(e);
        }
    }

    private static void seeAllQueries(Context ctx, ConnectionPool pool)
    {
        if (ctx.sessionAttribute("currentUser") == null)
        {
            ctx.attribute("message", "Du skal være logget ind for at se dette indhold.");
            ctx.render("login.html");
            return;
        }
        Account account = ctx.sessionAttribute("currentUser");
        String role = account.getRole();

        if (account.getRole().equals("salesperson"))
        {
            List<Order> orders = new ArrayList<>();
            String sortby = ctx.formParam("query");
            try
            {
                if (!(sortby==null || sortby.equals("username") || sortby.equals("status") || sortby.equals("date_placed") || sortby.equals("date_paid")))
                {
                    sortby = "order_id";
                }
                orders = OrderMapper.seeAllQueries(sortby, pool);
            }
            catch (DatabaseException e)
            {
                ctx.attribute("message","Noget gik galt. " + e.getMessage());
            }
            // Render Thymeleaf-skabelonen
            ctx.attribute("orders", orders);
            ctx.render("/requestedqueries.html");
        }



    }


    private static void generateSVG(int orderID, Context ctx, ConnectionPool pool) throws IOException, DatabaseException
    {

        List<Material> svgMatrialList = MaterialMapper.getSVGMaterialList(orderID, pool);
        int postLength = 0;
        int postWidth = 0;
        int beamLength = 0;
        int beamWidth = 0;
        int rafterLength = 0;
        int rafterWidth = 0;
        int fasciaBoardLength = 0;
        int fasciaBoardWidth = 0;
        int crossRafterLength = 0;
        int crossRafterWidth = 0;
        int quantityOfPosts = 0;
        int quantityOfBeams = 0;
        int quantityOfFaciaBoards = 0;

        for(Material mat: svgMatrialList){
            if(mat.getType().equals("Stolpe")){
                postLength = mat.getLength();
                postWidth = mat.getLength();
            }
            if(mat.getType().equals("Rem")){
                beamLength = mat.getLength();
                beamWidth = mat.getLength();
            }
            if(mat.getType().equals("Spær")){
                rafterLength = mat.getLength();
                rafterWidth = mat.getLength();
            }
            if(mat.getType().equals("Stern")){
                fasciaBoardLength = mat.getLength();
                fasciaBoardWidth = mat.getLength();
            }
            if(mat.getType().equals("Hulbånd")){
                crossRafterLength = mat.getLength();
                crossRafterWidth = mat.getLength();
            }
        }

        String svgContent = SVGCreation.loadSVGFromFile("SVG.xml");
        svgContent = SVGCreation.generateCarportSVG(
                postLength,
                postWidth,
                beamLength,
                beamWidth,
                rafterLength,
                rafterWidth,
                fasciaBoardLength,
                fasciaBoardWidth,
                crossRafterLength,
                crossRafterWidth,
                100,     // spacing
                quantityOfPosts,
                quantityOfBeams,
                quantityOfFaciaBoards
        );

        SVGCreation.saveSVGToFile("carport.svg", svgContent);
        //HTTP Request hvis vi vil have det
        ctx.result(svgContent).contentType("image/svg+xml");

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

private static Order getOrderOnReceipt(int orderID, Context ctx, ConnectionPool pool)
{
    Order order;
    try
    {
        order = OrderMapper.getOrderOnReceipt(orderID, pool);
        return order;
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
