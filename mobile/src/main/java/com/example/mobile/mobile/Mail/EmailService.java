package com.example.mobile.mobile.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Reservation.Model.Reservation;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendReservationConfirmation(String email,String fullName, Reservation reservation) {
        String subject = "Confirmation de votre réservation";
        String body = "Bonjour " + fullName + ",\n\n" +
                "Merci pour votre réservation.\n\n" +
                "Spectacle : " + reservation.getRepresentation().getSpectacle().getTitre() + "\n" +
                "Date : " + reservation.getRepresentation().getDate() + "\n" +
                "Lieu : " + reservation.getRepresentation().getLieu() + "\n" +
                "Billets : " + reservation.getBillets().toString() + "\n" +
                "Nombre total : " + reservation.getNombreBillets() + "\n\n" +
                "À bientôt !";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
