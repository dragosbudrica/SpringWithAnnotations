package com.kepler.rominfo.service;

import com.kepler.rominfo.dao.CategoryDao;
import com.kepler.rominfo.domain.vo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDao categoryDao;

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }
}