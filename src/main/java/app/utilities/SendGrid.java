package app.utilities;

import app.entities.Order;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;
import java.sql.Timestamp;

public class SendGrid
{

    private static final String API_KEY = System.getenv("API_SEND_GRID");
    private static final String salespersonEmail = "sales.person.fog@gmail.com";
    private static final String customerEmail = "customer.fog.test@gmail.com";
    private static String subject;

    public static void sendOffer(String email, String subject) throws IOException
    {
        Email from = new Email(salespersonEmail); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email)); // kode: fog12345
        personalization.addDynamicTemplateData("Name:", "Salesperson");
        personalization.addDynamicTemplateData("email", salespersonEmail);
        personalization.addDynamicTemplateData("zip", "2100");
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

    public static void sendBOM(String email, String subject) throws IOException
    {
        Email from = new Email(salespersonEmail); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email)); // kode: fog12345
        personalization.addDynamicTemplateData("email", salespersonEmail);
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

    public static void sendReceipt(String email, String subject, Order order) throws IOException
    {
        Email from = new Email(salespersonEmail); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        // Ordredetaljer
        int length = order.getLength();
        int width = order.getWidth();
        int price = 200;
        String roofType = order.getRoofType();
        boolean hasShed = order.getHasShed();
        Timestamp orderPlaced = order.getORDER_PLACED();

        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email)); // kode: fog12345
        personalization.addDynamicTemplateData("Emne:", subject);
        personalization.addDynamicTemplateData("email", salespersonEmail);
        personalization.addDynamicTemplateData("Carport længde: ", length);
        personalization.addDynamicTemplateData("Carport bredde: ", width);
        personalization.addDynamicTemplateData("Total pris: ", price);
        personalization.addDynamicTemplateData("Tagtype: ", roofType);
        personalization.addDynamicTemplateData("Redskabsrum: ", hasShed);
        personalization.addDynamicTemplateData("Bestillingsdato: ", orderPlaced);
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
/*
    public static boolean sendMailFromMain(Context ctx)
    {

        try
        {
            sendEmail();
            return true; // Email sent successfully
        } catch (IOException ex)
        {
            System.err.println("Failed to send email: " + ex.getMessage());
            return false; // Email did not send successfully
        }
    }*/

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
    public String getSubject()
    {
        return subject;
    }




}