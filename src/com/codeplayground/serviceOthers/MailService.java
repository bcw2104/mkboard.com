package com.codeplayground.serviceOthers;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class MailService {
	@Autowired
	private JavaMailSender mailSender;

	public String registerMailForm(String key) {
		StringBuffer page = new StringBuffer();
		page.append("<html>");
		page.append("<body>");
		page.append("<div style='width: 500px; min-height: 300px; padding-left: 30px; margin-right: 80px; box-shadow: 1px 1px 3px 0 rgba(82, 62, 62, 0.4);'>");
		page.append("<div style='padding: 20px 0 10px 0; border-bottom: 5px solid #0059ab; font-weight: bold; font-size: 1.3rem; margin-bottom: 10px;'>CodePlayground</div>");
		page.append("<div style='padding: 10px 0; font-size:15px'>");
		page.append("회원가입을 위한 인증번호입니다.<br/>");
		page.append("인증번호 : <span style='font-weight:bold;'>"+key+"</span><br/>");
		page.append("</div>");
		page.append("</div>");
		page.append("</body>");
		page.append("</html>");

		return page.toString();
	}

	public boolean sendRegisterMail(String to,String key) {
		MimeMessage message = mailSender.createMimeMessage();
		boolean result = true;

		if (isValidEmailAddress(to)) {
			try {
				String subject = "CodePlayGround 회원가입 인증 메일";

				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom("bcw2104@gmail.com", "CodePlayGround");
				messageHelper.setSubject(subject);
				messageHelper.setTo(to);
				messageHelper.setText(registerMailForm(key), true);

				mailSender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}
