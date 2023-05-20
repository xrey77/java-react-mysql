package com.springboot.java.react.controllers.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmail {

    @Autowired
    private JavaMailSender javaMailSender;
	
    public void sendMail(String to, String subject, String msg) {
		SimpleMailMessage xmsg = new SimpleMailMessage();
        xmsg.setTo(to);
        xmsg.setSubject(subject);
        xmsg.setText(msg);
        javaMailSender.send(xmsg);
    }	
		
}
