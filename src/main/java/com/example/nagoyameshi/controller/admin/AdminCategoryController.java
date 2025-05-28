package com.example.nagoyameshi.controller.admin;

import java.util.List;

//package com.example.nagoyameshi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.service.CategoryService;


@Controller
@RequestMapping("/admin/categories")

public class AdminCategoryController {
	
    private final CategoryRepository categoryRepository;       
    private final CategoryService categoryService;  

	public AdminCategoryController(CategoryRepository categoryRepository, 
			CategoryService categoryService
			) {
        this.categoryRepository = categoryRepository;     
        this.categoryService = categoryService;  
    }	
    

    
    @GetMapping
  public String index(Model model) {
      model.addAttribute("categories", categoryRepository.findAll());
      return "admin/categories/index"; // ← このThymeleafテンプレートが必要
  }
    
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Category category = categoryRepository.getReferenceById(id);

        model.addAttribute("category", category);

        return "admin/categories/show";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("categoryRegisterForm", new CategoryRegisterForm());
        return "admin/categories/register";
    }      
    
    @PostMapping("/create")
    public String create(@ModelAttribute @Validated CategoryRegisterForm categoryRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
        if (bindingResult.hasErrors()) {
            return "admin/categories/register";
        }
        
        categoryService.create(categoryRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "カテゴリを登録しました。");    
        
        return "redirect:/admin/categories";
    }        
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
        categoryRepository.deleteById(id);
                
        redirectAttributes.addFlashAttribute("successMessage", "カテゴリを削除しました。");
        
        return "redirect:/admin/categories";
    }        
    

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        Category category = categoryRepository.getReferenceById(id);

        CategoryEditForm categoryEditForm = new CategoryEditForm(
        	    category.getId(),
        	    category.getName()
        	);


        model.addAttribute("categoryEditForm", categoryEditForm);

        //  カテゴリ一覧を渡す
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "admin/categories/edit";
    }
    
    

    
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated CategoryEditForm categoryEditForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/edit";
        }

        categoryService.update(categoryEditForm); // ← ここで更新処理

        redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");

        return "redirect:/admin/categories";
    }

}