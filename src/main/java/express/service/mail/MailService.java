package express.service.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class MailService {

  public void sendHtmlMail(MailInfo emailInfo) throws Exception {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    // props.put("mail.smtp.host", "smtp.sina.cn");
    props.put("mail.smtp.host", "mail.sap.corp");

    String fromAddress = EmailAccountUtil.getAddress();
    String fromPwd = EmailAccountUtil.getPassword(fromAddress);

    MyAuthenticator authenticator = new MyAuthenticator(fromAddress, fromPwd);
    // get email session
    Session session = Session.getInstance(props, authenticator);

    // define MIME email object
    MimeMessage mimeMessage = new MimeMessage(session);

    mimeMessage.setFrom(new InternetAddress(fromAddress, "Smart Express System"));

    mimeMessage.setRecipient(Message.RecipientType.TO, emailInfo.getEmailTo());
    mimeMessage.setRecipients(Message.RecipientType.CC, emailInfo.getEmailCc());
    mimeMessage.setSubject(emailInfo.getEmailSubject());
    mimeMessage.setSentDate(new Date());

    // set "related to send html email"
    Multipart mp = new MimeMultipart("related");

    MimeBodyPart htmlBodyPart = new MimeBodyPart();

    emailInfo.setEmailBody(emailInfo.getEmailBody().replaceAll("contentInfo",
        emailInfo.getEmailContent()));
    emailInfo.setEmailBody(emailInfo.getEmailBody().replaceAll("themeInfo",
        emailInfo.getEmailTheme()));

    htmlBodyPart.setDataHandler(new DataHandler(emailInfo.getEmailBody(),
        "text/html;charset=GBK"));

    mp.addBodyPart(htmlBodyPart);
    // set sap header pic
    FileDataSource sapPic = new FileDataSource(new File(MailInfo.class
        .getClassLoader().getResource("sap.jpg").getPath()));
    MimeBodyPart sapPicBodyPart = new MimeBodyPart();
    sapPicBodyPart.setDataHandler(new DataHandler(sapPic));
    sapPicBodyPart.setContentID("sapPic");
    mp.addBodyPart(sapPicBodyPart);

    mimeMessage.setContent(mp);
    Transport.send(mimeMessage);
  }

  private class MyAuthenticator extends Authenticator {
    private String address;
    private String pwd;

    public MyAuthenticator(String address, String pwd) {
      this.address = address;
      this.pwd = pwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(address, pwd);
    }
  }
}
