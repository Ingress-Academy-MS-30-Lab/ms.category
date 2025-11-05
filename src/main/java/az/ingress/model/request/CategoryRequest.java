package az.ingress.model.request;

import az.ingress.model.dto.CategoryNameDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    List<CategoryNameDto> name;
    Long parentCategoryId;
}
