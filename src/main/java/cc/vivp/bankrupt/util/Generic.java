package cc.vivp.bankrupt.util;

import cc.vivp.bankrupt.exception.EntityNotFoundException;
import java.util.Optional;

public final class Generic {

    private Generic() {
        // Prohibit initialization. This class is supposed to be a utility class.
    }

    public static <T> T throwEntityNotFoundExceptionIfNotPresentElseReturnValue(Optional<T> wrappedEntity)
        throws EntityNotFoundException {
        if (wrappedEntity.isPresent()) {
            return wrappedEntity.get();
        }
        throw new EntityNotFoundException(MessageKeys.NOT_FOUND);
    }

}
