package br.com.ord.ORD.Controller.api;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/logout")
public class LogoutApiController {
    @PostMapping
    public ResponseEntity<?> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok(
                Map.of("sucesso", true, "mensagem", "Logout realizado com sucesso", "redirect", "/login"));
    }
}