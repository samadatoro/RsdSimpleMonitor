package me.example;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * simple email class to send email messages
 * @author SNourou
 *
 */
public class Emailer {
	
	private static Emailer _me  = null;

	
	private Emailer(){
		
	}
	
	public static synchronized  Emailer getInstance(){
		if(_me == null){
			_me = new Emailer();
		}
		return _me;
	}
	
	/**
	 * sends email message from the specified fromEmail to the specified toAddress using the specified properties
	 * @param fromEmail
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @param props
	 * @throws AddressException
	 * @throws MessagingException
	 */
    public void sendEmail(String fromEmail, String toEmail, String subject, String body, Properties props) throws AddressException, MessagingException{
     

        // create a mail session
        Session emailSession = Session.getInstance(props, null);
        
        // create the message to be sent
        Message msg = new MimeMessage(emailSession);

        msg.setFrom(new InternetAddress(fromEmail));
        
        // will cause an address exception if an email is invalid. you can customize here depending on your requirements
        InternetAddress recipients[] = InternetAddress.parse(toEmail);

        //set the recipient emails
        msg.setRecipients(Message.RecipientType.TO, recipients);
        if (subject != null) {
            subject = subject.trim();
        }
        msg.setSubject(subject);
        msg.setSentDate(new java.util.Date());
        msg.setContent(body, "text/html");
        Transport.send(msg);

    }
}
