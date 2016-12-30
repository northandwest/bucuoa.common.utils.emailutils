package com.bucuoa.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	public static void main(String[] args) throws MessagingException {
		// 配置发送邮件的环境属性

		MailServerConfig config = new MailServerConfig();
		config.setAuth(true);
		config.setSmtphost("smtp.163.com");
		config.setUser("");
		config.setPassword("");
		

		MailBody mail = new MailBody();
		mail.setSubject("你好");
		mail.setContent("rt");
		mail.setTo("wujiang@foxmail.com");
		mail.setFrom("yswj81@163.com");

		send(config, mail);
	}

	private static void send( MailServerConfig config , MailBody mail) throws AddressException, MessagingException {
		
		final Properties props = new Properties();
		/*
		 * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
		 * mail.user / mail.from
		 */
		// 表示SMTP发送邮件，需要进行身份验证
		props.put("mail.smtp.auth", String.valueOf(config.isAuth()));
		props.put("mail.smtp.host", config.getSmtphost());
		// 发件人的账号
		props.put("mail.user", config.getUser());
		// 访问SMTP服务时需要提供的密码
		props.put("mail.password", config.getPassword());
		
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 用户名、密码
				String userName = props.getProperty("mail.user");
				String password = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, password);
			}
		};
		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(props, authenticator);
		// 创建邮件消息
		MimeMessage message = new MimeMessage(mailSession);
		// 设置发件人
		InternetAddress form = new InternetAddress(mail.getFrom());
		message.setFrom(form);

		// 设置收件人
		InternetAddress to = new InternetAddress(mail.getTo());
		message.setRecipient(RecipientType.TO, to);

		// 设置抄送
		if (mail.getCc() != null && !mail.getCc().equals("")) {
			InternetAddress cc = new InternetAddress(mail.getCc());
			message.setRecipient(RecipientType.CC, cc);
		}
		// 设置密送，其他的收件人不能看到密送的邮件地址
		if (mail.getBcc() != null && !mail.getBcc().equals("")) {
			InternetAddress bcc = new InternetAddress(mail.getBcc());
			message.setRecipient(RecipientType.CC, bcc);
		}
		// 设置邮件标题
		message.setSubject(mail.getSubject());

		// 设置邮件的内容体
		message.setContent(mail.getContent(), "text/html;charset=UTF-8");

		// 发送邮件
		Transport.send(message);
	}
}