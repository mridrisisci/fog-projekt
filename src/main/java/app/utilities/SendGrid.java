package app.utilities;

import app.entities.Account;
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

    public static void sendOffer(String email, String subject, Order order) throws IOException
    {
        Email from = new Email(salespersonEmail); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        // Ordredetaljer
        int length = order.getLength();
        int width = order.getWidth();
        int priceInteger = order.getPrice();
        String price = String.valueOf(priceInteger);
        String roofType = order.getRoofType().toString();
        String shed = "Uden skur";
        Timestamp orderPlaced = order.getOrderPlaced();
        String hasShed2;
        String length2 = String.valueOf(length);
        length2 = length2 + " cm";
        String width2 = String.valueOf(width);
        width2 = width2 + " cm";


        // ngrok dynamic URL for testing redirects with dynamic mail
        String ngrokURL = "  https://e36b-193-29-107-174.ngrok-free.app ";
        String URL = ngrokURL + "/order/acceptoffer/" + order.getORDER_ID();

        // Dynamisk URL til produktion // når app skal deployes
        String acceptURL = "https://dataduck.dk/order/acceptoffer/" + order.getORDER_ID();




        Mail mail = new Mail();
        mail.setFrom(from);


        Personalization personalization = new Personalization();
        personalization.addTo(new Email(email));
        personalization.addDynamicTemplateData("Emne:", subject);
        personalization.addDynamicTemplateData("email", salespersonEmail);
        personalization.addDynamicTemplateData("Carport_length", length2);
        personalization.addDynamicTemplateData("Carport_bredde", width2);
        personalization.addDynamicTemplateData("Total_pris", price);
        personalization.addDynamicTemplateData("Tagtype", roofType);
        personalization.addDynamicTemplateData("Redskabsrum", shed);
        personalization.addDynamicTemplateData("Bestillingsdato", orderPlaced);
        personalization.addDynamicTemplateData("ngrokURL", URL);
        //personalization.addDynamicTemplateData("AcceptUrl", acceptURL); // uncomment before deploying
        mail.addPersonalization(personalization);

        // Add category and template ID
        mail.addCategory("carportapp");
        mail.setTemplateId("d-6ac3727740a24341b323aef7805519ae");

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

    public static void sendBOM(String email, String subject, Order order) throws IOException
    {
        Email from = new Email(salespersonEmail); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        // Ordredetaljer
        int length = order.getLength();
        int width = order.getWidth();
        String roofType = order.getRoofType().toString();
        String shed = "Uden skur";
        Timestamp orderPlaced = order.getOrderPlaced();
        String hasShed2;
        String length2 = String.valueOf(length);
        length2 = length2 + " cm";
        String width2 = String.valueOf(width);
        width2 = width2 + " cm";


        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email)); // kode: fog12345
        personalization.addDynamicTemplateData("Emne:", subject);
        personalization.addDynamicTemplateData("email", salespersonEmail);
        personalization.addDynamicTemplateData("Carport_length", length2);
        personalization.addDynamicTemplateData("Carport_bredde", width2);
        personalization.addDynamicTemplateData("Tagtype", roofType);
        personalization.addDynamicTemplateData("Redskabsrum", shed);
        personalization.addDynamicTemplateData("Bestillingsdato", orderPlaced);
        mail.addPersonalization(personalization);

        // Add category and template ID
        mail.addCategory("carportapp");
        mail.setTemplateId("d-d24c871f01c543b390ed32391f9fc8ca");

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
        String roofType = order.getRoofType().toString();
        String shed = "Uden skur";
        Timestamp orderPlaced = order.getOrderPlaced();
        String hasShed2;
        String length2 = String.valueOf(length);
        length2 = length2 + " cm";
        String width2 = String.valueOf(width);
        width2 = width2 + " cm";



        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email)); // kode: fog12345
        personalization.addDynamicTemplateData("Emne:", subject);
        personalization.addDynamicTemplateData("email", salespersonEmail);
        personalization.addDynamicTemplateData("Carport_length", length2);
        personalization.addDynamicTemplateData("Carport_bredde", width2);
        personalization.addDynamicTemplateData("Total_pris", price);
        personalization.addDynamicTemplateData("Tagtype", roofType);
        personalization.addDynamicTemplateData("Redskabsrum", shed);
        personalization.addDynamicTemplateData("Bestillingsdato", orderPlaced);
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

    public static void notifySalesPersonOfNewOrder(String email, String subject) throws IOException
    {
        Email from = new Email(salespersonEmail); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email)); // kode: fog12345
        personalization.addDynamicTemplateData("Emne:", subject);
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