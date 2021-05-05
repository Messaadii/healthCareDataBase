package com.healthCare.healthCareDataBase.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
	
	@Autowired
	private JavaMailSender javaMailSender; 

	public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("healthcaretnteam@gmail.com");
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(text);
		javaMailSender.send(mail);
	}

	public void sendHtmlMessage(String to, String subject,String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	    helper.setFrom("healthcaretnteam@gmail.com");
	    helper.setTo(to);
	    helper.setSubject(subject);
	    String html = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div><b>Health Care Team</b></div>\n" +
                "<br>\n" +
                "<div>To verify your account you need to enter the next code, your verification code is:</div>\n"+
                "<div style=\"margin-top:30px;margin-bottom:30px;margin-left:auto;margin-right:auto; width:fit-content;\">\n"+
                "<div style=\"display:inline-block;width:25px;height:25px; color:white; background-color:#66c2b2; border-radius:5px; font-size:20px;margin:5px; padding-left:12px;padding-bottom:5px;\">"+body.charAt(0)+"</div>\n"+
                "<div style=\"display:inline-block;width:25px;height:25px; color:white; background-color:#66c2b2; border-radius:5px; font-size:20px;margin:5px; padding-left:12px;padding-bottom:5px;\">\n"+body.charAt(1)+"</div>"+
                "<div style=\"display:inline-block;width:25px;height:25px; color:white; background-color:#66c2b2; border-radius:5px; font-size:20px;margin:5px; padding-left:12px;padding-bottom:5px;\">\n"+body.charAt(2)+"</div>"+
                "<div style=\"display:inline-block;width:25px;height:25px; color:white; background-color:#66c2b2; border-radius:5px; font-size:20px;margin:5px; padding-left:12px;padding-bottom:5px;\">\n"+body.charAt(3)+"</div>"+
                "<div style=\"display:inline-block;width:25px;height:25px; color:white; background-color:#66c2b2; border-radius:5px; font-size:20px;margin:5px; padding-left:12px;padding-bottom:5px;\">\n"+body.charAt(4)+"</div>"+
                "</div>\n"+
                "<div>Thank's for choosing Health Care</div>\n"+
                "</body>\n" +
                "</html>\n";
	    
	    helper.setText(html, true);
	    javaMailSender.send(message);
	}

}
