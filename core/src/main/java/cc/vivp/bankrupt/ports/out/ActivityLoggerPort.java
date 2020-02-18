package cc.vivp.bankrupt.ports.out;

import cc.vivp.bankrupt.domain.Activity;

public interface ActivityLoggerPort {
  Activity logActivity(Activity activity);
}
