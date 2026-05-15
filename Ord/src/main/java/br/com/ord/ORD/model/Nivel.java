package br.com.ord.ORD.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nivel {
    private String nome;
    private String descricao;
    private List<AtividadesNivel> atividades;
}