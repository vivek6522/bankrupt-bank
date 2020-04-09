package cc.vivp.bankrupt.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends Exception {

    public AccessDeniedException(final String message) {
        super(message);
    }
}
