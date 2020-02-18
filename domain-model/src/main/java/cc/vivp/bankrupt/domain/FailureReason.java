package cc.vivp.bankrupt.domain;

public enum FailureReason {
  INSUFFICIENT_FUNDS,
  TARGET_ACCOUNT_DOES_NOT_EXIST,
  DAY_LIMIT_EXHAUSTED,
  TECHNICAL_ERROR;
}
