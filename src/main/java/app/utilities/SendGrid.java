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

    public static void sendOffer(String email, String subject, Order order) throws IOException
    {
        Email from = new Email(salespersonEmail); // kode: fog12345
        from.setName("Johannes Fog Byggemarked");

        // Ordredetaljer
        int orderIDInteger = order.getOrderID();
        String orderID = String.valueOf(orderIDInteger);
        int length = order.getLength();
        int width = order.getWidth();
        int priceInteger = order.getSalesPrice();
        String price = String.valueOf(priceInteger);
        String roofType = order.getRoofType().toString();
        Timestamp orderPlaced = order.getOrderPlaced();
        String length2 = String.valueOf(length);
        length2 = length2 + " cm";
        String width2 = String.valueOf(width);
        width2 = width2 + " cm";

        // Dynamisk URL til produktion // når app skal deployes
        String baseURL = "https://carport.dataduck.dk";
        String dynamicURL = baseURL + "/order/acceptoffer/" + orderID;




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
        personalization.addDynamicTemplateData("Bestillingsdato", orderPlaced);
        personalization.addDynamicTemplateData("dynamicURL", dynamicURL);
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
        int orderIDInteger = order.getOrderID();
        String orderID = String.valueOf(orderIDInteger);
        int length = order.getLength();
        int width = order.getWidth();
        String roofType = order.getRoofType().toString();
        Timestamp orderPlaced = order.getOrderPlaced();
        String length2 = String.valueOf(length);
        length2 = length2 + " cm";
        String width2 = String.valueOf(width);
        width2 = width2 + " cm";

        // Dynamisk URL til produktion // når app skal deployes
        String baseURL = "https://carport.dataduck.dk";
        String dynamicURL = baseURL + "/order/billOfMaterials/" + orderID;



        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email)); // kode: fog12345
        personalization.addDynamicTemplateData("Emne:", subject);
        personalization.addDynamicTemplateData("email", salespersonEmail);
        personalization.addDynamicTemplateData("Carport_length", length2);
        personalization.addDynamicTemplateData("Carport_bredde", width2);
        personalization.addDynamicTemplateData("Tagtype", roofType);
        personalization.addDynamicTemplateData("Bestillingsdato", orderPlaced);
        personalization.addDynamicTemplateData("dynamicURL", dynamicURL);
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
        int price = order.getSalesPrice();
        String roofType = order.getRoofType().toString();
        Timestamp orderPlaced = order.getOrderPlaced();
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