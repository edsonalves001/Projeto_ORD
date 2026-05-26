package br.com.ord.ORD.repository;

import br.com.ord.ORD.model.Atividade;
import br.com.ord.ORD.model.Questao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface AtividadeRepository extends MongoRepository<Atividade,String> {
    List<Atividade> findByDificuldade(String dificuldade);
    Atividade findByNome(String nome);

    @Update("{ '$push': { 'questoes' : ?1 } }")
    long findAndPushQuestoesById(String id, Questao questao);
}