package br.com.fiap.medieval_market_api.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.medieval_market_api.model.ClassePersonagem;
import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.model.Personagem;
import br.com.fiap.medieval_market_api.model.RaridadeItem;
import br.com.fiap.medieval_market_api.model.TipoItem;
import br.com.fiap.medieval_market_api.repository.ItemRepository;
import br.com.fiap.medieval_market_api.repository.PersonagemRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {
    
    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        var personagens = List.of(
            Personagem.builder().nome("Marcos").classe(ClassePersonagem.ARQUEIRO).nivel(1).moedas(BigDecimal.valueOf(new Random().nextDouble() * 5000)).build(),
            Personagem.builder().nome("André").classe(ClassePersonagem.GUERREIRO).nivel(10).moedas(BigDecimal.valueOf(new Random().nextDouble() * 5000)).build(),
            Personagem.builder().nome("Yuri").classe(ClassePersonagem.MAGO).nivel(25).moedas(BigDecimal.valueOf(new Random().nextDouble() * 5000)).build(),
            Personagem.builder().nome("Vinícius").classe(ClassePersonagem.ARQUEIRO).nivel(70).moedas(BigDecimal.valueOf(new Random().nextDouble() * 5000)).build(),
            Personagem.builder().nome("Pedro").classe(ClassePersonagem.MAGO).nivel(30).moedas(BigDecimal.valueOf(new Random().nextDouble() * 5000)).build(),
            Personagem.builder().nome("Tiago").classe(ClassePersonagem.GUERREIRO).nivel(42).moedas(BigDecimal.valueOf(new Random().nextDouble() * 5000)).build()
        );

        personagemRepository.saveAll(personagens);

        var itens = List.of(
            Item.builder().nomeItem("Espada encantada").tipo(TipoItem.ARMA).raridade(RaridadeItem.COMUM).preco(BigDecimal.valueOf(new Random().nextDouble() * 500)).dono(personagens.get(new Random().nextInt(personagens.size()))).build(),
            Item.builder().nomeItem("Elixir de vida").tipo(TipoItem.POÇÃO).raridade(RaridadeItem.RARO).preco(BigDecimal.valueOf(new Random().nextDouble() * 500)).dono(personagens.get(new Random().nextInt(personagens.size()))).build(),
            Item.builder().nomeItem("Capacete de ametista").tipo(TipoItem.ARMADURA).raridade(RaridadeItem.ÉPICO).preco(BigDecimal.valueOf(new Random().nextDouble() * 500)).dono(personagens.get(new Random().nextInt(personagens.size()))).build(),
            Item.builder().nomeItem("Colar de mamute").tipo(TipoItem.ACESSÓRIO).raridade(RaridadeItem.LENDÁRIO).preco(BigDecimal.valueOf(new Random().nextDouble() * 500)).dono(personagens.get(new Random().nextInt(personagens.size()))).build()
        );
        itemRepository.saveAll(itens);
    }

}
