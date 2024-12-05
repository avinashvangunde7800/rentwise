package com.astar.auth.service.impl;

import com.astar.auth.model.entity.RentDetails;
import com.astar.auth.model.entity.User;
import com.astar.auth.repository.RentInfoRepository;
import com.astar.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class EmailNotificationService {

    private final UserRepository userRepository;
    private final RentInfoRepository rentInfoRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.scheduling.enabled:true}")
    private boolean isSchedulingEnabled;

    public EmailNotificationService(UserRepository userRepository, RentInfoRepository rentInfoRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.rentInfoRepository = rentInfoRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "${notification.email.cron}")
    public void sendEmailNotifications() {
        if (!isSchedulingEnabled) {
            log.info("Email scheduling is disabled.");
            return;
        }

        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                String emailBody = generateEmailBody(user);
                sendEmail(user.getEmail(), "Monthly Rent Due Notification", emailBody);
            }
        }
    }

    private String generateEmailBody(User user) {
        RentDetails rentDetails = rentInfoRepository.findByUserId(user.getId());

        String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Hello ").append(user.getUsername()).append(",\n\n");

        if (rentDetails == null || rentDetails.getAmount() == null) {
            emailBody.append("No rent amount information is available for ").append(currentMonth).append(".\n");
        } else {
            emailBody.append("Your rent for ").append(currentMonth).append(" is due:\n")
                    .append("Amount: ").append(rentDetails.getAmount())
                    .append("\n");
        }

        emailBody.append("\nIf already paid, please ignore this email.\n")
                .append("Please ensure timely payment. Thank you!\n\n")
                .append("Best regards,\n")
                .append("Team RentWise");

        return emailBody.toString();
    }

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
        }
    }
}
