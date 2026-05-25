package br.com.ord.ORD.repository;

import br.com.ord.ORD.model.Nivel;
import br.com.ord.ORD.model.Topico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface TopicoRepository extends MongoRepository<Topico,String> {
    @Update("{ '$push': { 'niveis' : ?1 } }")
    long findAndPushNivelById(String id, Nivel nivel);
}
