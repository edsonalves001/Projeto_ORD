package br.com.ord.ORD.Controller.api;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioApiController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios/busca")
    public List<Usuario> buscarUsuarios(
            @RequestParam String nome){

        return usuarioRepository
                .findByNomeContainingIgnoreCase(nome);
    }
}