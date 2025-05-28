package com.example.nagoyameshi.controller;


import java.util.Map;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

//import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

@Controller
//@RequestMapping("/subscription")
@RequestMapping("/api")
public class SubscriptionController {

    @Value("${stripe.api.secret-key}")
    private String stripeSecretKey;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public SubscriptionController(UserRepository userRepository, 
    		RoleRepository roleRepository,
    		UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostMapping("/create-checkout-session")
    @ResponseBody
    public ResponseEntity<?> createCheckoutSession(@AuthenticationPrincipal UserDetailsImpl userDetails) throws StripeException {
        User user = userDetails.getUser();
        Stripe.apiKey = stripeSecretKey;

        // まだStripe顧客IDがなければ作成してDBに保存
        if (user.getStripeCustomerId() == null || user.getStripeCustomerId().isEmpty()) {
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setEmail(user.getEmail())
                .build();
            Customer customer = Customer.create(customerParams);
            user.setStripeCustomerId(customer.getId());
            userRepository.save(user);
        }

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
            .setSuccessUrl("http://localhost:8080/api/success")
            .setCancelUrl("http://localhost:8080/api/cancel")
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPrice("price_1RQ5iZCkhpTGubr34KXY0B2o")
                    .build()
            )
            .setCustomer(user.getStripeCustomerId())  // 顧客IDをセット
            .build();

        Session session = Session.create(params);
        return ResponseEntity.ok(session.getUrl());
    }
    
    


    @PostMapping("/create-portal-session")
    @ResponseBody
    public Map<String, String> createPortalSession(@AuthenticationPrincipal UserDetailsImpl userDetails) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String stripeCustomerId = userDetails.getUser().getStripeCustomerId();

        if (stripeCustomerId == null || stripeCustomerId.isEmpty()) {
            throw new IllegalStateException("Stripeの顧客IDが登録されていません");
        }

        // Billing Portal セッションの作成
        SessionCreateParams params = SessionCreateParams.builder()
            .setCustomer(stripeCustomerId)
            .setReturnUrl("http://localhost:8080/user")
            .build();

        Session portalSession = Session.create(params);

        return Map.of("url", portalSession.getUrl());
    }
    


    @GetMapping("/success")
    public String subscriptionSuccess(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser(); // 実際のエンティティ取得

        Role subscribedRole = roleRepository.findById(3)
            .orElseThrow(() -> new IllegalArgumentException("サブスク用ロールが存在しません"));

        user.setRole(subscribedRole);
        userRepository.save(user);

        return "redirect:/user";
    }
    

    

    
    @PostMapping("/cancel")
    public String cancelSubscription(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        // サブスク解除 = roleを通常会員に変更（例: role_id = 2）
        userService.cancelSubscription(user);

        return "redirect:/user?canceled"; // 解除後のリダイレクト先
    }

    
    @GetMapping("/cancel")
    public String cancelSubscription(Authentication authentication, RedirectAttributes redirectAttributes) {
        // 認証情報からユーザーを取得
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        // ユーザーのサブスクを解除する処理（サービスクラスなどにロジックを委譲）
        userService.cancelSubscription(user);

        // フラッシュメッセージをセット
        redirectAttributes.addFlashAttribute("successMessage", "サブスクリプションを解除しました。");

        // 会員情報ページにリダイレクト
        return "redirect:/user";
    }
    

}
