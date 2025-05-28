package com.example.nagoyameshi.controller;

import java.util.List;

//import org.apache.catalina.User;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.HouseRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;

@Controller
@RequestMapping("/houses")
public class HouseController {
    private final HouseRepository houseRepository;   
    private final ReviewRepository reviewRepository; 
	private final FavoriteRepository favoriteRepository;
	private final CategoryRepository categoryRepository;	
	
	public HouseController(HouseRepository houseRepository,
            ReviewRepository reviewRepository,
            FavoriteRepository favoriteRepository,
            CategoryRepository categoryRepository) {
				this.houseRepository = houseRepository;
				this.reviewRepository = reviewRepository;
				this.favoriteRepository = favoriteRepository;
				this.categoryRepository = categoryRepository;
}
	

  
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "area", required = false) String area,
                        @RequestParam(name = "price", required = false) Integer price,   
                        @RequestParam(name = "order", required = false) String order,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        @RequestParam(name = "categoryId", required = false) Integer categoryId,
                        Model model) 
    {
        Page<House> housePage;
                
        if (keyword != null && !keyword.isEmpty()) {
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
            } else {
                housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
            } 
        } else if (area != null && !area.isEmpty()) {
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
            } else {
                housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
            }  
        } else if (price != null) {
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
            } else {
                housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
            } 
            
        } else if (categoryId != null) {
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByCategoryIdOrderByPriceAsc(categoryId, pageable);
            } else {
                housePage = houseRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId, pageable);
            }
            
        } else {
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findAllByOrderByPriceAsc(pageable);
            } else {
                housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);   
            } 
        }                

        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("categoryId", categoryId);        
        
        model.addAttribute("housePage", housePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("price", price);   
        model.addAttribute("order", order);
        
        return "houses/index";
    }
        
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model,
                       @AuthenticationPrincipal UserDetailsImpl userDetails) {

        House house = houseRepository.getReferenceById(id);
        model.addAttribute("house", house);
        
        List<Review> reviewList = reviewRepository.findByHouse(house);
        model.addAttribute("reviewList", reviewList);

        boolean hasReview = false;
        boolean isFavorited = false;
        Integer loginUserId = null;

        if (userDetails != null) {
            User loginUser = userDetails.getUser();
            loginUserId = loginUser.getId();
            hasReview = reviewRepository.existsByHouseIdAndUserId(house.getId(), loginUser.getId());
            
            isFavorited = favoriteRepository.existsByHouseIdAndUserId(house.getId(), loginUser.getId());
        }

        model.addAttribute("hasReview", hasReview);
        model.addAttribute("isFavorited", isFavorited);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("reservationInputForm", new ReservationInputForm());

        return "houses/show";
    }
    

    
}
//}