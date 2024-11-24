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
        String carportWidth = ctx.formParam("carportwidth");
        String carportHeight = ctx.formParam("carportheight");
        String trapeztag = ctx.formParam("trapeztag");
        String shedWidth = ctx.formParam("shedwidth");
        String shedLength = ctx.formParam("shedlength");
        String specialRequests = ctx.formParam("specialrequests");

        String name = ctx.formParam("name");
        String address = ctx.formParam("address");
        String postalCode = ctx.formParam("postalcode");
        String city = ctx.formParam("city");
        String phone = ctx.formParam("phone");
        String email = ctx.formParam("email");
        String consent = ctx.formParam("consent");
        List<Order> carportOrder = new ArrayList<>();
        List<String> params = new ArrayList<>(Arrays.asList(
            carportWidth, carportHeight, trapeztag, shedWidth, shedLength, specialRequests,
            name, address, postalCode, city, phone, email, consent
        ));

/*
        try
        {


            if (params.size() == 12)
            {

                OrderMapper.createQueryInOrders(int customerID, int carportID, int salesPersonID, String status, Timestamp timeStamp,
                                                int carportHeight, int carportWidth, boolean hasShed, String roofType, int accountID, ConnectionPool pool);
            }

        } catch (SQLException e)
        {
            ctx.attribute("message", e.getMessage());
        }*/


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
