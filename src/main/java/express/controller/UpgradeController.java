package express.controller;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpgradeController {

  @Autowired
  private Datastore datastore;

  @RequestMapping("/upgrade")
  public String upgrade() {
    return "Success!";
  }
  
  private void createSequences() {
    
  }
}
