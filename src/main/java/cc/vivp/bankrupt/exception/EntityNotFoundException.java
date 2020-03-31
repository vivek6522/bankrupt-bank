package cc.vivp.bankrupt.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(final String message) {
        super(message);
    }

}
