package com.mailsend;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns="/send")
public class Mymailbox extends HttpServlet {
   
	String name, subject, email, message;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		//String to = "mayurshelke640@gmail.com";
		name = request.getParameter("name");
		email = request.getParameter("email");
		subject = request.getParameter("subject");
		message = request.getParameter("message");
		String to=email;
		String file=request.getParameter("files");
		final String username ="mayurshelke640@gmail.com";
		final String password ="********";
		
		Properties p = new Properties();
		
		p.put("mail.smtp.auth", true);
		p.put("mail.smtp.starttls.enable", true);
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "587");

		 Session session = Session.getInstance(p,
			      new javax.mail.Authenticator() {
			         protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(username, password);
			         }
			      });
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email));
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			
			MimeBodyPart tPart= new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			
			String final_Text = "Name :" + name +"   "+"Email :"+ email +"     " + "Subject :"+ subject + "Message :"+ message +" ";
			tPart.setText(final_Text);
			
			System.out.println(final_Text);
			message.setSubject(subject);
			
			multipart.addBodyPart(tPart);

			message.setContent(multipart);
			MimeBodyPart attachment= new MimeBodyPart();
			//attachment.attachFile("C:\\Users\\Admin\\Desktop\\mailsendingproject.zip");
			attachment.attachFile(file);
			multipart.addBodyPart(attachment);
			message.setContent(multipart);
			//message.setSubject("Contact Details");
			
			Transport.send(message);
			
			out.println("<center><h2 style='color:red;'>Email sent Successfully.<h2>");
			out.println("Thank You " +name+"Your mail has been submitted to us.</center>");
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		
		
	}
	
}
