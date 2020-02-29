package cc.vivp.bankrupt.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntityNotFoundException extends Exception {
    private final Map<String, String> params;

    public EntityNotFoundException(final String message) {
        super(message);
        params = new HashMap<>(0);
    }
}
