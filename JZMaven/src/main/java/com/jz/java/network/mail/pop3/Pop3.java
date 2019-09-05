package com.jz.java.network.mail.pop3;

import com.sun.mail.pop3.POP3SSLStore;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Pop3 {
  private final String host;

  private final String user;

  private final String password;

  private final boolean ENABLE_DEBUG;

  Pop3(String host, String user, String password) {
    this.host = host;
    this.user = user;
    this.password = password;
    ENABLE_DEBUG = true;
  }

  public static void main(String[] args) throws MessagingException, IOException {
    Pop3 pop3 = new Pop3("pop.163.com", "javacourse001@163.com", "javamail1234");
    Store store = null;
    Folder folder = null;

    try {
      store = pop3.createSSLStore();
      folder = store.getFolder("INBOX");
      folder.open(Folder.READ_WRITE);
      System.out.println("Total messages: " + folder.getMessageCount());
      System.out.println("New messages: " + folder.getNewMessageCount());
      System.out.println("Unread messages: " + folder.getUnreadMessageCount());
      System.out.println("Deleted messages: " + folder.getDeletedMessageCount());
      Message[] messages = folder.getMessages();
      for (Message message : messages) {
        printMessage((MimeMessage) message);
      }
    } finally {
      if (folder != null) {
        folder.close(true);
      }

      if (store != null) {
        store.close();
      }
    }

  }

  private Store createSSLStore() throws MessagingException {
    Properties properties = new Properties();
    properties.put("mail.store.protocol", "pop3");
    properties.put("mail.pop3.port", "995");
    properties.put("mail.pop3.host", host);

    // 启动SSL:
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.put("mail.smtp.socketFactory.port", "995");

    Session session = Session.getInstance(properties, null);
    session.setDebug(false);

    URLName urlName = new URLName("pop3", host, 995, "", user, password);

    Store store = new POP3SSLStore(session, urlName);
    store.connect();

    return store;
  }

  private static void printMessage(MimeMessage msg) throws IOException, MessagingException {
    System.out.println("--------------------");
    System.out.println("Subject: " + MimeUtility.decodeText(msg.getSubject()));
    System.out.println("From: " + getFrom(msg));
    System.out.println("To: " + getTo(msg));
    System.out.println("Sent: " + msg.getSentDate().toString());
    System.out.println("Seen: " + msg.getFlags().contains(Flags.Flag.SEEN));
    System.out.println("Priority: " + getPriority(msg));
    System.out.println("Size: " + msg.getSize() / 1024 + "kb");
    System.out.println("Body: " + getBody(msg));
    System.out.println("--------------------");
    System.out.println();
  }

  private static String getFrom(MimeMessage msg) throws IOException, MessagingException {
    Address[] froms = msg.getFrom();
    return addressToString(froms[0]);
  }

  private static String getTo(MimeMessage msg) throws MessagingException, IOException {
    // 使用 msg.getAllRecipients() 获取所有收件人
    Address[] tos = msg.getRecipients(MimeMessage.RecipientType.TO);
    List<String> list = new ArrayList<>();
    for (Address to : tos) {
      list.add(addressToString(to));
    }
    return String.join(", ", list);
  }

  private static String addressToString(Address addr) throws IOException {
    InternetAddress address = (InternetAddress) addr;
    String personal = address.getPersonal();
    return personal == null ? address.getAddress()
        : (MimeUtility.decodeText(personal) + " <" + address.getAddress() + ">");
  }

  private static String getPriority(MimeMessage msg) throws MessagingException {
    String priority = "Normal";
    String[] headers = msg.getHeader("X-Priority");
    if (headers != null) {
      String header = headers[0];
      if ("1".equals(header) || "high".equalsIgnoreCase(header)) {
        priority = "High";
      } else if ("5".equals(header) || "low".equalsIgnoreCase(header)) {
        priority = "Low";
      }
    }
    return priority;
  }

  private static String getBody(Part part) throws MessagingException, IOException {
    if (part.isMimeType("text/*")) {
      return part.getContent().toString();
    }
    if (part.isMimeType("multipart/*")) {
      Multipart multipart = (Multipart) part.getContent();
      for (int i = 0; i < multipart.getCount(); i++) {
        BodyPart bodyPart = multipart.getBodyPart(i);
        String body = getBody(bodyPart);
        if (!body.isEmpty()) {
          return body;
        }
      }
    }
    return "";
  }
}
