package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.model.Cidade;
import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.model.Restaurante;
import com.projetos.algafood.domain.repository.CozinhaRepository;
import com.projetos.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService
{
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com código %d";
	
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
				.orElseThrow( () -> new EntidadeNaoEncontradaException(
						String.format( MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId )
				) );
	}
}
