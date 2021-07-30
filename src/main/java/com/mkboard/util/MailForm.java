package com.mkboard.util;

import org.springframework.beans.factory.annotation.Value;

public class MailForm {

	@Value("${mail.title}")
	private String mailTitle;
	@Value("${mail.serverPost}")
	private int serverPost;

	public String registerMailForm(String key) {
		StringBuffer page = new StringBuffer();
		page.append("<html>");
		page.append("<body>");
		page.append("<div style='width: 500px; min-height: 300px; padding-left: 30px; margin-right: 80px; box-shadow: 1px 1px 3px 0 rgba(82, 62, 62, 0.4);"
				+ "font-family: Arial, Helvetica, sans-serif;'>");
		page.append("<div style='padding: 20px 0 10px 0; border-bottom: 5px solid #0059ab; font-weight: bold; font-size: 1.3rem; margin-bottom: 10px;'>"+mailTitle+"</div>");
		page.append("<h2>회원가입을 위한 인증코드입니다.</h2>");
		page.append("<div style='padding: 10px 0; font-size:16px'>");
		page.append("인증코드 : <span style='font-weight:bold;'>"+key+"</span><br/>");
		page.append("<div style='margin-top: 10px;font-size: 13px;'>※ 인증코드는 15분동안 유효합니다.</div>");
		page.append("</div>");
		page.append("</div>");
		page.append("</body>");
		page.append("</html>");

		return page.toString();
	}

	public String modifyMailForm(String key) {
		StringBuffer page = new StringBuffer();
		page.append("<html>");
		page.append("<body>");
		page.append("<div style='width: 500px; min-height: 300px; padding-left: 30px; margin-right: 80px; box-shadow: 1px 1px 3px 0 rgba(82, 62, 62, 0.4);"
				+ "font-family: Arial, Helvetica, sans-serif;'>");
		page.append("<div style='padding: 20px 0 10px 0; border-bottom: 5px solid #0059ab; font-weight: bold; font-size: 1.3rem; margin-bottom: 10px;'>"+mailTitle+"</div>");
		page.append("<h2>이메일 변경 인증코드입니다.</h2>");
		page.append("<div style='padding: 10px 0; font-size:16px'>");
		page.append("인증코드 : <span style='font-weight:bold;'>"+key+"</span><br/>");
		page.append("<div style='margin-top: 10px;font-size: 13px;'>※ 인증코드는 15분동안 유효합니다.</div>");
		page.append("</div>");
		page.append("</div>");
		page.append("</body>");
		page.append("</html>");

		return page.toString();
	}

	public String findMailForm(String key,String userId) {
		StringBuffer page = new StringBuffer();
		page.append("<html>");
		page.append("<body>");
		page.append("<div style='width: 500px; min-height: 300px; "
				+ "padding-left: 30px; margin-right: 80px; box-shadow: 1px 1px 3px 0 rgba(82, 62, 62, 0.4);"
				+ "font-family: Arial, Helvetica, sans-serif; '>");
		page.append("<div style='padding: 20px 0 10px 0; border-bottom: 5px solid #0059ab; font-weight: bold; font-size: 1.3rem; margin-bottom: 10px;'>"+mailTitle+"</div>");
		page.append("<h2>계정 정보입니다.</h2>");
		page.append("<div style='padding: 10px 0; font-size:16px'>");
		page.append("아이디: <span style='font-weight:bold;'>"+userId+"</span><br/>");
		page.append("</div>");
		page.append("<a href='http://localhost:"+serverPost+"/account/find/"+key+"'>"
				+ "<button style='width:200px; height:40px;"
				+ "font-size: 14px;"
				+ "background-color: #0059ab;color: #ffffff;"
				+ "letter-spacing: 2px;font-weight: bold;"
				+ "border-radius: 5px;'>비밀번호 변경</button></a><br/>");
		page.append("<div style='margin-top: 10px;font-size: 13px;'>※ 비밀번호 변경 링크는 15분동안 유효합니다.</div>");
		page.append("</div>");
		page.append("</body>");
		page.append("</html>");

		return page.toString();
	}
}
