package app.controllers;

import app.utilities.SendGrid;
import io.javalin.Javalin;

public class SendGridController
{

    private static String API_KEY = System.getenv("API_SEND_GRID");
    private static String salespersonEmail = "sales.person.fog@gmail.com";
    private static String customerEmail = "customer.fog.test@gmail.com";
    private static String adminEmail = "admin@example.com";
    private static String subjectLine = "Carport Order";
    private static String body;

    public static void addRoutes(Javalin app)
    {
        app.post("/sendemail", ctx -> SendGrid.sendEmail(customerEmail, subjectLine, salespersonEmail, "2100", body) );
        app.get("/sendemail", ctx -> ctx.render("send_email.html"));

    }


}
