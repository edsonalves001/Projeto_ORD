package br.com.ord.ORD.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtividadesUsuario {
    private String atividadeID;
    private boolean concluida;
}
