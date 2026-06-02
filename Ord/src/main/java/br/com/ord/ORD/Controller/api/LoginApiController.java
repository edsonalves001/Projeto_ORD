package br.com.ord.ORD.Controller.api;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/login")
public class LoginApiController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String,String> dados, HttpSession session){
        List<String> erros = new ArrayList<>();
        String email = dados.get("email");
        String senha = dados.get("senha");
        if(email == null || email.trim().isEmpty()){
            erros.add("O email não pode estar vazio");
        }
        else if(!email.contains("@")){
            erros.add("Email inválido");
        }

        if(senha == null || senha.trim().isEmpty()){
            erros.add("A senha não pode estar vazia");
        }
        Usuario usuario = usuarioRepository.findByEmail(email);

        if(usuario == null){
            erros.add("Não existe conta com esse email");
        }
        if(!erros.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("sucesso", false, "erros", erros));
        }

        if(!encoder.matches(senha, usuario.getSenha()
        )){
            return ResponseEntity.badRequest().body(
                    Map.of("sucesso", false, "erros", List.of("Senha incorreta")));
        }

        if(!usuario.getVerificado()){
            return ResponseEntity.badRequest().body(Map.of("sucesso", false, "erros", List.of("Conta ainda não verificada")));
        }
        session.setAttribute("usuarioId", usuario.getId()
        );

        return ResponseEntity.ok(Map.of("sucesso", true, "mensagem", "Login realizado", "redirect", "/home"));
    }
}