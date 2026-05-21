package br.com.ord.ORD.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "atividade_ORD")
public class Atividade {
    @Id
    private String id;
    private String nome;
    private String dificuldade;
    private int scoreRecompensa;
    private boolean concluidoAtividade;
    private List<Questao> questoes;

    public Atividade(String nome, String dificuldade, int scoreRecompensa, boolean concluidoAtividade) {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.scoreRecompensa = scoreRecompensa;
        this.concluidoAtividade = concluidoAtividade;
    }
}
