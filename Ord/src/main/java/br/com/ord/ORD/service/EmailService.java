package br.com.ord.ORD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void enviarEmail() {

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom("edsonpg.alves@gmail.com");
        mensagem.setTo("edsonpg.alves@gmail.com");
        mensagem.setSubject("Teste");
        mensagem.setText("Email enviado pelo Spring Boot");

        emailSender.send(mensagem);
    }
}