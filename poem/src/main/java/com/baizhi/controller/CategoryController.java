package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("getAll")
    public void getAll(HttpServletResponse response) throws IOException {
        List<Category> categorys = categoryService.getAllCategory();

        StringBuilder sb = new StringBuilder();

        sb.append("<select>");
        for (Category category : categorys) {
            sb.append("<option value=\""+category.getId()+"\">"+category.getName()+"</option>");
        }
        sb.append("</select>");

        //设置响应,响应页面
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(sb.toString());
    }

    @RequestMapping("getAllCategory")
    public List<Category> getAllCategory(){
        List<Category> categorys = categoryService.getAllCategory();
        return categorys;
    }
}
