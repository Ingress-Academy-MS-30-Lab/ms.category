package az.ingress.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CategoryCacheDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    Long id;
    String name;
    List<CategoryCacheDto> subCategories;
}
