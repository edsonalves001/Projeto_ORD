package br.com.ord.ORD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class emailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String OrdRemetente;
    public String enviarEmail(String destinatario) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(OrdRemetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject("teste");
            simpleMailMessage.setText("Mensagem");
            javaMailSender.send(simpleMailMessage);
            return "Email enviado";
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    return "";
}
}
