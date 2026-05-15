package br.com.ord.ORD.repository;

import br.com.ord.ORD.model.Atividade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AtividadeRepository extends MongoRepository<Atividade,String> {
    List<Atividade> findByDificuldade(String dificuldade);
}
