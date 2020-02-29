package cc.vivp.bankrupt.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    public ApiError(HttpStatus status) {
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this(status, "Unexpected error", ex, new ArrayList<>());
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this(status, message, ex, new ArrayList<>());
    }

    public ApiError(HttpStatus status, String message, Throwable ex, List<ApiSubError> subErrors) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.subErrors = subErrors;
    }

    public void addSubError(ApiSubError subError) {
        subErrors.add(subError);
    }
}
