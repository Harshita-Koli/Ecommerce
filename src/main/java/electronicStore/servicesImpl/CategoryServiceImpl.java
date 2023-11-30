package electronicStore.servicesImpl;

import electronicStore.entities.Category;
import electronicStore.exceptions.ResourceNotFoundException;
import electronicStore.payloads.CategoryDto;
import electronicStore.payloads.pagebleResponse;
import electronicStore.repositories.CategoryRepository;
import electronicStore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelmapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = modelmapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelmapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        // get category of given Id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception !!"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return modelmapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception !!"));
        categoryRepository.delete(category);
    }

    @Override
    public pagebleResponse<CategoryDto> getAll() {
        return null;


    }

    @Override
    public CategoryDto get(String categoryId) {
        return null;
    }
}
