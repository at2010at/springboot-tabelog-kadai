package com.example.nagoyameshi.controller.admin;

import java.util.List;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.form.HouseEditForm;
import com.example.nagoyameshi.form.HouseRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.HouseRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.service.HouseService;

@Controller
@RequestMapping("/admin/houses")

public class AdminHouseController {
	
    private final HouseRepository houseRepository;       
    private final HouseService houseService;  
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    
	public AdminHouseController(HouseRepository houseRepository, 
			HouseService houseService, 
			ReviewRepository reviewRepository, 
			CategoryRepository categoryRepository) {
        this.houseRepository = houseRepository;     
        this.houseService = houseService;  
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
    }	
    

    
    @GetMapping
    public String index(Model model,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        @RequestParam(name = "keyword", required = false) String keyword) {

        Page<House> housePage;

        if (keyword != null && !keyword.isEmpty()) {
            housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);                
        } else {
            housePage = houseRepository.findAll(pageable);
        }

        List<Review> reviews = reviewRepository.findAll(); 
        model.addAttribute("housePage", housePage);        
        model.addAttribute("keyword", keyword);
        model.addAttribute("reviews", reviews);             

        return "admin/houses/index";
    }
    
    
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        List<Review> reviews = reviewRepository.findByHouse(house); 

        model.addAttribute("house", house);
        model.addAttribute("reviews", reviews);

        return "admin/houses/show";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("houseRegisterForm", new HouseRegisterForm());
        return "admin/houses/register";
    }      
    
    @PostMapping("/create")
    public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
        if (bindingResult.hasErrors()) {
            return "admin/houses/register";
        }
        
        houseService.create(houseRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");    
        
        return "redirect:/admin/houses";
    }    
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        String imageName = house.getImageName();

        HouseEditForm houseEditForm = new HouseEditForm(
        	    house.getId(),
        	    house.getName(),
        	    null, // MultipartFile はアップロード対象なので null でOK
        	    house.getDescription(),
//        	    house.getPrice(),
        	    house.getCapacity(),
        	    house.getPostalCode(),
        	    house.getAddress(),
        	    house.getPhoneNumber(),
        	    house.getOpeningTime(),
        	    house.getClosingTime(),
        	    house.getHoliday(),
        	    house.getPriceMin(),
        	    house.getPriceMax(),
        	    house.getCategory() != null ? house.getCategory().getId() : null
        	);


        //  追加でフォームに設定
        houseEditForm.setOpeningTime(house.getOpeningTime());
        houseEditForm.setClosingTime(house.getClosingTime());
        houseEditForm.setHoliday(house.getHoliday());
        houseEditForm.setPriceMin(house.getPriceMin());
        houseEditForm.setPriceMax(house.getPriceMax());
        if (house.getCategory() != null) {
            houseEditForm.setCategoryId(house.getCategory().getId());
        }

        model.addAttribute("imageName", imageName);
        model.addAttribute("houseEditForm", houseEditForm);

        //  カテゴリ一覧を渡す
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "admin/houses/edit";
    }
    
    

    
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated HouseEditForm houseEditForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/houses/edit";
        }

        houseService.update(houseEditForm); 

        redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");

        return "redirect:/admin/houses";
    }


    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
        houseRepository.deleteById(id);
                
        redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
        
        return "redirect:/admin/houses";
    }    
    

    
}

