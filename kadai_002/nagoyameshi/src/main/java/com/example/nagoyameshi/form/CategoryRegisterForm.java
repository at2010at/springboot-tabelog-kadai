package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;




@Data
//@AllArgsConstructor
public class CategoryRegisterForm {
    @NotBlank(message = "カテゴリ名を入力してください。")
    private String name;
        

}
