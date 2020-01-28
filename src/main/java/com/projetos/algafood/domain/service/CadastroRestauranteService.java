package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.model.Restaurante;
import com.projetos.algafood.domain.repository.CozinhaRepository;
import com.projetos.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService
{
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar( Restaurante restaurante )
	{
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar( cozinhaId );

		if ( cozinha == null )
		{
			throw new EntidadeNaoEncontradaException(
					String.format( "Não existe cadastro de cozinha com código %d", cozinhaId )
			);
		}

		restaurante.setCozinha( cozinha );

		return restauranteRepository.salvar( restaurante );
	}
}
