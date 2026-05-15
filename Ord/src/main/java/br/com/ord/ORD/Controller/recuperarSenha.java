package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import br.com.ord.ORD.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;
@Controller
public class recuperarSenha{
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioRepository usuarioRepository;
@PostMapping("/recuperarSenha")
public String recuperarSenha(@RequestParam String email, RedirectAttributes redirectAttributes) {
    Usuario usu = usuarioRepository.findByEmail(email);
    if (usu == null) {
        redirectAttributes.addFlashAttribute(
                "erro",
                "Email não encontrado"
        );

        return "redirect:/esqueciSenha";
    }

    String codigo =
            UUID.randomUUID().toString();

    usu.setCodigoverificado(codigo);

    usuarioRepository.save(usu);
    emailService.enviarRecuperacaoSenha(email,codigo);

    redirectAttributes.addFlashAttribute(
            "mensagem",
            "Email enviado"
    );

    return "redirect:/login";
}

    @PostMapping("/novaSenha")
    public String novaSenha(@RequestParam String codigo,
                            @RequestParam String senha) {

        Usuario usu =
                usuarioRepository
                        .findBycodigoverificado(codigo);

        if (usu != null) {

            BCryptPasswordEncoder encoder =
                    new BCryptPasswordEncoder();

            usu.setSenha(
                    encoder.encode(senha)
            );

            usu.setCodigoverificado(null);

            usuarioRepository.save(usu);
        }

        return "redirect:/login";
    }
}