package cc.vivp.bankrupt.exception;

public class TransferException extends DomainException {

    public TransferException(final String message) {
        super(message);
    }

    public TransferException(final Throwable cause) {
        super(cause);
    }
}
