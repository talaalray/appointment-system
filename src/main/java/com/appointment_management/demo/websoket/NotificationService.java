package com.appointment_management.demo.websoket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j  // ← أضف هذا للطباعة
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyProvider(Long providerId, AppointmentNotification notification) {
        System.out.println(" ===== إرسال إشعار للمزود " + providerId + " =====");
        System.out.println(" الرسالة: " + notification.getMessage());
        System.out.println(" العنوان: /topic/providers/" + providerId + "/appointments");

        messagingTemplate.convertAndSend(
                "/topic/providers/" + providerId + "/appointments",
                notification
        );

        System.out.println(" تم الإرسال!");
    }

    public void notifyCustomer(Long customerId, AppointmentNotification notification) {
        System.out.println(" ===== إرسال إشعار للعميل " + customerId + " =====");
        System.out.println(" الرسالة: " + notification.getMessage());
        System.out.println(" العنوان: /topic/customers/" + customerId + "/appointments");

        messagingTemplate.convertAndSend(
                "/topic/customers/" + customerId + "/appointments",
                notification
        );

        System.out.println(" تم الإرسال!");
    }
}