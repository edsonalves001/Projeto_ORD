package br.com.ord.ORD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void enviarEmailVerificacao(String destino, String codigo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destino);
        mensagem.setSubject(
                "Verificação de Email"
        );
        mensagem.setText("Clique no link abaixo:\n\n" + "http://localhost:8080/verificar?codigo=" + codigo);
        javaMailSender.send(mensagem);
    }

      public void enviarRecuperacaoSenha(String destino, String codigo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destino);
        mensagem.setSubject("Recuperação de Senha");
        mensagem.setText("Clique no link abaixo para recuperar sua senha:\n\n"+ "http://localhost:8080/novaSenha?codigo=" + codigo);
        javaMailSender.send(mensagem);
    }
}