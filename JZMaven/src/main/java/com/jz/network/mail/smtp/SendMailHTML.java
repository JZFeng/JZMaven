package com.jz.network.mail.smtp;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailHTML {
  public static void main(String[] args) throws MessagingException {
    SendMail sender = new SendMail("smtp.sina.com", "javacourse001@sina.com", "java-12345678");
    Session session = sender.createSSLSession();
    Message msg = createHTMLMessage(session,"javacourse001@sina.com", "javacourse001@163.com", "Java HTML邮件",
        "<h1>Hello</h1><p>这是一封<u>javamail</u>测试邮件！</p>");

    Transport.send(msg);
  }

  private static Message createHTMLMessage(Session session, String from, String to, String subject, String body) throws MessagingException {
    MimeMessage msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(from));
    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    msg.setSubject(subject,"UTF-8");
    msg.setText(body, "UTF-8", "HTML");

    return msg;
  }
}
