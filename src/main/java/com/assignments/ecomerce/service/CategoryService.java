package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategory();

    Category save(Category category);

    Page<Category> pageCategory(int pageNo);

    Category findById(Integer id);

    Category findByName(String name);
    Category update(Category category);
    Category updateStatus(Integer id);

    public void enableById(Integer id);

    public Page<Category> searchCategory(int pageNo, String keyword);

    Page toPage(List<Category> list, Pageable pageable);

    List<Category> transfer(List<Category> categories);
}
