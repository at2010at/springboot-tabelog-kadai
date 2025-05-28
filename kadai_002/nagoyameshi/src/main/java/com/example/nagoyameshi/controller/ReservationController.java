package com.example.nagoyameshi.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.House;
import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
//import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.HouseRepository;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.HouseService;
import com.example.nagoyameshi.service.ReservationService;
import com.example.nagoyameshi.service.StripeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;   
    private final HouseRepository houseRepository;
    private final ReservationService reservationService; 
    private final StripeService stripeService; 
    private final HouseService houseService;
    
	public ReservationController(ReservationRepository reservationRepository, 
			HouseRepository houseRepository, 
			ReservationService reservationService, 
			StripeService stripeService,
			HouseService houseService) { 
    	    
    	this.reservationRepository = reservationRepository; 
        this.houseRepository = houseRepository;
        this.reservationService = reservationService;
        this.stripeService = stripeService;
        this.houseService = houseService;
    }    

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        User user = userDetailsImpl.getUser();
        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("reservationPage", reservationPage);         
        
        return "reservations/index";
    }
    
    @GetMapping("/houses/{id}/reservations/input")
    public String input(@PathVariable(name = "id") Integer id,
                        @ModelAttribute @Validated ReservationInputForm reservationInputForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model)
    {   
        House house = houseRepository.getReferenceById(id);
        Integer numberOfPeople = reservationInputForm.getNumberOfPeople();   
        Integer capacity = house.getCapacity();
        
        if (numberOfPeople != null) {
            if (!reservationService.isWithinCapacity(numberOfPeople, capacity)) {
                FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "宿泊人数が定員を超えています。");
                bindingResult.addError(fieldError);                
            }            
        }         
        
        if (bindingResult.hasErrors()) {            
            model.addAttribute("house", house);            
            model.addAttribute("errorMessage", "予約内容に不備があります。"); 
            return "houses/show";
        }
        
        redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);           
        
        return "redirect:/houses/{id}/reservations/confirm";
    }    
    
//    // 確認画面の表示
//    @PostMapping("/houses/{id}/reservations/confirm")
//    public String confirmReservation(
//            @PathVariable Long houseId,
//            @ModelAttribute ReservationRegisterForm form,
//            Model model) {
//
//        model.addAttribute("house", houseService.findById(houseId));
//        model.addAttribute("reservationRegisterForm", form);
//        return "reservations/confirm";  // 上で修正したThymeleafテンプレートに対応
//    }
    
//    @GetMapping("/houses/{id}/reservations/confirm")
//    public String confirm(@PathVariable(name = "id") Integer id,
//                          @ModelAttribute ReservationInputForm reservationInputForm,
//                          @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,                          
//                          Model model) 
//    {        
//        House house = houseRepository.getReferenceById(id);
//        User user = userDetailsImpl.getUser(); 
//                
//        //チェックイン日とチェックアウト日を取得する
//        LocalDate checkinDate = reservationInputForm.getCheckinDate();
//        LocalDate checkoutDate = reservationInputForm.getCheckoutDate();
// 
//        // 宿泊料金を計算する
//        Integer price = house.getPrice();        
//        Integer amount = reservationService.calculateAmount(checkinDate, checkoutDate, price);
//        
//        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(house.getId(), user.getId(), checkinDate.toString(), checkoutDate.toString(), reservationInputForm.getNumberOfPeople(), amount);
//        
//        model.addAttribute("house", house);  
//        model.addAttribute("reservationRegisterForm", reservationRegisterForm);       
//        
//        return "reservations/confirm";
//    }    
    
    @GetMapping("/houses/{id}/reservations/confirm")
    public String confirm(@PathVariable(name = "id") Integer id,
                          @ModelAttribute ReservationInputForm reservationInputForm,
                          @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,     
                          HttpServletRequest httpServletRequest,
                          Model model) 
    {        
        House house = houseRepository.getReferenceById(id);
        User user = userDetailsImpl.getUser(); 

        // 日時を結合して LocalDateTime に
        LocalDateTime checkinDateTime = reservationInputForm.getCheckinDateTime();  // ← ReservationInputFormで組み立てる
        LocalDateTime checkoutDateTime = reservationInputForm.getCheckoutDateTime(); // ← +2時間した値を取得

        Integer price = house.getPrice();        
        Integer amount = price;

        // フォームデータ作成
        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(
            house.getId(),
            user.getId(),
            checkinDateTime.toString(),
            checkoutDateTime.toString(),
            reservationInputForm.getNumberOfPeople(),
            amount
        );

        // Stripe用セッション作成
//        String sessionId = stripeService.createStripeSession(house.getName(), reservationRegisterForm, httpServletRequest);
//
        model.addAttribute("house", house);  
        model.addAttribute("reservationRegisterForm", reservationRegisterForm);      
//        model.addAttribute("sessionId", sessionId);
        
        return "reservations/confirm";
    }

