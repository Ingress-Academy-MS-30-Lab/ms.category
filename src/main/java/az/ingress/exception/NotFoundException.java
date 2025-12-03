package az.ingress.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final ErrorMessage error;
    public NotFoundException(ErrorMessage error) {
        this.error = error;
    }
}
