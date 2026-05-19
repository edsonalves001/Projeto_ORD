package br.com.ord.ORD.Controller;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class esqueciSenhaController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/esqueci-senha")
    public String esqueciSenha(){
        return "esqueciSenha";
    }

    @PostMapping("/esqueci-senha")
    public String enviarRecuperacao(@RequestParam String email, RedirectAttributes ra
    ){
        Usuario usuario =  usuarioRepository.findByEmail(email);
        if(usuario == null){
            ra.addFlashAttribute("erro", "Não existe conta com esse email");
            return "redirect:/esqueci-senha";
        }
        String token = UUID.randomUUID().toString();
        usuario.setTokenVerificacao(token);
        usuarioRepository.save(usuario);
        String link = "http://localhost:8080/nova-senha?token=" + token;
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(email);
        mensagem.setSubject("Recuperação de senha");
        mensagem.setText("Clique no link abaixo para redefinir sua senha:\n\n" + link);
        emailSender.send(mensagem);
        ra.addFlashAttribute("mensagem", "Um email de recuperação foi enviado");
        return "redirect:/login";
    }

    @GetMapping("/nova-senha")
    public String novaSenha(@RequestParam String token, Model model){
        model.addAttribute("token", token);
        return "novaSenha";
    }

    @PostMapping("/nova-senha")
    public String atualizarSenha(@RequestParam String token, @RequestParam String senha, @RequestParam String senha2, RedirectAttributes ra, Model model){
        List<String> erros = new ArrayList<>();
        Usuario usuario = usuarioRepository.findByTokenVerificacao(token);
        if(usuario == null){
            erros.add("Token inválido");
        }
        if(senha.length() < 8){
            erros.add("A senha deve ter pelo menos 8 caracteres");
        }
        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temMinuscula = senha.matches(".*[a-z].*");
        boolean temNumero = senha.matches(".*\\d.*");
        boolean temEspecial = senha.matches(".*[@#$%^&+=!].*");
        if(!temMaiuscula){
            erros.add("A senha precisa ter letra maiúscula");
        }
        if(!temMinuscula){
            erros.add("A senha precisa ter letra minúscula");
        }
        if(!temNumero){
            erros.add("A senha precisa ter número");
        }
        if(!temEspecial){
            erros.add("A senha precisa ter caractere especial");
        }
        if(!senha.equals(senha2)){
            erros.add("As senhas não coincidem");
        }
        if(!erros.isEmpty()){
            model.addAttribute("erros", erros);
            model.addAttribute("token", token);
            return "novaSenha";
        }
        ra.addFlashAttribute("sucesso", "Senha alterada com sucesso");
        usuario.setSenha(senha);
        usuario.setTokenVerificacao(null);
        usuarioRepository.save(usuario);
        ra.addFlashAttribute("sucesso", "Senha atualizada com sucesso");
        return "redirect:/login";
    }
}
