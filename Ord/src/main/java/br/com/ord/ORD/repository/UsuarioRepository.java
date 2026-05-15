package br.com.ord.ORD.repository;
import br.com.ord.ORD.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
        Usuario findByEmail(String email);
        Usuario findBycodigoverificado(String codigo);

}

