package com.sda.store.sdastore;

import com.sda.store.sdastore.model.Category;
import com.sda.store.sdastore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void cleanup(){
        categoryRepository.findAll().forEach(category -> {
            categoryRepository.delete(category);
        });
    }
    @Test
    public void testSaveCategoryWithSubcategories(){
        Category parent = new Category();
        parent.setName("parent");

        Category subcategory1 = new Category();
        subcategory1.setName("sub1");
        subcategory1.setParent(parent);

        Category subcategory2 = new Category();
        subcategory2.setName("sub2");
        subcategory2.setParent(parent);

        List<Category> child = new ArrayList<>();
        child.add(subcategory1);
        child.add(subcategory2);
        parent.setSubCategories(child);

        Category savedParent = categoryRepository.save(parent);
        Assertions.assertEquals(parent.getName(), savedParent.getName());
        Assertions.assertEquals(child.size(), savedParent.getSubCategories().size());
        savedParent.getSubCategories().forEach(subCategory -> {
            Assertions.assertNotNull(subcategory1.getId());
        });
    }

    @Test
    public void testDeleteCategoryAndSubcategories(){

        Category parent = new Category();
        parent.setName("parent");

        Category subcategory1 = new Category();
        subcategory1.setName("sub1");
        subcategory1.setParent(parent);

        Category subcategory2 = new Category();
        subcategory2.setName("sub2");
        subcategory2.setParent(parent);

        List<Category> child = new ArrayList<>();
        child.add(subcategory1);
        child.add(subcategory2);
        parent.setSubCategories(child);

        Category savedParent = categoryRepository.save(parent);
        categoryRepository.delete(savedParent);
        Assertions.assertEquals(0, categoryRepository.count());

    }

}
