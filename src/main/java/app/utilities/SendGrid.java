package app.utilities;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import io.javalin.http.Context;

import java.io.IOException;

public class SendGrid
{

    private static String API_KEY = System.getenv("API_SEND_GRID");
    private static String salespersonEmail = "sales.person.fog@gmail.com";
    private static String customerEmail = "customer.fog.test@gmail.com";
    private static String adminEmail = "admin@example.com";
    private static String subjectLine = "Carport Order";
    private static String body;

    public boolean sendGrid(String to, String name, String email, String zip, String body) {
        try {
            SendGrid.sendEmail(to, name, email, zip, body);
            return true; // Email sent successfully
        } catch (IOException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            return false; // Email sending failed
        }
    }

    public static void sendEmail(String to, String name, String email, String zip, String body) throws IOException
    {
        Email from = new Email("sales.person.fog@gmail.com"); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email("customer.fog.test@gmail.com")); // kode: fog12345
        personalization.addDynamicTemplateData("Name:", "Salesperson");
        personalization.addDynamicTemplateData("email", "sales.person.fog@gmail.com");
        personalization.addDynamicTemplateData("zip", "2100");
        personalization.addDynamicTemplateData("body", body);
        mail.addPersonalization(personalization);

        // Add category and template ID
        mail.addCategory("carportapp");
        mail.setTemplateId("d-cd10b548b86849aea9d1785fce4952b7");

        com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(API_KEY);
        Request request = new Request();
        try
        {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            //response
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());
        } catch (IOException ex)
        {
            System.out.println("Error sending mail");
            throw ex;
        }
    }

    public static boolean sendMailFromMain(Context ctx)
    {
        String to = ctx.formParam("to");
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String zip = ctx.formParam("zip");
        String body = ctx.formParam("body");

        try
        {
            sendEmail(to, name, email, zip, body);
            return true; // Email sent successfully
        } catch (IOException ex)
        {
            System.err.println("Failed to send email: " + ex.getMessage());
            return false; // Email did not send successfully
        }
    }

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