package cc.vivp.bankrupt.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class Activity {

  @NonNull private final ActivityType activityType;
  @NonNull private final Account account;
  @NonNull private final Money money;
  @NonNull private final LocalDateTime timestamp;
  @NonNull private final Boolean successful;
  private final FailureReason failureReason;
}
