package app.entities;

public class Account
{
    private  int accountID;
    private  String role;
    private final String USERNAME;
    private  String password;
    private final int TELEPHONE;
    private final String EMAIL;
    private  String address;

    public Account(String ADDRESS, String EMAIL, int TELEPHONE, String password, String USERNAME, String role, int accountID)
    {
        this.address = ADDRESS;
        this.EMAIL = EMAIL;
        this.TELEPHONE = TELEPHONE;
        this.password = password;
        this.USERNAME = USERNAME;
        this.role = role;
        this.accountID = accountID;
    }

    public Account(String username, String email, int telephone)
    {
        this.USERNAME = username;
        this.EMAIL = email;
        this.TELEPHONE = telephone;
    }

    public int getAccountID()
    {
        return accountID;
    }

    public String getRole()
    {
        return role;
    }

    public String getUSERNAME()
    {
        return USERNAME;
    }

    public String getPassword()
    {
        return password;
    }

    public int getTELEPHONE()
    {
        return TELEPHONE;
    }

    public String getEMAIL()
    {
        return EMAIL;
    }

    public String getAddress()
    {
        return address;
    }
}
