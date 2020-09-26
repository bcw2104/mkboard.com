package com.mkboard.serviceOthers;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.mkboard.util.MailForm;

@Service
@EnableAsync
public class MailService {

	@Value("${mail.title}")
	private String mailTitle;
	@Value("${mail.username}")
	private String sender;

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailForm form;

	@Async
	public void sendModifyMail(String to,String key) {
		MimeMessage message = mailSender.createMimeMessage();

		if (isValidEmailAddress(to)) {
			try {
				String subject = mailTitle+ " 이메일 변경 메일";

				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(sender, mailTitle);
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
				String subject = mailTitle+" 회원가입 인증 메일";

				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(sender, mailTitle);
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
				String subject = mailTitle+" 계정찾기 메일";

				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
				messageHelper.setFrom(sender, mailTitle);
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
