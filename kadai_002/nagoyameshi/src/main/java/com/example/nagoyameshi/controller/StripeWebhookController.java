package com.example.nagoyameshi.controller;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Subscription;
import com.stripe.net.Webhook;


@RestController
@RequestMapping("/api/webhook")
public class StripeWebhookController {

    @Value("${stripe.api.secret-key}")
    private String stripeSecretKey;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public StripeWebhookController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestHeader("Stripe-Signature") String sigHeader, @RequestBody String payload) {
        Stripe.apiKey = stripeSecretKey;
        String endpointSecret = "your_webhook_signing_secret_here";

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook signature verification failed");
        }

        switch (event.getType()) {
            case "customer.subscription.created":
            case "invoice.payment_succeeded":
                // 顧客IDを取得
                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                if (dataObjectDeserializer.getObject().isPresent()) {
                    if ("customer.subscription.created".equals(event.getType())) {
                        Subscription subscription = (Subscription) dataObjectDeserializer.getObject().get();
                        String customerId = subscription.getCustomer();

                        // DBのユーザーをStripe顧客IDで検索してロール更新
                        userRepository.findByStripeCustomerId(customerId).ifPresent(user -> {
                            Role subscribedRole = roleRepository.findById(3).orElseThrow(() -> new RuntimeException("Role not found"));
                            user.setRole(subscribedRole);
                            userRepository.save(user);
                        });
                    }
                    // invoice.payment_succeeded なども必要に応じて同様に処理可能
                }
                break;
            case "customer.subscription.deleted":
                Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
                if (subscription != null) {
                    String customerId = subscription.getCustomer();
                    userRepository.findByStripeCustomerId(customerId).ifPresent(user -> {
                        Role normalRole = roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found"));
                        user.setRole(normalRole);
                        userRepository.save(user);
                    });
                }
                break;
            default:
                // 他のイベントは特に処理しない
                break;
        }

        return ResponseEntity.ok("Success");
    }
}

