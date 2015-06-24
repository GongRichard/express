package express.service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class ExpressNumberPool {

  private static Set<Integer> inUseExpressNumbers = new ConcurrentSkipListSet<Integer>();
  
  // get the minimum number to use
  public static int pullAnAvaliableExpressNumber() {
    int target = 1;
    while (target < 99999) {
      if (inUseExpressNumbers.contains(target)) {
        target++;
        continue;
      } else {
        inUseExpressNumbers.add(target);
        return target;
      }
    }
    return 0;
  }
  
  public static void ErasureExpressNumber(int number) {
    inUseExpressNumbers.remove(number);
  }
}
