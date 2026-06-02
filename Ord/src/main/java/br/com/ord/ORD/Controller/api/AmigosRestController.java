package br.com.ord.ORD.Controller.api;
import br.com.ord.ORD.dto.ApiResponse;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/amigos")
public class AmigosRestController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/{amigoId}")
    public ResponseEntity<?> adicionarAmigo(@PathVariable String amigoId, HttpSession session){
        String usuarioId = (String) session.getAttribute("usuarioId");

        if(usuarioId == null){
            return ResponseEntity.status(401).body(
                    new ApiResponse(false, "Usuário não logado"));
        }

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        Usuario amigo = usuarioRepository.findById(amigoId).orElse(null);

        if(usuario == null || amigo == null){
            return ResponseEntity.badRequest().body(
               new ApiResponse(false, "Usuário não encontrado"));}

        if(!usuario.getAmigosUsuario().contains(amigoId)){
            usuario.getAmigosUsuario().add(amigoId);
        }

        if(!amigo.getAmigosUsuario().contains(usuarioId)){
            amigo.getAmigosUsuario().add(usuarioId);
        }

        usuarioRepository.save(usuario);
        usuarioRepository.save(amigo);

        return ResponseEntity.ok(
                new ApiResponse(true, "Amigo adicionado com sucesso"
                )
        );
    }

    @DeleteMapping("/{amigoId}")
    public ResponseEntity<?> removerAmigo(
            @PathVariable String amigoId,
            HttpSession session){

        String usuarioId = (String) session.getAttribute("usuarioId");

        if(usuarioId == null){
            return ResponseEntity.status(401).body(new ApiResponse(false, "Usuário não logado"));
        }

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        Usuario amigo = usuarioRepository.findById(amigoId).orElse(null);

        if(usuario == null || amigo == null){
            return ResponseEntity.badRequest().body(
                   new ApiResponse(
                    false, "Usuário não encontrado"));
        }

        usuario.getAmigosUsuario().remove(amigoId);
        amigo.getAmigosUsuario().remove(usuarioId);

        usuarioRepository.save(usuario);
        usuarioRepository.save(amigo);

        return ResponseEntity.ok(
                new ApiResponse(true, "Amigo removido"
                )
        );
    }
}