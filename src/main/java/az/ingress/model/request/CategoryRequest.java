package az.ingress.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    String name;
    Long parentCategoryId;
    List<CategoryRequest> childCategories;
}
