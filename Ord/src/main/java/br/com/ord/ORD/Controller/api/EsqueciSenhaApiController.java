package br.com.ord.ORD.Controller.api;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EsqueciSenhaApiController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JavaMailSender emailSender;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> enviarRecuperacao(@RequestBody Map<String,String> dados){
        String email = dados.get("email");
        Usuario usuario = usuarioRepository.findByEmail(email);
        if(usuario == null){
            return ResponseEntity.badRequest().body(
                    Map.of("sucesso", false, "erros", List.of("Não existe conta com esse email")));
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
        return ResponseEntity.ok(
                Map.of("sucesso", true, "mensagem", "Um email de recuperação foi enviado", "redirect", "/login"));
    }

    @PostMapping("/nova-senha")
    public ResponseEntity<?> atualizarSenha(@RequestBody Map<String,String> dados){
        String token = dados.get("token");
        String senha = dados.get("senha");
        String senha2 = dados.get("senha2");
        List<String> erros = new ArrayList<>();
        Usuario usuario = usuarioRepository.findByTokenVerificacao(token);
        if(usuario == null){
            erros.add("Token inválido");
        }

        if(senha.length() < 8){
            erros.add("A senha deve ter pelo menos 8 caracteres");
        }
        if(!senha.matches(".*[A-Z].*")){
            erros.add("A senha precisa ter letra maiúscula");
        }

        if(!senha.matches(".*[a-z].*")){
            erros.add("A senha precisa ter letra minúscula");
        }

        if(!senha.matches(".*\\d.*")){
            erros.add("A senha precisa ter número");
        }

        if(!senha.matches(".*[@#$%^&+=!].*")){
            erros.add("A senha precisa ter caractere especial");
        }

        if(!senha.equals(senha2)){
            erros.add("As senhas não coincidem");
        }

        if(!erros.isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.of("sucesso", false, "erros", erros));
        }

        usuario.setSenha(encoder.encode(senha));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(
                Map.of("sucesso", true, "mensagem", "Senha atualizada com sucesso", "redirect", "/login"));
    }
}