package com.jz.network.mail.smtp;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;

public class SendMailWithAttachment {
  public static void main(String[] args) throws MessagingException, IOException {

    SendMail sender = new SendMail("smtp.sina.com", "javacourse001@sina.com", "java-12345678");
    Session session = sender.createSSLSession();

    try (InputStream in = SendMailWithAttachment.class.getResourceAsStream("/javamail.jpg")) {
      Message message = createMessageWithAttachment(session,
          "javacourse001@sina.com",
          "javacourse001@163.com",
          "email with attachment",
          "This is an email with attachment",
          "javamail.jpg",
          in);

      Transport.send(message);
    }


  }

  private static Message createMessageWithAttachment(Session session,
                                                     String from,
                                                     String to,
                                                     String subject,
                                                     String body,
                                                     String filename,
                                                     InputStream in
  ) throws MessagingException, IOException {

    MimeMessage msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(from));
    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    msg.setSubject(subject, "UTF-8");

    Multipart multipart = new MimeMultipart();
    BodyPart textpart = new MimeBodyPart();
    textpart.setContent(body, "text/html;charset=utf-8");
    multipart.addBodyPart(textpart);

    // add image
    BodyPart imagepart = new MimeBodyPart();
    imagepart.setFileName(filename);
    imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(in, "application/octet-stream")));
    multipart.addBodyPart(imagepart);

    msg.setContent(multipart);

    return msg;
  }
}
