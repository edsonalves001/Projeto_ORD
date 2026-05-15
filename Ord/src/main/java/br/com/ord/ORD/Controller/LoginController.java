package br.com.ord.ORD.Controller;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String OrdLogin(Model model) {
         return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session,
                        RedirectAttributes ra) {

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (email.isEmpty() || senha.isEmpty()) {
            ra.addFlashAttribute("erro", "Preencha todos os campos");
            return "redirect:/login";

        } else if (!email.contains("@")) {
            ra.addFlashAttribute("erro", "Email inválido");
            return "redirect:/login";

        } else if (usuario != null && usuario.getSenha().equals(senha)) {
            session.setAttribute("usuarioId", usuario.getId());
            return "redirect:/home";
        }

        ra.addFlashAttribute("erro", "Email ou senha incorretos");
        return "redirect:/login";
    }
@DeleteMapping("/delete")
    public String deletarUsuario(String usu_id){
        usuarioRepository.deleteById(usu_id);
        return "redirect:/login";
}
}
