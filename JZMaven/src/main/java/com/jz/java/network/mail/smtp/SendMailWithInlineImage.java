package com.jz.java.network.mail.smtp;

import com.google.common.io.ByteArrayDataInput;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;

public class SendMailWithInlineImage {
  public static void main(String[] args) throws IOException, MessagingException {
    SendMail sender = new SendMail("smtp.sina.com", "javacourse001@sina.com", "java-12345678");
    Session session = sender.createSSLSession();
    try (InputStream in = SendMailWithInlineImage.class.getResourceAsStream("/javamail.jpg")) {
      Message message = createMessageInlineImage(session, "javacourse001@sina.com", "javacourse001@163.com",
          "Hello Java HTML邮件内嵌图片测试",
          "<h1>Hello</h1><p><img src=\"cid:img01\"></p><p>这是一封内嵌图片的<u>javamail</u>测试邮件！</p>",
          "javamail.jpg", in);

      Transport.send(message);
    }

  }

  private static Message createMessageInlineImage(Session session,
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

    BodyPart imagepart = new MimeBodyPart();
    imagepart.setFileName(filename);
    imagepart.setDataHandler(new DataHandler(new ByteArrayDataSource(in, "image/jpeg")));
    // 与HTML的<img src="cid:img01">关联:
    imagepart.setHeader("Content-ID", "<img01>");
    multipart.addBodyPart(imagepart);

    msg.setContent(multipart);

    return msg;
  }
}
