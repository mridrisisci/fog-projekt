package app.controllers;

import app.entities.Carport;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import app.persistence.OrderMapper;
import app.utilities.Calculator;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;

public class MaterialController
{

    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
        app.get("/test.html", ctx -> ctx.render("test.html") );
    }


    public static int[] getLengthAndWidth(Context ctx, ConnectionPool pool){
        OrderMapper orderMapper = new OrderMapper();
        //"new int[2]" er bare lavet så lenghtAndWidth = OrderMapper.getLength... ikke er færdig
        int[] lengthAndWidth = new int[2];
        // int[] lengthAndWidth = OrderMapper.getLengthAndWidthByOrderID(order_ID, pool);
        // kaste højde + bredde videre til calculator
        // Calculator.calcBeams(intx, inty)
        int quantity;
        int length;

        return lengthAndWidth;
    }

    // Eks. på CalcPosts()
    //TODO:Metoden er udkommenteret da den fejler - skal fikses

    public static int getNumberOfPosts(Carport carport, Context ctx, ConnectionPool pool)
    {
        // TODO: Der skal nok testes lidt på tallene så vi sørger for mindst mulig spild af materiale
        // TODO: Evt. laves en unit test her lidt ala. det Andrës gruppe lavede i går ( calcOptimalWood() )
        final int POSTS = Calculator.calcPosts(carport);

        try
        {
            MaterialMapper.createMaterialList(POSTS, pool);

        } catch (DatabaseException e)
        {
            System.out.println(e.getMessage());
            ctx.attribute("message", e.getMessage());
        }
        return POSTS;


        //return POSTS; // antal posts, der skal hentes vha. mapperen
    }




    // Eks. på CalcPosts()
    public static void calcPosts(int height, int width, Context ctx, ConnectionPool pool)
    {
        int[] quantity = new int[2];

        quantity[0] = height;
        quantity[1] = width;

        // TODO: Der skal nok testes lidt på tallene så vi sørger for mindst mulig spild af materiale
        // TODO: Evt. laves en unit test her lidt ala. det Andrës gruppe lavede i går ( calcOptimalWood() )
        final int POSTS = 4;
        Material type  = new Material("post");
        List<Material> materialList = new ArrayList<>();

        try
        {
            materialList = MaterialMapper.createMaterialList(POSTS, type, pool);

        } catch (DatabaseException e)
        {
            System.out.println(e.getMessage());
            ctx.attribute("message", e.getMessage());
        }
        ctx.attribute("materialList", materialList);
        ctx.render("test.html");


        //return POSTS; // antal posts, der skal hentes vha. mapperen
    }





}
