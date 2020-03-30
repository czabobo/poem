package com.baizhi.service;

import com.baizhi.dao.CategoryDao;
import com.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> getAllCategory() {
        return categoryDao.selectAll();
    }
}
