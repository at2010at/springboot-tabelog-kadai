package com.example.nagoyameshi.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {    
    private Integer houseId;
        
    private Integer userId;    
        
//    private LocalDateTime checkinDate;    
    private String checkinDate;    
        
//    private LocalDateTime checkoutDate;    
    private String checkoutDate;    
    
    private Integer numberOfPeople;
    
    private Integer amount;    
}