package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import br.com.ord.ORD.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CadastroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // CADASTRO
    @PostMapping("/cadastro")
    public String cadastro(@RequestParam String nome,
                           @RequestParam String email,
                           @RequestParam String senha,
                           @RequestParam String senha2,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        boolean podeSalvar = true;

        // EMAIL
        if (email.isEmpty()) {

            podeSalvar = false;

            redirectAttributes.addFlashAttribute(
                    "erroEmail",
                    "Email inválido"
            );
        }

        // EMAIL EXISTE
        if (usuarioRepository.findByEmail(email) != null) {

            podeSalvar = false;

            redirectAttributes.addFlashAttribute(
                    "erroEmail",
                    "Esse email já existe"
            );
        }

        // NOME
        if (nome.isEmpty() || nome.matches("\\d+")) {

            podeSalvar = false;

            redirectAttributes.addFlashAttribute(
                    "erroNome",
                    "Nome inválido"
            );
        }

        // SENHA
        if (senha.length() < 8) {

            podeSalvar = false;

            redirectAttributes.addFlashAttribute(
                    "erroSenha",
                    "Senha muito pequena"
            );
        }

        // SENHAS DIFERENTES
        if (!senha.equals(senha2)) {

            podeSalvar = false;

            redirectAttributes.addFlashAttribute(
                    "erroSenha",
                    "As senhas são diferentes"
            );
        }

        if (!podeSalvar) {
            return "redirect:/cadastro";
        }

        // CÓDIGO
        String codigo =
                UUID.randomUUID().toString();

        // SALVA TEMPORARIAMENTE
        session.setAttribute("nomeTemp", nome);

        session.setAttribute("emailTemp", email);

        session.setAttribute(
                "senhaTemp",
                encoder.encode(senha)
        );

        session.setAttribute(
                "codigoTemp",
                codigo
        );

        // ENVIA EMAIL
        emailService.enviarEmailVerificacao(
                email,
                codigo
        );

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Verifique seu email"
        );

        return "redirect:/login";
    }

    // VERIFICAÇÃO
    @GetMapping("/verificar")
    public String verificar(@RequestParam String codigo,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        String codigoTemp =
                (String) session.getAttribute(
                        "codigoTemp"
                );

        if (codigoTemp == null) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Sessão expirada"
            );

            return "redirect:/cadastro";
        }

        if (codigoTemp.equals(codigo)) {

            Usuario usu = new Usuario();

            usu.setNome(
                    (String) session.getAttribute(
                            "nomeTemp"
                    )
            );

            usu.setEmail(
                    (String) session.getAttribute(
                            "emailTemp"
                    )
            );

            usu.setSenha(
                    (String) session.getAttribute(
                            "senhaTemp"
                    )
            );

            usu.setStatus_verificado(true);

            usu.setCaminhoIconPerfil(
                    "../assets/Perfil_basico.png"
            );

            usuarioRepository.save(usu);

            session.invalidate();

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Cadastro concluído"
            );

            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute(
                "erro",
                "Código inválido"
        );

        return "redirect:/cadastro";
    }
}