package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.*;
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

    public static void main(String[] args) {

        // Initialize Javalin and configure the web server
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7072);


        OrderController.addRoutes(app, dBConnection);
        MaterialController.addRoutes(app, dBConnection);
        AccountController.addRoutes(app, dBConnection);
        SendGridController.addRoutes(app);


        app.get("/svg/carport", SVGController::generateSVG);


    }
}