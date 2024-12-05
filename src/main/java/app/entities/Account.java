package app.entities;

public class Account
{
    private int accountID;
    private String role;
    private String username;
    private String password;
    private int telephone;
    private String email;
    private String address;

    public Account(String username, String email)
    { // used for send tilbud-route
        this.username = username;
        this.email = email;
    }

    public Account(String username, String email, int telephone)
    {
        this.username = username;
        this.email = email;
        this.telephone = telephone;
    }


    public Account(int accountID, String email, int telephone)
    { // used to log in
        this.accountID = accountID;
        this.username = email;
        this.telephone = telephone;
    }

    public Account(int accountID, String username, String email, int telephone, String role)
    { // this constructor is used for 'orderhistory' mapper method
        this.accountID = accountID;
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
    }

    public int getAccountID()
    {
        return accountID;
    }

    public String getRole()
    {
        return role;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public int getTelephone()
    {
        return telephone;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }
}
