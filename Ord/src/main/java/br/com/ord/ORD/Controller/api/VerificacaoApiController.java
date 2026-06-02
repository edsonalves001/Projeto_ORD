package br.com.ord.ORD.Controller.api;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/verificacao")
public class VerificacaoApiController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/verificar")
    public String verificar(
            @RequestParam String token,
            RedirectAttributes redirectAttributes) {

        System.out.println("TOKEN RECEBIDO: " + token);

        Usuario usu =
                usuarioRepository.findByTokenVerificacao(token);

        if (usu == null) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Token inválido"
            );

            return "redirect:/login";
        }

        usu.setVerificado(true);

        usuarioRepository.save(usu);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Conta verificada com sucesso"
        );

        return "redirect:/login";
    }
}