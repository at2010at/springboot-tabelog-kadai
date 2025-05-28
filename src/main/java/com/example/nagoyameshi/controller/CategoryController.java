package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.HouseRepository;



@Controller
public class CategoryController {

    private final HouseRepository houseRepository;
    private final CategoryRepository categoryRepository;

    public CategoryController(HouseRepository houseRepository,
                                  CategoryRepository categoryRepository) {
        this.houseRepository = houseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category/{id}")
    public String showByCategory(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return "error/404";
        }

        List<House> houseList = houseRepository.findByCategoryId(id);
        model.addAttribute("houseList", houseList);
        model.addAttribute("category", category);
        return "houses/category"; // 例: houses/category.html
    }
    

}
//}