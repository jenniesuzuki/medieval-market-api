package br.com.fiap.medieval_market_api.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.model.RaridadeItem;
import br.com.fiap.medieval_market_api.model.TipoItem;
import br.com.fiap.medieval_market_api.repository.ItemRepository;
import br.com.fiap.medieval_market_api.specification.ItemSpecification;
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

    public record ItemFilter(String nomeItem, TipoItem tipo, BigDecimal precoMin, BigDecimal precoMax, RaridadeItem raridade) {
    }

    @Autowired
    private ItemRepository repository;
    
    @GetMapping
    @Cacheable("itens")
    // @Operation(description = "Listar todos os itens", tags = "itens", summary = "Lista de itens")
    public Page<Item> getItens(ItemFilter filter, @PageableDefault(size = 10, sort = "nomeItem") Pageable pageable) {
        var specification = ItemSpecification.withFilters(filter);
        //log.info("Buscando todos os itens");
        return repository.findAll(specification, pageable);
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
