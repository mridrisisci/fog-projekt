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

    public Account(int accountID, String email, String role)
    {
        this.accountID = accountID;
        this.username = email;
        this.role = role;
    }

    public Account(String username, String email, int telephone)
    {
        this.username = username;
        this.email = email;
        this.telephone = telephone;
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
