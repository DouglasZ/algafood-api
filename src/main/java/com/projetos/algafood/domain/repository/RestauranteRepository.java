package com.projetos.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.projetos.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>
{
    List<Restaurante> findByTaxaFreteBetween( BigDecimal taxaInicial, BigDecimal tavaFinal );

    //	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome( String nome, @Param("id") Long conzinhaId );

//	List<Restaurante> findByNomeContainingAndCozinhaId( String nome, Long conzinhaId );

    Optional<Restaurante> findFirstByNomeContaining( String nome );

    List<Restaurante> findTop2ByNomeContaining( String nome );

    int countByCozinhaId( Long cozinhaId );
}
