package br.com.ord.ORD.Controller;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CustomizacaoController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping("/customizacao")
    public String loja() {
        return "customizacao";
    }
    @PostMapping("/usuario/trocar-foto")
    public String trocarFoto(
            @RequestParam("caminho") String caminho,
            HttpSession session
    ){

        String usuarioId =
                (String) session.getAttribute("usuarioId");


        Usuario usuarioLogado =
                usuarioRepository.findById(usuarioId)
                        .orElse(null);

        if(usuarioLogado == null){

            return "redirect:/login";

        }

        usuarioLogado.setCaminhoIconPerfil(caminho);

        usuarioRepository.save(usuarioLogado);

        return "redirect:/customizacao";
    }
}
