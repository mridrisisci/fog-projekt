package app.utilities;

public class SendGrid
{
    private static String API_KEY;
    private static String salespersonEmail;
    private static String customerEmail;
    private static String adminEmail;
    private static String subjectLine;
    private static String body;


    public String getAPI_KEY()
    {
        return API_KEY;
    }

    public String getSalespersonEmail()
    {
        return salespersonEmail;
    }

    public String getCustomerEmail()
    {
        return customerEmail;
    }

    public String getAdminEmail()
    {
        return adminEmail;
    }

    public String getSubjectLine()
    {
        return subjectLine;
    }

    public String getBody()
    {
        return body;
    }
}
