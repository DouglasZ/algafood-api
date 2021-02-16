package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetos.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.model.Restaurante;
import com.projetos.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService
{
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public Restaurante salvar( Restaurante restaurante )
	{
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinha.buscar( cozinhaId );

		restaurante.setCozinha( cozinha );

		return restauranteRepository.save( restaurante );
	}

	public Restaurante buscar( Long restauranteId )
	{
		return restauranteRepository.findById( restauranteId )
				.orElseThrow( () -> new RestauranteNaoEncontradoException( restauranteId ) );
	}
}
