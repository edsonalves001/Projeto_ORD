package br.com.ord.ORD.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "usuario_ORD")
public class Usuario {
    @Id
    private String id;
    private String nome;
    @Indexed(unique = true)
    private String email;
    private Integer idade;
    private String caminhoIconPerfil;
    private String caminhoWallpaper;
    private String senha;
    private String emailResponsavel;
    private List<AtividadesUsuario> atividades;
    private Boolean verificado;
    private String tokenVerificacao;
    private int score;
    private int streak;
    private List<String> AmigosUsuario;
    private int Vidas;
}