//    @GetMapping("/houses/{id}/reservations/confirm")
//    public String confirm(@PathVariable(name = "id") Integer id,
//                          @ModelAttribute ReservationInputForm reservationInputForm,
//                          @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,     
//                          HttpServletRequest httpServletRequest,
//                          Model model) 
//    {        
//        House house = houseRepository.getReferenceById(id);
//        User user = userDetailsImpl.getUser(); 
//                
//        //チェックイン日とチェックアウト日を取得する
//        LocalDate checkinDate = reservationInputForm.getReservationDate();
//        LocalDate checkoutDate = reservationInputForm.getReservationDate();
// 
//        // 宿泊料金を計算する
//        Integer price = house.getPrice();        
//        Integer amount = reservationService.calculateAmount(checkinDate, checkoutDate, price);
//        
//        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(house.getId(), user.getId(), checkinDate.toString(), checkoutDate.toString(), reservationInputForm.getNumberOfPeople(), amount);
//        
//        String sessionId = stripeService.createStripeSession(house.getName(), reservationRegisterForm, httpServletRequest);
//        
//        model.addAttribute("house", house);  
//        model.addAttribute("reservationRegisterForm", reservationRegisterForm);      
//        model.addAttribute("sessionId", sessionId);
//        
//        return "reservations/confirm";
//    }  

//    @PostMapping("/create")
//    public String createReservation(
//            @PathVariable Long houseId,
//            @ModelAttribute ReservationRegisterForm form,
//            RedirectAttributes redirectAttributes) {
//
//        Reservation reservation = new Reservation();
//
//        // HouseとUserのエンティティを取得して設定
//        House house = houseService.findById(form.getHouseId()); // houseIdを使ってHouseを取得
//        reservation.setHouse(house);
//
//        User user = userService.findById(form.getUserId()); // 認証ユーザーから取得する場合は別処理
//        reservation.setUser(user);
//
//        // その他のフィールドを設定
//        reservation.setCheckinDate(form.getCheckinDate());
//        reservation.setCheckoutDate(form.getCheckoutDate());
//        reservation.setNumberOfPeople(form.getNumberOfPeople());
//        reservation.setAmount(form.getAmount()); // 金額不要であれば省略してOK
//
//        // 保存
//        reservationService.save(reservation);
//
//        redirectAttributes.addFlashAttribute("successMessage", "予約が完了しました。");
//        return "redirect:/houses/" + houseId;
//    }    
    
//    @PostMapping("/create")
//    public String createReservation(
//            @PathVariable Long houseId,
//            @ModelAttribute ReservationRegisterForm form,
//            RedirectAttributes redirectAttributes) {
//
//        Reservation reservation = new Reservation();
//        reservation.setHouse(form.getHouseId());
//        reservation.setUser(form.getUserId());
//        reservation.setCheckinDate(form.getCheckinDate());
//        reservation.setCheckoutDate(form.getCheckoutDate());
//        reservation.setNumberOfPeople(form.getNumberOfPeople());
//        // 金額を使わない場合はコメントアウトOK
//        // reservation.setAmount(form.getAmount());
//
//        reservationService.save(reservation);  // サービス経由で保存
//
//        redirectAttributes.addFlashAttribute("successMessage", "予約が完了しました。");
//        return "redirect:/houses/" + houseId;
//    }    
    
//    @PostMapping("/create")
//    public String createReservation(
//            @PathVariable Long houseId,
//            @ModelAttribute ReservationRegisterForm form,
//            RedirectAttributes redirectAttributes) {
//
//        Reservation reservation = new Reservation();
//
//        reservation.setHouse(houseService.findById(form.getHouseId()));
//        reservation.setUser(userService.findById(form.getUserId()));
//        reservation.setCheckinDate(form.getCheckinDate());
//        reservation.setCheckoutDate(form.getCheckoutDate());
//        reservation.setNumberOfPeople(form.getNumberOfPeople());
//        reservation.setAmount(form.getAmount()); // 不要なら削除可能
//
//        reservationService.save(reservation);
//
//        redirectAttributes.addFlashAttribute("successMessage", "予約が完了しました。");
//        return "redirect:/houses/" + houseId;
//    }
    
    @PostMapping("/houses/{id}/reservations/create")
    public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {                
        reservationService.create(reservationRegisterForm);        
        
        return "redirect:/reservations?reserved";
    }

    @GetMapping("/houses/{id}/reservations/new/{houseId}")
    public String showReservationForm(@PathVariable Integer houseId, Model model) {
        Optional<House> optionalHouse = houseRepository.findById(houseId);
        if (optionalHouse.isEmpty()) {
            return "redirect:/houses";
        }

        House house = optionalHouse.get();
        LocalTime start = house.getOpeningTime();
        LocalTime end = house.getClosingTime();

        System.out.println("Opening: " + start);
        System.out.println("Closing: " + end);

        List<String> timeSlots = new ArrayList<>();
        if (start != null && end != null && !start.isAfter(end)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            while (!start.isAfter(end)) {
                System.out.println("Time slot: " + start); // ← デバッグ
                timeSlots.add(start.format(formatter));
                start = start.plusMinutes(30);
            }
        }

        model.addAttribute("house", house);
        model.addAttribute("timeSlots", timeSlots);
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        return "reservations/form";
    }

}
    