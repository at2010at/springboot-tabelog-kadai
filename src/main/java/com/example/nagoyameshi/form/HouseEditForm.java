package com.example.nagoyameshi.form;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseEditForm {
    @NotNull
    private Integer id;    
    
    @NotBlank(message = "民宿名を入力してください。")
    private String name;
        
    private MultipartFile imageFile;
    
    @NotBlank(message = "説明を入力してください。")
    private String description;   
    
    @NotNull(message = "定員を入力してください。")
    @Min(value = 1, message = "定員は1人以上に設定してください。")
    private Integer capacity;       
    
    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;
    
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;
    
    @NotNull(message = "開店時間を入力してください。")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openingTime;

    @NotNull(message = "閉店時間を入力してください。")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closingTime;

//    月～日　祝日は未定
    private String holiday;

    @Min(value = 0, message = "最低価格は0円以上に設定してください。")
    private Integer priceMin;

    @Min(value = 0, message = "最高価格は0円以上に設定してください。")
    private Integer priceMax;

    private Integer categoryId; // category は ID で管理するならこれが必要
        
}