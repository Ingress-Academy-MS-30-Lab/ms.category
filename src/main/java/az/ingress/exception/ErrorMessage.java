package az.ingress.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    UNEXPECTED_ERROR("UNEXCEPTED_ERROR", "Unexpected error occurred"),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "Category not found");

    private final String code;
    private final String message;
}