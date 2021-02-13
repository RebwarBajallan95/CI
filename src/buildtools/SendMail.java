package buildtools;

import com.google.gson.internal.LinkedTreeMap;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sound.sampled.Port;
import java.io.BufferedReader;
import java.io.File;
import java.util.Properties;

/**
 * Class to notify the user who pushed to the branch
 * about the automated test results.
 *
 * The class uses outgoing email server from Google (gmail)
 * and sends the email from besselingcodehub@gmail.com
 */
public class SendMail {

    /**
     * Constructor to send an email to recipient
     * @param recipientName name of the recipient
     * @param recipientEmail email of the recipient
     * @param fileResult results of the automated tests
     * @param boolResult results regarding if all tests
     *                  succeded or not
     */
    public static void sendMail(String recipientName,String recipientEmail, File fileResult, Boolean boolResult){
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("besselingcodehub@gmail.com", "SoftwareFundamentals");
            }
        });
        String host = "smtp.gmail.com";
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("besselingcodehub@gmail.com"));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            message.setSubject("Automated test results");

            message.setText(formatOutPut(fileResult,boolResult));

            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

    /**
     * Helper method to format the results
     * to the recipient in a nice way
     * @param results results in text file
     * @param result result whether all tests
     *        succeded or not
     * @return the formated result.
     */
    private static String formatOutPut(File results, boolean result){
        StringBuilder sb = new StringBuilder();
        String boolResult = result ? "All tests succeded" : "Some of the tests failed, see details below.";
        sb.append(boolResult + "\n\n");
        sb.append(results.toString());
        return sb.toString();
    }
}
