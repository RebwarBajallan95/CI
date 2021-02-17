package buildtools;

import com.google.gson.internal.LinkedTreeMap;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sound.sampled.Port;
import java.io.*;
import java.util.Properties;

/**
 * Class to notify the user who pushed to the branch
 * about the automated test results.
 *
 * The class uses outgoing email server from Google (gmail)
 * and sends the email from besselingcodehub@gmail.com
 */
public class SendMail {
    String output;

    /**
     * Constructor to send an email to recipient
     * @param recipientName name of the recipient
     * @param recipientEmail email of the recipient
     */
    public SendMail(String recipientName,String recipientEmail){
        System.out.println(recipientEmail);
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
            try {
            output = formatOutPut();
            }catch (Exception e){
                System.out.println(e);
            }
                message.setText(output);

            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public String getOutput(){
        return output;
    }

    /**
     * Helper method to format the results
     * to the recipient in a nice way
     *        succeded or not
     * @return the formated result.
     */
    private static String formatOutPut() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("logFile.txt"));
        String line;
        while ((line = br.readLine()) != null){
            line = line + "\n";
            sb.append(line);
        }
        return sb.toString();
    }
}
