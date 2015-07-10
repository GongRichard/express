package express.service.mail;

import org.junit.Test;

public class MailInfoTest {

  @Test
  public void testGetHtml() throws Exception {
    MailInfo mailInfo = new MailInfo("test subject", "test contetn");
    mailInfo.setEmailTo("zonghan.wu@sap.com");
    //new MailService().sendHtmlMail(mailInfo);
  }
}
