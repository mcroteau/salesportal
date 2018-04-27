package com.randr.webdw.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// import org.apache.xpath.operations.String;

/**
 * User: Administrator
 * Date: Jul 7, 2004
 * Time: 4:12:02 PM
 * @author randr
 * @version $Revision: 1.7 $
 */
public class EmailUtilities {
    public static final String TYPE_TEXT = "text/plain";
    public static final String TYPE_HTML = "text/html";

    /**
     * Method declaration
     *
     * @param relayHost
     * @param relayUser String
     * @param relayPassword String
     * @param from
     * @param to
     * @param subject
     * @param body
     * @param emailType String
     * @see
     */
    public static boolean sendMail(String relayHost, String relayUser, String relayPassword,
                                String from, String to,
                                String subject, String body,
                                String emailType) {
    	  System.out.println("================================\n\n");
        System.out.println("relayHost = " + "'" +relayHost+"'"); 
        System.out.println("relayUser = " + "'" +relayUser+ "'" );
        System.out.println("relayPassword = " + "'" + relayPassword + "'");
        System.out.println("from = " + from);
        System.out.println("to = " + to);
    	  System.out.println("sendMail");
    	  System.out.println("=================================\n\n");
    	
        boolean debug = true ;    // change to get more information
        Properties props = new Properties();
        props.put("mail.smtp.host", relayHost);

        if (!relayUser.equals("")) {
            props.put("mail.smtp.user", relayUser);
        }

        if (!relayPassword.equals("")) {
            props.put("mail.smtp.password", relayPassword);
        }
        System.out.println("TRUE");
        if (!relayPassword.equals("") && !relayUser.equals("")) {
        	System.out.println("TRUE");
        	props.put("mail.smtp.auth", "true");
        }
        
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);
        try {
            Message message = new MimeMessage(session);
            InternetAddress fromInternetAddress = new InternetAddress(from);
            message.setFrom(fromInternetAddress);

            InternetAddress[] toInternetAddressArray = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, toInternetAddressArray);
            message.setSubject(subject);
            message.setDataHandler(new DataHandler(body.toString(), emailType));
//            message.setContent(body, emailType);
            message.saveChanges();
            Transport tr = session.getTransport("smtp");
            tr.connect(relayHost, relayUser, relayPassword);
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
            
//            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public static boolean sendMail(String from, String to, String relayHost,
            String subject, String body) {
    	System.out.println("\n\nHERE??? NO AUTH\n\n");
		boolean debug = true;    // change to get more information
		Properties props = new Properties();
		
		props.put("mail.smtp.host", relayHost);    // server mail arobs.com
		
		// optional
		// props.put("mail.smtp.user","cb@arobs.com");
		Session session = Session.getDefaultInstance(props, null);
		
		session.setDebug(debug);
		
		try {
		
			// se creaza un mesaj
			Message msg = new MimeMessage(session);
			
			// seteaza de la cine se primesta mailul
			InternetAddress fromAddress = new InternetAddress(from);
			
			msg.setFrom(fromAddress);
			
			InternetAddress[] toAddress = {
			new InternetAddress(to)
			};
			
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject);
			msg.setContent(body, "text/plain");
			Transport.send(msg);
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
    }
    
    

    //created this to catch the Exception from the method calling 
    //to print to screen  (see ApplicationStartupWizard saveEmailSettings();)
    public static void sendMailWithLogging(String from, String to, String relayHost,
            String subject, String body) throws MessagingException {
		
    	boolean debug = true;    // change to get more information
		Properties props = new Properties();
		
		props.put("mail.smtp.host", relayHost);    // server mail arobs.com
		
		// optional
		// props.put("mail.smtp.user","cb@arobs.com");
		Session session = Session.getDefaultInstance(props, null);
		
		session.setDebug(debug);
		

		
		// se creaza un mesaj
		Message msg = new MimeMessage(session);
		
		// seteaza de la cine se primesta mailul
		InternetAddress fromAddress = new InternetAddress(from);
		
		msg.setFrom(fromAddress);
		
		InternetAddress[] toAddress = {new InternetAddress(to)};
		
		msg.setRecipients(Message.RecipientType.TO, toAddress);
		msg.setSubject(subject);
		msg.setContent(body, "text/plain");
		Transport.send(msg);

    }    
    
    
    //created this to catch the Exception from the method calling 
    //to print to screen  (see ApplicationStartupWizard saveEmailSettings();)
    public static void sendMailWithLogging(String relayHost, String relayUser, String relayPassword,
	            String from, String to,
	            String subject, String body,
	            String emailType) throws MessagingException {
		
    	System.out.println("================================\n\n");
		System.out.println("relayHost = " + "'" +relayHost+"'"); 
		System.out.println("relayUser = " + "'" +relayUser+ "'" );
		System.out.println("relayPassword = " + "'" + relayPassword + "'");
		System.out.println("from = " + from);
		System.out.println("to = " + to);
		System.out.println("sendMail");
		System.out.println("=================================\n\n");
		
		boolean debug = true ;    // change to get more information
		Properties props = new Properties();
		props.put("mail.smtp.host", relayHost);
		
		if (!relayUser.equals("")) {
			props.put("mail.smtp.user", relayUser);
		}
		
		if (!relayPassword.equals("")) {
			props.put("mail.smtp.password", relayPassword);
		}
		
		System.out.println("TRUE");
		if (!relayPassword.equals("") && !relayUser.equals("")) {
			System.out.println("TRUE");
			props.put("mail.smtp.auth", "true");
		}
		
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
	
		Message message = new MimeMessage(session);
		InternetAddress fromInternetAddress = new InternetAddress(from);
		message.setFrom(fromInternetAddress);
		
		InternetAddress[] toInternetAddressArray = {new InternetAddress(to)};
		message.setRecipients(Message.RecipientType.TO, toInternetAddressArray);
		message.setSubject(subject);
		message.setDataHandler(new DataHandler(body.toString(), emailType));
		//message.setContent(body, emailType);
		message.saveChanges();
		Transport tr = session.getTransport("smtp");
		tr.connect(relayHost, relayUser, relayPassword);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();

	
	}

}
