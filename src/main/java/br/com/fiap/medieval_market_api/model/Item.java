package br.com.fiap.medieval_market_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    @NotBlank(message = "campo obrigatório")
    private String nomeItem;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoItem tipo;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private RaridadeItem raridade;

    @Positive(message = "deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "campo obrigatório")
    @ManyToOne
    private Personagem dono;
}
