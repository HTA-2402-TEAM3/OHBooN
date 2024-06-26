package com.ohboon.ohboon.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Map;
import java.util.Properties;

public class NaverMail {
    private final Properties mailServerInfo;
    private final Authenticator authenticator;

    public NaverMail() {
        String naverID = "mgrtest";
        String naverPW = "Ujm1357!@";

        mailServerInfo = new Properties();
        mailServerInfo.put("mail.smtp.host","smtp.naver.com");
        mailServerInfo.put("mail.smtp.port","465");
        mailServerInfo.put("mail.smtp.ssl.enable","true");
        mailServerInfo.put("mail.smtp.ssl.trust","smtp.naver.com");
        mailServerInfo.put("mail.smtp.starttls.enable","true");
        mailServerInfo.put("mail.smtp.auth","true");
        mailServerInfo.put("mail.smtp.debug","true");
        mailServerInfo.put("mail.smtp.ssl.protocols","TLSv1.3");
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(naverID, naverPW);
            }
        };
    }

    public void sendMail(Map<String,String> sendMailInfo) throws MessagingException {
        //메일 보내는 세션 생성
        Session session = Session.getInstance(mailServerInfo,authenticator);
        session.setDebug(true);

        //메세지 작성
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMailInfo.get("from")));
        message.addRecipients
                (Message.RecipientType.TO,
                        String.valueOf(new InternetAddress(sendMailInfo.get("to"))));
        message.setSubject(sendMailInfo.get("subject"));
        message.setContent(sendMailInfo.get("content"),sendMailInfo.get("format"));

        Transport.send(message);
    }
}