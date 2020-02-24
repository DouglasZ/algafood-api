package com.projetos.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.model.Restaurante;
import com.projetos.algafood.domain.repository.CozinhaRepository;
import com.projetos.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome( String nome )
	{
		return cozinhaRepository.findByNomeContaining( nome );
	}

	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete( BigDecimal taxaInicial, BigDecimal taxaFinal )
	{
		return restauranteRepository.findByTaxaFreteBetween( taxaInicial, taxaFinal );
	}

	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorNome( String nome, Long cozinhaId )
	{
		return restauranteRepository.consultarPorNome( nome, cozinhaId );
	}

	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome( String nome )
	{
		return restauranteRepository.findFirstByNomeContaining( nome );
	}

	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome( String nome )
	{
		return restauranteRepository.findTop2ByNomeContaining( nome );
	}

	@GetMapping("/cozinhas/exists-por-nome")
	public boolean cozinhaExistsPorNome( String nome )
	{
		return cozinhaRepository.existsByNome( nome );
	}

	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha( Long cozinhaId )
	{
		return restauranteRepository.countByCozinhaId( cozinhaId );
	}
}
