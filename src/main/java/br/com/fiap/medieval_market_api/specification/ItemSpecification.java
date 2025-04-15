package br.com.fiap.medieval_market_api.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.medieval_market_api.controller.ItemController.ItemFilter;
import br.com.fiap.medieval_market_api.model.Item;
import jakarta.persistence.criteria.Predicate;

public class ItemSpecification {
    public static Specification<Item> withFilters(ItemFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nomeItem() != null && !filter.nomeItem().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("nomeItem")), "%" + filter.nomeItem().toLowerCase() + "%")
                );
            }

            if (filter.tipo() != null) {
                predicates.add(
                    cb.equal(root.get("tipo"), filter.tipo())
                );
            }

            if (filter.raridade() != null) {
                predicates.add(
                    cb.equal(root.get("raridade"), filter.raridade())
                );
            }

            if (filter.precoMin() != null && filter.precoMax() != null) {
                predicates.add(
                    cb.between(root.get("preco"), filter.precoMin(), filter.precoMax())
                );
            }

            if (filter.precoMin() != null && filter.precoMax() == null) {
                predicates.add(
                    cb.equal(root.get("preco"), filter.precoMin())
                );
            }

            if (filter.precoMin() == null && filter.precoMax() != null) {
                predicates.add(
                    cb.equal(root.get("preco"), filter.precoMax())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
