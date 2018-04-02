package com.accenture.aris.core.support.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class JavaMailSenderWrapper {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	public void send(String to, String from, String subject, String templatePath, Map<String, Object> model) {
		String[] tos = new String[1];
		tos[0] = to;
		sendWithAttachements(tos, from, subject, null, null, templatePath, model);
	}
	
	public void send(String[] to, String from, String subject, String templatePath, Map<String, Object> model) {
		sendWithAttachements(to, from, subject, null, null, templatePath, model);
	}
	
	public void sendWithAttachement(String to, String from, String subject, DataSource attachementDataSource, String attachementName, String templatePath, Map<String, Object> model) {
		String[] tos = new String[1];
		tos[0] = to;
		DataSource[] attachementsDataSource = new DataSource[1];
		attachementsDataSource[0] = attachementDataSource;
		String[] attachementsName = new String[1];
		attachementsName[0] = attachementName;
		sendWithAttachements(tos, from, subject, attachementsDataSource, attachementsName, templatePath, model);
	}
	
	public void sendWithAttachement(String[] to, String from, String subject, DataSource attachementDataSource, String attachementName, String templatePath, Map<String, Object> model) {
		DataSource[] attachementsDataSource = new DataSource[1];
		attachementsDataSource[0] = attachementDataSource;
		String[] attachementsName = new String[1];
		attachementsName[0] = attachementName;
		sendWithAttachements(to, from, subject, attachementsDataSource, attachementsName, templatePath, model);
	}
	
	public void sendWithAttachements(final String[] to, final String from, final String subject, final DataSource[] attachementsDataSource, final String[] attachementName, final String templatePath, final Map<String, Object> model) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				for(int i = 0; i < to.length; i++) {
					message.addTo(to[i]);
				}
				message.setFrom(from);
				message.setSubject(subject);
				message.setText(VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, templatePath, "UTF-8", model), true);
				if(attachementsDataSource != null) {
					for(int i = 0; i < attachementsDataSource.length; i++) {
						message.addAttachment(MimeUtility.encodeWord(attachementName[i]), attachementsDataSource[i]);
					}
					
				}
			}	
		};
		this.mailSender.send(preparator);
	}
	
}
