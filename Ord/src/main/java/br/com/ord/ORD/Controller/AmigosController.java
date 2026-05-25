package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AmigosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/adicionar-amigo/{amigoId}")
    @ResponseBody
    public String adicionarAmigo(@PathVariable String amigoId,
                                 HttpSession session){

        String usuarioId =
                (String) session.getAttribute("usuarioId");

        if(usuarioId == null){
            return "Usuário não logado";
        }

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElse(null);

        Usuario amigo = usuarioRepository
                .findById(amigoId)
                .orElse(null);

        if(usuario == null || amigo == null){
            return "Usuário não encontrado";
        }

        // evita duplicação
        if(!usuario.getAmigosUsuario().contains(amigoId)){

            usuario.getAmigosUsuario().add(amigoId);

        }

        if(!amigo.getAmigosUsuario().contains(usuarioId)){

            amigo.getAmigosUsuario().add(usuarioId);

        }

        usuarioRepository.save(usuario);
        usuarioRepository.save(amigo);

        return "OK";
    }
    @PostMapping("/remover-amigo/{amigoId}")
    @ResponseBody
    public String removerAmigo(@PathVariable String amigoId,
                               HttpSession session){

        String usuarioId =
                (String) session.getAttribute("usuarioId");

        if(usuarioId == null){
            return "Usuário não logado";
        }

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElse(null);

        Usuario amigo = usuarioRepository
                .findById(amigoId)
                .orElse(null);

        if(usuario == null || amigo == null){
            return "Usuário não encontrado";
        }

        // remove o amigo do usuário logado
        usuario.getAmigosUsuario().remove(amigoId);

        // remove o usuário do amigo
        amigo.getAmigosUsuario().remove(usuarioId);

        usuarioRepository.save(usuario);
        usuarioRepository.save(amigo);

        return "Amigo removido, Bye bye";
    }
}
