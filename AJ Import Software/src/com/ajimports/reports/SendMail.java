package com.ajimports.reports;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ajimports.view.ProductView;

public class SendMail{
	
	public static Logger logger = Logger.getLogger(SendMail.class);
	private JavaMailSender mailSender;
	private SimpleMailMessage simpleMailMessage;

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendFile(String dear, String content,String reportName) {

	   MimeMessage message = mailSender.createMimeMessage();

	   try{
		   MimeMessageHelper helper = new MimeMessageHelper(message, true);

		   helper.setFrom(simpleMailMessage.getFrom());
		   helper.setTo(simpleMailMessage.getTo());
		   helper.setSubject(simpleMailMessage.getSubject());
		   helper.setText(String.format(
				   simpleMailMessage.getText(), dear, content));

		   FileSystemResource file = new FileSystemResource("D://Murugesu//Documents//"+reportName+OpenFile.fileName+".pdf");
		   helper.addAttachment(file.getFilename(), file);

		   mailSender.send(message);
		   logger.info("Mail sent successfully");

	   }catch (MessagingException e) {
		   throw new MailParseException(e);
	   }
	   catch(Exception e){
		   e.printStackTrace();
	}
	     
      }


        
}