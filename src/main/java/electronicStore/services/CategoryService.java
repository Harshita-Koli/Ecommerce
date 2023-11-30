package electronicStore.services;

import electronicStore.payloads.CategoryDto;
import electronicStore.payloads.pagebleResponse;


public interface CategoryService {
    //    create
    CategoryDto create(CategoryDto categoryDto);

    //    update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //    delete
    void delete(String categoryId);

    //    get all
    pagebleResponse<CategoryDto> getAll();

    //    get single category detail
    CategoryDto get(String categoryId);
    //    search
}
