package com.web.shop.webbanhang.security;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.web.shop.webbanhang.entity.User;
import com.web.shop.webbanhang.service.impl.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private UserService userServices;
	
	@Autowired
	private JavaMailSender mailSender;

	@RequestMapping("/forgot_password")
	public String forgotPassword(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		return "home/user/forgot_password_form";
	}
	
	@PostMapping("/forgot_password")
	public String processForgotPasswordForm(HttpServletRequest request,Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(45);
		
		try {
			userServices.updateResetPasswordToken(token, email);
			// generate reset password link
			//send email 
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token="+token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have send an email a reset password link to your email. Please check. ");
			
		} catch (UserNotFoundException e) {
			model.addAttribute("error", e.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email.");
		}
//		System.out.println("Email: "+email);
//		System.out.println("Token: "+token);
		model.addAttribute("pageTitle", "Forgot Password");
		return "home/user/forgot_password_form";
	}

	private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("contact@shopme.com","Shopme Support");
		helper.setTo(email);
		
		String subject = "Here's the link to reset your password";
		String content = "<p>Hello,</p>"
				+ "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change password:</p>"
				+ "<p><a href=\""+resetPasswordLink+ "\">Change my password</a></p>"
						+ "<p>Ignore this email if you do remember your password, or you have not made the request.</p>";
		helper.setSubject(subject);
		helper.setText(content,true);
		mailSender.send(message);
		
	}
	
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token")String token, Model model) {
		User user = userServices.getByResetPasswordToken(token);
		if(user == null) {
			model.addAttribute("message", "Reset your password");
			model.addAttribute("title", "Invalid Token");
			return "message";
		}
		model.addAttribute("token", token);
		model.addAttribute("pageTitle", "Reset Your Password");
		return "home/user/reset_password_form";
	}
	
	
	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request,Model model) {
		
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		User user = userServices.getByResetPasswordToken(token);
		if(user == null) {
			model.addAttribute("message", "Reset your password");
			model.addAttribute("title", "Invalid Token");
			return "home/user/message";
		} else {
			userServices.updatePassword(user, password);
			model.addAttribute("message", "You have successfully changed your password.");
		}
		return "redirect:/users/login";
	}
}
