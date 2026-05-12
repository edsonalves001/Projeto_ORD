package br.com.ord.ORD.config;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
//esse arquivo é um controller universal, que vai servir para não precisarmos repetir código
//O GlobalControllerAdvice vai pegar a sessão com o id do usuário e criar um objeto do tipo usuário(JPA) usando o repository usuário, e graças ao ModelAttribute, esse objeto poderá ser usado em todas as paginas

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @ModelAttribute
    public void adicionarUsuario(HttpSession session, Model model) {

        String alunoId = (String) session.getAttribute("usuarioId");


        if (alunoId != null) {
            if (!model.containsAttribute("usuarioLogado")) {
                Usuario usuario = usuarioRepository.findById(alunoId).orElse(null);
                model.addAttribute("usuarioLogado", usuario);
            }
        }
    }
}

