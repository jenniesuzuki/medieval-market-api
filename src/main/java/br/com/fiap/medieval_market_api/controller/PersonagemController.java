package br.com.fiap.medieval_market_api.controller;

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

import br.com.fiap.medieval_market_api.model.ClassePersonagem;
import br.com.fiap.medieval_market_api.model.Personagem;
import br.com.fiap.medieval_market_api.repository.PersonagemRepository;
import br.com.fiap.medieval_market_api.specification.PersonagemSpecification;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/personagens")
@Slf4j
public class PersonagemController {

    public record PersonagemFilter(String nome, ClassePersonagem classe) {
    }
    
    @Autowired
    private PersonagemRepository repository;

    @GetMapping
    @Cacheable("personagens")
    // @Operation(description = "Listar todos os personagens", tags = "personagens", summary = "Lista de personagens")
    public Page<Personagem> getPersonagens(PersonagemFilter filter, @PageableDefault(size = 5, sort = "nome")  Pageable pageable) {
        var specification = PersonagemSpecification.withFilters(filter);
        return repository.findAll(specification, pageable);
    }
    
    @PostMapping
    @CacheEvict(value = "personagens", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    // @Operation(responses = { @ApiResponse(responseCode = "400", description = "Falha na validação")})
    public Personagem createPersonagem(@RequestBody @Valid Personagem personagem) {
        log.info("Cadastrando personagem " + personagem.getNome());
        return repository.save(personagem);
    }

    @GetMapping("{id}")
    public Personagem get(@PathVariable Long id) {
        log.info("Buscando personagem " + id);
        return getPersonagem(id);
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyPersonagem(@PathVariable Long id) {
        log.info("Apagando personagem " + id);
        repository.delete(getPersonagem(id));
    }

    @PutMapping("{id}")
    public Personagem updatePersonagem(@PathVariable Long id, @RequestBody @Valid Personagem personagem) {
        log.info("Atualizando personagem " + id + " " + personagem);
        getPersonagem(id);
        personagem.setId(id);
        return repository.save(personagem);
    }

    private Personagem getPersonagem(Long id) {
        return repository.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Personagem não encontrado"
            )
        );
    }
}
