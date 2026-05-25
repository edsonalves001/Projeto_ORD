package br.com.ord.ORD.repository;

import br.com.ord.ORD.model.Atividade;
import br.com.ord.ORD.model.Questao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;

import java.awt.geom.QuadCurve2D;
import java.util.List;

public interface AtividadeRepository extends MongoRepository<Atividade,String> {
    List<Atividade> findByDificuldade(String dificuldade);

    @Update("{ '$push': { 'questoes' : ?1 } }")
    long findAndPushQuestoesById(String id, Questao questao);
}
