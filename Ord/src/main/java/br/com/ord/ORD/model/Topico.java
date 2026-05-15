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
@Document(collection = "topico_ORD")
public class Topico {
    @Id
    private String id;
    private String nome;
    private String caminhoIcone;
    private String descricao;
    private List<Nivel> niveis;
}