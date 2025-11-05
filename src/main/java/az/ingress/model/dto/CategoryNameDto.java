package az.ingress.model.dto;

import az.ingress.model.enums.Language;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryNameDto {
    Language language;
    String name;
}
