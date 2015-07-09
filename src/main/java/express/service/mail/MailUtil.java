package express.service.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MailUtil {

  public static String getEmailBody() {
    try {
      String emailFilePath = "email.html";
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
