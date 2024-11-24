package app.controllers;

import app.entities.Account;
import app.persistence.AccountMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;

public class AccountController
{
    public static void addRoutes(Javalin app, ConnectionPool dBConnection)
    {
    }

    public static int createAccount(int accountId)
    {

        AccountMapper.createAccount();
     return accountId;
    }

    private static Account login(){
        return null;
    }

    private static void logout()
    {

    }
}
