package app.controllers;

import app.entities.Account;
import app.exceptions.DatabaseException;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AccountController
{
    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
    }


}
