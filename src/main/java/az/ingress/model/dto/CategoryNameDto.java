package az.ingress.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CategoryNameDto {
    @NotBlank(message = "{category.name.notblank}")
    String name;
    String language;
    @NotBlank(message = "{category.description.notblank}")
    String description;
}
