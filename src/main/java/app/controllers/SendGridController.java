package app.controllers;

import app.utilities.SendGrid;
import io.javalin.Javalin;

import java.io.IOException;

public class SendGridController
{

    public static void addRoutes(Javalin app)
    {
        //app.post("/sendemail", ctx -> SendGrid.sendEmail(customerEmail, subjectLine, salespersonEmail, "2100", body) );
        app.get("/sendemail", ctx -> ctx.render("send_email.html"));

    }





}
