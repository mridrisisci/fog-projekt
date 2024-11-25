package app.utilities;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;

public class SendGrid {

    private static String API_KEY = System.getenv("SENDGRID_API_KEY");
    private static String salespersonEmail = "salesperson@example.com";
    private static String customerEmail;
    private static String adminEmail = "admin@example.com";
    private static String subjectLine = "Carport Order";
    private static String body;

    // Getters for attributes
    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getSalespersonEmail() {
        return salespersonEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getSubjectLine() {
        return subjectLine;
    }

    public String getBody() {
        return body;
    }

    public static void sendEmail(String to, String name, String email, String zip, String body) throws IOException {
        Email from = new Email("christofferleisted@gmail.com");
        from.setName("Johannes Fog Byggemarked");

        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(to));
        personalization.addDynamicTemplateData("name", name);
        personalization.addDynamicTemplateData("email", email);
        personalization.addDynamicTemplateData("zip", zip);
        personalization.addDynamicTemplateData("body", body);
        mail.addPersonalization(personalization);

        // Add category and template ID
        mail.addCategory("carportapp");
        mail.setTemplateId("d-cd10b548b86849aea9d1785fce4952b7");

        com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            //response
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Error sending mail");
            throw ex;
        }
    }
}
