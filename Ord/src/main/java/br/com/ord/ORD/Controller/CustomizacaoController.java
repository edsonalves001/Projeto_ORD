package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class CustomizacaoController {
    @Autowired
    private  UsuarioRepository usuarioRepository;

    @GetMapping("/customizacao")
    public String loja() {
        return "customizacao";
    }

    @PostMapping("/usuario/trocar-foto")
    @ResponseBody
    public String trocarFoto(@RequestBody Map<String, String> body,
                             HttpSession session){

        Usuario usuarioLogado =
                (Usuario) session.getAttribute("usuarioLogado");

        usuarioLogado.setCaminhoIconPerfil(body.get("caminho"));

        usuarioRepository.save(usuarioLogado);

        session.setAttribute("usuarioLogado", usuarioLogado);

        return "Foto alterada";
    }
}
