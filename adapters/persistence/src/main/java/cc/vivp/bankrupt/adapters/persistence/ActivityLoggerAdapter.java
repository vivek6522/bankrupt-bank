package cc.vivp.bankrupt.adapters.persistence;

import cc.vivp.bankrupt.domain.Activity;
import cc.vivp.bankrupt.ports.out.ActivityLoggerPort;

public class ActivityLoggerAdapter implements ActivityLoggerPort {

  @Override
  public Activity logActivity(Activity activity) {
    return null;
  }
}
