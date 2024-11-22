package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class OrderController
{

    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
        app.get("/", ctx -> ctx.render("index.html") );
        app.get("/createquery", ctx -> ctx.render("createquery.html") );
        app.post("/customcarportquery", ctx -> createQuery(ctx, dBConnection) );
    }

    private static void createQuery(Context ctx, ConnectionPool dbConnection)
    {

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
