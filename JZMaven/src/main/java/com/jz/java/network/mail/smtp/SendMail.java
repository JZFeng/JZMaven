package com.jz.java.network.mail.smtp;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
  private  String smtp_host;
  private  String user;
  private  String password;
  private final boolean enable_debug = true;

  SendMail(String smtp_host, String user, String password) {
    this.smtp_host = smtp_host;
    this.user = user;
    this.password = password;
  }

  public static void main(String[] args) throws MessagingException {
    SendMail sender = new SendMail("smtp.sina.com", "javacourse001@sina.com", "java-12345678");
    Session session = sender.createSSLSession();
    Message message = createMimeMessage(session, "javacourse001@sina.com", "javacourse001@sina.com","java测试","这是一封java测试邮件");
    Transport.send(message);
  }

  private Session createSSLSession() {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", smtp_host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.auth","true");
//    启动ssl
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.put("mail.smtp.socketFactory.port","465");

    Session session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user,password);
      }
    });

    session.setDebug(enable_debug);

    return session;
  }

  private static Message createMimeMessage(Session session, String from, String to, String subject, String body) throws MessagingException {
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(subject,"UTF-8");
    message.setText(body, "UTF-8");
    return message;

  }
}
