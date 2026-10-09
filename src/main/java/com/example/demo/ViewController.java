package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String login() {
        return "user_management/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user_management/register";
    }
    
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }
    
    @GetMapping("/accountant-dashboard")
    public String accountantDashboard() {
        return "accountant-dashboard";
    }
    
    @GetMapping("/supervisor-dashboard")
    public String supervisorDashboard() {
        return "supervisor-dashboard";
    }
    
    @GetMapping("/operator-dashboard")
    public String operatorDashboard() {
        return "operator-dashboard";
    }
    
    @GetMapping("/customer-dashboard")
    public String customerDashboard() {
        return "customer-dashboard";
    }

    @GetMapping("/user-management")
    public String userManagement() {
        return "user_management/user-management";
    }
    
    @GetMapping("/machine-management")
    public String machineManagement() {
        return "machine_management/machine-management";
    }
    
    @GetMapping("/profile")
    public String profile() {
        return "user_management/profile";
    }
    
    @GetMapping("/booking-requests")
    public String bookingRequests() {
        return "booking_request_management/booking-request-management";
    }
    
    @GetMapping("/feedbacks")
    public String feedbacks() {
        return "feedback_management/feedback-management";
    }
    
    @GetMapping("/machines")
    public String machines() {
        return "machine_management/machines";
    }
    
    @GetMapping("/feedback-management")
    public String feedbackManagement() {
        return "feedback_management/feedback-management";
    }
    
    @GetMapping("/booking-request-management")
    public String bookingRequestManagement() {
        return "booking_request_management/booking-request-management";
    }
    
    @GetMapping("/feedback-management-admin")
    public String feedbackManagementAdmin() {
        return "admin_feedback_management";
    }
    
    @GetMapping("/booking-request-management-admin")
    public String bookingRequestManagementAdmin() {
        return "admin_booking_management";
    }
}