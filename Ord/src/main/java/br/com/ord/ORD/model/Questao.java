package br.com.ord.ORD.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Questao {
    private String enunciado;
    private String ensino;
    private String tipo;
    private boolean concluido;
    private List<Alternativa> alternativas;
}
