package br.com.fiap.medieval_market_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Personagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo obrigatório")
    private String nome;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private ClassePersonagem classe;

    @NotNull(message = "campo obrigatório")
    @Min(1)
    @Max(99)
    private Integer nivel;

    @PositiveOrZero(message = "deve ser maior ou igual a zero")
    private BigDecimal moedas;
}
