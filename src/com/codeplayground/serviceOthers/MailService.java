package com.codeplayground.serviceOthers;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.codeplayground.util.MailForm;

@Service
@EnableAsync
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailForm form;

	@Async
	public void sendModifyMail(String to,String key) {
		MimeMessage message = mailSender.createMimeMessage();

		if (isValidEmailAddress(to)) {
			try {
				String subject = "CodePlayGround 이메일 변경 메일";

				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom("bcw2104@gmail.com", "CodePlayGround");
				messageHelper.setSubject(subject);
				messageHelper.setTo(to);
				messageHelper.setText(form.modifyMailForm(key), true);

				mailSender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Async
	public void sendRegisterMail(String to,String key) {
		MimeMessage message = mailSender.createMimeMessage();

		if (isValidEmailAddress(to)) {
			try {
				String subject = "CodePlayGround 회원가입 인증 메일";

				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom("bcw2104@gmail.com", "CodePlayGround");
				messageHelper.setSubject(subject);
				messageHelper.setTo(to);
				messageHelper.setText(form.registerMailForm(key), true);

				mailSender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Async
	public void sendFindMail(String to,String userId,String key) {
		MimeMessage message = mailSender.createMimeMessage();

		if (isValidEmailAddress(to)) {
			try {
				String subject = "CodePlayGround 계정찾기 메일";

				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom("bcw2104@gmail.com", "CodePlayGround");
				messageHelper.setSubject(subject);
				messageHelper.setTo(to);
				messageHelper.setText(form.findMailForm(key,userId), true);

				mailSender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
