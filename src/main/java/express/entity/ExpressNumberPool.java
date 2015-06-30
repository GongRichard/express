package express.entity;

import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class ExpressNumberPool {

  @Id
  ObjectId id;

  private Set<Integer> inUseExpressNumbers = new HashSet<Integer>();

  public Set<Integer> getInUseExpressNumbers() {
    return inUseExpressNumbers;
  }

  public void setInUseExpressNumbers(Set<Integer> inUseExpressNumbers) {
    this.inUseExpressNumbers = inUseExpressNumbers;
  }
}
