package express.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence")
public class SequenceId {
  
  public static final String SEQUENCE_USER = "User";
  
  public static final String SEQUENCE_EXPRESS_ITEM = "ExpressItem";
  
  public static final String SEQUENCE_EXPRESS_BILL = "ExpressBill";

  @Id
  private String id;

  private long seq;

  public SequenceId() {}

  public SequenceId(String id, long seq) {
    super();
    this.id = id;
    this.seq = seq;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getSeq() {
    return seq;
  }

  public void setSeq(long seq) {
    this.seq = seq;
  }
}