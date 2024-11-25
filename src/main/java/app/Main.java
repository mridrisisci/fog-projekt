package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.OrderController;
import app.persistence.ConnectionPool;
import app.utilities.SendGrid;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.io.IOException;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "carport";

    private static final ConnectionPool dBConnection = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    public boolean sendGrid(String to, String name, String email, String zip, String body) {
        try {
            SendGrid.sendEmail(to, name, email, zip, body);
            return true; // Email sent successfully
        } catch (IOException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            return false; // Email sending failed
        }
    }

    public static void main(String[] args) {
        Main mainApp = new Main();

        // Initialize Javalin and configure the web server
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7072);


        OrderController.addRoutes(app, dBConnection);

        app.get("/send-email", ctx -> ctx.render("send_email.html"));

        app.post("/send-email", ctx -> {
            String to = ctx.formParam("to");
            String name = ctx.formParam("name");
            String email = ctx.formParam("email");
            String zip = ctx.formParam("zip");
            String body = ctx.formParam("body");

            boolean result = mainApp.sendGrid(to, name, email, zip, body);
            if (result) {
                ctx.result("Email sent successfully!");
            } else {
                ctx.result("Failed to send email. Please try again.");
            }
        });
    }
}
