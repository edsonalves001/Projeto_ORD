package br.com.ord.ORD.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AmigosUsuarioModel {

    private String id;
    private String Nome;
    private Integer Score;

}