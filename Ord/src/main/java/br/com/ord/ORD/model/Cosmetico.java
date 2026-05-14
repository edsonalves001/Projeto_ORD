package br.com.ord.ORD.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cosmetico_ORD")
public class Cosmetico {
    @Id
    private String id;
    private String nome;
    private String tipo;
    private String caminhoImagem;
    private int score;
}
