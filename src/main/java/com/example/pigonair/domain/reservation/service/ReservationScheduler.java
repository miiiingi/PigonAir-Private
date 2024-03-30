package com.example.pigonair.domain.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduler {

    @Autowired
    private ReservationService reservationService;

    @Scheduled(cron = "0 * * * * *") // 매 분마다 실행
    public void updateReservationStatus() {
        reservationService.updateReservationStatus();
    }
}