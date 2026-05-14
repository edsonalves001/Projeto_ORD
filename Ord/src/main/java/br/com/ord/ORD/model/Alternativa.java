package br.com.ord.ORD.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alternativa {
    private String texto;
    private boolean correto;
}
