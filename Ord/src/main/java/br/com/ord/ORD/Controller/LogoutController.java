package br.com.ord.ORD.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class LogoutController {

    @PostMapping("/logout/{id}")
    public String apagarSessao(
            @PathVariable String id,
            HttpSession session) {

        String usuarioId = (String) session.getAttribute("usuarioId");

        // Verifica se a sessão pertence ao usuário
        if(usuarioId != null && usuarioId.equals(id)) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}