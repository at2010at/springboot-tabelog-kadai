package com.example.nagoyameshi.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ReservationInputForm {
    private LocalDate reservationDate;
    private String startTime; // "HH:mm" 形式
    private Integer numberOfPeople;

    // 開始日時を LocalDateTime として取得
    public LocalDateTime getCheckinDateTime() {
        if (reservationDate != null && startTime != null) {
            return LocalDateTime.of(reservationDate, LocalTime.parse(startTime));
        }
        return null;
    }

    // 終了日時は +2時間で仮登録
    public LocalDateTime getCheckoutDateTime() {
        LocalDateTime checkin = getCheckinDateTime();
        return (checkin != null) ? checkin.plusHours(2) : null;
    }
}

