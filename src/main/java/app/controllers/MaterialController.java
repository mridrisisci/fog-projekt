package app.controllers;

import app.entities.Carport;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;

public class MaterialController
{

    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
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

        try
        {
            MaterialMapper.createMaterialList(POSTS, type, pool);

        } catch (DatabaseException e)
        {
            System.out.println(e.getMessage());
            ctx.attribute("message", e.getMessage());
        }


        //return POSTS; // antal posts, der skal hentes vha. mapperen
    }





}
