package app.entities;

public class Account
{
    private final int ACCOUNT_ID;
    private final String ROLE;
    private final String USERNAME;
    private final String PASSWORD;
    private final int TELEPHONE;
    private final String EMAIL;
    private final String ADDRESS;

    public Account(String ADDRESS, String EMAIL, int TELEPHONE, String PASSWORD, String USERNAME, String ROLE, int ACCOUNT_ID)
    {
        this.ADDRESS = ADDRESS;
        this.EMAIL = EMAIL;
        this.TELEPHONE = TELEPHONE;
        this.PASSWORD = PASSWORD;
        this.USERNAME = USERNAME;
        this.ROLE = ROLE;
        this.ACCOUNT_ID = ACCOUNT_ID;
    }

    public int getACCOUNT_ID()
    {
        return ACCOUNT_ID;
    }

    public String getROLE()
    {
        return ROLE;
    }

    public String getUSERNAME()
    {
        return USERNAME;
    }

    public String getPASSWORD()
    {
        return PASSWORD;
    }

    public int getTELEPHONE()
    {
        return TELEPHONE;
    }

    public String getEMAIL()
    {
        return EMAIL;
    }

    public String getADDRESS()
    {
        return ADDRESS;
    }
}
