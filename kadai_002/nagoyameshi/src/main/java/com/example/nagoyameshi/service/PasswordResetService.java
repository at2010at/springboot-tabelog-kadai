package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
//import java.util.Optional;
import java.util.UUID;

//import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.PasswordResetToken;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.PasswordResetTokenRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public void processForgotPassword(String email, String baseUrl) {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isEmpty()) return;
//
//        User user = userOpt.get();

        User user = userRepository.findByEmail(email);  

        // トークンを生成して保存
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);

        // URLを作成してメール送信
        String resetLink = baseUrl + "/reset-password?token=" + token;
        emailService.sendResetPasswordEmail(email, resetLink);
    }
}
