package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.HouseRepository;


@Controller
public class HomeController {
    private final HouseRepository houseRepository;
//    private final ShopRepository shopRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public HomeController(HouseRepository houseRepository) {
//        public HomeController(HouseRepository houseRepository, ShopRepository shopRepository) {
        this.houseRepository = houseRepository;
//        this.shopRepository = shopRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<House> newHouses = houseRepository.findTop10ByOrderByCreatedAtDesc();
        List<Category> categoryList = categoryRepository.findAll();
//        List<Shop> newShops = shopRepository.findTop6ByOrderByCreatedAtDesc();  // ← 追加

        model.addAttribute("newHouses", newHouses);
        model.addAttribute("categoryList", categoryList);
//        model.addAttribute("newShops", newShops);  // ← 追加

        return "index";
    }
}


