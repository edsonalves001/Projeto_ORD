package br.com.ord.ORD.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private boolean status_verificado;
    private String codigoverificado;
    private String senha;


}

