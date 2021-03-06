package express.service.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

public class MailInfo {

  private String emailSubject;
  private String emailBody;

  private Address emailTo;
  private Address[] emailCc;

  private String emailContent = "Welcome!";
  private String emailTheme = "email theme!";

  public MailInfo(String emailSubject, String type) {
    this.emailSubject = emailSubject;
    this.emailBody = getEmailBodyStream();
  }

  public void setEmailSubject(String emailSubject) {
    this.emailSubject = emailSubject;
  }

  public String getEmailSubject() {
    return this.emailSubject;
  }

  public void setEmailBody(String emailBody) {
    this.emailBody = emailBody;
  }

  public String getEmailBody() {
    return this.emailBody;
  }

  public void setEmailTo(String userEmailAddress) throws Exception {
    this.emailTo = new InternetAddress(userEmailAddress);
  }

  public Address getEmailTo() {
    return this.emailTo;
  }

  public void setEmailCc(String... receivers) throws Exception {
    Address[] ccuserEmailAddress = new InternetAddress[receivers.length];
    if (receivers != null) {
      int i = 0;
      for (String address : receivers) {
        ccuserEmailAddress[i] = new InternetAddress(address);
        i++;
      }
    }
    this.emailCc = ccuserEmailAddress;
  }

  public void setEmailCcByArray(String[] receivers) throws Exception {
    Address[] ccuserEmailAddress = new InternetAddress[receivers.length];
    if (receivers != null) {
      int i = 0;
      for (String address : receivers) {
        ccuserEmailAddress[i] = new InternetAddress(address);
        i++;
      }
    }
    this.emailCc = ccuserEmailAddress;
  }

  public Address[] getEmailCc() {
    return this.emailCc;
  }

  public String getEmailContent() {
    return emailContent;
  }

  public void setEmailContent(String emailContent) {
    this.emailContent = emailContent;
  }

  public String getEmailTheme() {
    return emailTheme;
  }

  public void setEmailTheme(String emailTheme) {
    this.emailTheme = emailTheme;
  }

  private static String getEmailBodyStream() {
    try {
      String emailFilePath = MailInfo.class.getClassLoader()
          .getResource("email.html").getPath();
      FileInputStream fileinputstream = new FileInputStream(emailFilePath);
      int lenght = fileinputstream.available();
      byte[] bytes = new byte[lenght];
      lenght = fileinputstream.read(bytes);
      fileinputstream.close();
      return new String(bytes, 0, lenght);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
