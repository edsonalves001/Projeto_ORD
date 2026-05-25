package br.com.ord.ORD.repository;
import br.com.ord.ORD.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByEmail(String email);
    @Override
    Optional<Usuario> findById(String id);
    Usuario findByTokenVerificacao(String tokenVerificacao);
    Usuario findByNome(String nome);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

}

