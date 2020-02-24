package com.projetos.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>
{
	List<Restaurante> findByTaxaFreteBetween( BigDecimal taxaInicial, BigDecimal tavaFinal );

	List<Restaurante> findByNomeContainingAndCozinhaId( String nome, Long conzinhaId );

	Optional<Restaurante> findFirstByNomeContaining( String nome );

	List<Restaurante> findTop2ByNomeContaining( String nome );

	int countByCozinhaId( Long cozinhaId);
}
