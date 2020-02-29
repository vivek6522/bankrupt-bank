package cc.vivp.bankrupt.exception;

public class DomainException extends Exception {

    public DomainException() {
    }

    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DomainException(final Throwable cause) {
        super(cause);
    }
}
