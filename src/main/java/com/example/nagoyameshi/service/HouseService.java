package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.form.HouseEditForm;
import com.example.nagoyameshi.form.HouseRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.HouseRepository;

@Service
public class HouseService {
   private final HouseRepository houseRepository;    
   private final CategoryRepository categoryRepository;
   

   @Value("${app.upload-dir}")
   private String uploadDir;
   
   public HouseService(HouseRepository houseRepository, 
			CategoryRepository categoryRepository) {
       this.houseRepository = houseRepository;
       this.categoryRepository = categoryRepository;
   }    
   
   @Transactional
   public void create(HouseRegisterForm houseRegisterForm) {
       House house = new House();        
       MultipartFile imageFile = houseRegisterForm.getImageFile();
       
       if (!imageFile.isEmpty()) {
           String imageName = imageFile.getOriginalFilename(); 
           String hashedImageName = generateNewFileName(imageName);
           Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
           copyImageFile(imageFile, filePath);
           house.setImageName(hashedImageName);
       }
       
       house.setName(houseRegisterForm.getName());                
       house.setDescription(houseRegisterForm.getDescription());
       house.setPrice(houseRegisterForm.getPrice());
       house.setCapacity(houseRegisterForm.getCapacity());
       house.setPostalCode(houseRegisterForm.getPostalCode());
       house.setAddress(houseRegisterForm.getAddress());
       house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
                   
       houseRepository.save(house);
   }  
   
   @Transactional
   public void update(HouseEditForm houseEditForm) {
       House house = houseRepository.getReferenceById(houseEditForm.getId());
       MultipartFile imageFile = houseEditForm.getImageFile();
       
       if (!imageFile.isEmpty()) {
           String imageName = imageFile.getOriginalFilename(); 
           String hashedImageName = generateNewFileName(imageName);
           
           
           Path filePath = Paths.get(uploadDir, hashedImageName);
           
           
           copyImageFile(imageFile, filePath);
           house.setImageName(hashedImageName);
       }
       
       house.setName(houseEditForm.getName());                
       house.setDescription(houseEditForm.getDescription());
       house.setCapacity(houseEditForm.getCapacity());
       house.setPostalCode(houseEditForm.getPostalCode());
       house.setAddress(houseEditForm.getAddress());
       house.setPhoneNumber(houseEditForm.getPhoneNumber());

       house.setOpeningTime(houseEditForm.getOpeningTime());
       house.setClosingTime(houseEditForm.getClosingTime());
       house.setHoliday(houseEditForm.getHoliday());
       house.setPriceMin(houseEditForm.getPriceMin());
       house.setPriceMax(houseEditForm.getPriceMax());       
       
       if (houseEditForm.getCategoryId() != null) {
           Category category = categoryRepository.findById(houseEditForm.getCategoryId()).orElse(null);
           house.setCategory(category);
       } else {
           house.setCategory(null);
       }
       
       houseRepository.save(house);
   }  
   
   // UUIDを使って生成したファイル名を返す
   public String generateNewFileName(String fileName) {
	    String ext = "";
	    int i = fileName.lastIndexOf(".");
	    if (i != -1) {
	        ext = fileName.substring(i);
	    }
	    return UUID.randomUUID().toString() + ext;
	}
   

   
   // 画像ファイルを指定したファイルにコピーする
   public void copyImageFile(MultipartFile imageFile, Path filePath) {           
       try {
           Files.copy(imageFile.getInputStream(), filePath);
       } catch (IOException e) {
//           e.printStackTrace();
           throw new RuntimeException("画像ファイルの保存に失敗しました", e);
       }          
   } 
}




