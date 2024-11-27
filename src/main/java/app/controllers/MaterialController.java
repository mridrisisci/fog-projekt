package app.controllers;

import app.entities.Carport;
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
    //TODO:Metoden er udkommenteret da den fejler - skal fikses
    /*
    public static int calcPosts(Carport carport, Context ctx, ConnectionPool pool)
    {

        // TODO: Der skal nok testes lidt på tallene så vi sørger for mindst mulig spild af materiale
        // TODO: Evt. laves en unit test her lidt ala. det Andrës gruppe lavede i går ( calcOptimalWood() )
        final int POSTS = carport.calcPosts(); //Skal hentes fra Carport calcPosts

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
    }*/


}
