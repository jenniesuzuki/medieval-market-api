package br.com.fiap.medieval_market_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;
    private String nomeItem;
    private TipoItem tipo;
    private RaridadeItem raridade;
    private BigDecimal preco;
    private Personagem dono;
}
