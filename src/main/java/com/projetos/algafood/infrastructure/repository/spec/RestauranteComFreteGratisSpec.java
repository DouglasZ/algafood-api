package com.projetos.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.projetos.algafood.domain.model.Restaurante;

public class RestauranteComFreteGratisSpec implements Specification<Restaurante>
{
    @Override
    public Predicate toPredicate( Root<Restaurante> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder )
    {
        return builder.equal( root.get( "taxaFrete" ), BigDecimal.ZERO );
    }
}
