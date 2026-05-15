package br.com.ord.ORD.repository;

import br.com.ord.ORD.model.Topico;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicoRepository extends MongoRepository<Topico,String> {
}
