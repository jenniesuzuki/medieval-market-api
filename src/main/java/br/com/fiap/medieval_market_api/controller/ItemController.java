package br.com.fiap.medieval_market_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.medieval_market_api.repository.ItemRepository;

@RestController
@RequestMapping("/itens")
public class ItemController {
    
    @Autowired
    private ItemRepository repository;

    
}
