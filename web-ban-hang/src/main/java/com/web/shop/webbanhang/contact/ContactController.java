package com.web.shop.webbanhang.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/users/contact")
    public String viewContactForm(){
        return "home/user/contact";
    }

    @PostMapping("/users/contact")
    public String contactSuccess(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String mailSubject = name + " has sent a message";
        String mailContent = "<p><b>Sender Name:</b> "+ name+"</p>";
        mailContent += "<p><b>Sender Email:</b> "+email+"</p>";
        mailContent += "<p><b>Sender Email:</b> "+subject+"</p>";
        mailContent += "<p><b>Content:</b> "+content+"</p>";

        helper.setFrom("danh91982@gmail.com","ABELO contact");
        helper.setTo(email);
        helper.setSubject(mailSubject);
        helper.setText(mailContent, true);

        mailSender.send(message);

        return "home/user/contact_success";
    }
}
