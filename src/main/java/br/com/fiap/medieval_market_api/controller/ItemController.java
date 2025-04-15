package br.com.fiap.medieval_market_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/itens")
@Slf4j
public class ItemController {

    @Autowired
    private ItemRepository repository;
    
    @GetMapping
    @Cacheable("itens")
    // @Operation(description = "Listar todos os personagens", tags = "personagens", summary = "Lista de personagens")
    public List<Item> getItens() {
        log.info("Buscando todos os itens");
        return repository.findAll();
    }
    
    @PostMapping
    @CacheEvict(value = "itens", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    // @Operation(responses = { @ApiResponse(responseCode = "400", description = "Falha na validação")})
    public Item createItem(@RequestBody @Valid Item item) {
        log.info("Cadastrando item " + item.getNomeItem());
        return repository.save(item);
    }

    @GetMapping("{idItem}")
    public Item get(@PathVariable Long idItem) {
        log.info("Buscando item " + idItem);
        return getItem(idItem);
    }
    
    @DeleteMapping("{idItem}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyItem(@PathVariable Long idItem) {
        log.info("Apagando item " + idItem);
        repository.delete(getItem(idItem));
    }

    @PutMapping("{idItem}")
    public Item updateItem(@PathVariable Long idItem, @RequestBody @Valid Item item) {
        log.info("Atualizando item " + idItem + " " + item);
        getItem(idItem);
        item.setIdItem(idItem);
        return repository.save(item);
    }

    private Item getItem(Long idItem) {
        return repository.findById(idItem)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Item não encontrado"
            )
        );
    }
}
