package az.ingress.service.concrete;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.dao.repository.CategoryRepository;
import az.ingress.mapper.CategoryMapper;
import az.ingress.model.request.CategoryRequest;
import az.ingress.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(CategoryMapper.CATEGORY_MAPPER.toEntity(categoryRequest));
    }

    @Override
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAllByActiveIsTrue();
    }
}
