package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projetos.algafood.domain.exception.EntidadeEmUsoException;
import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar( Cozinha cozinha )
	{
		return cozinhaRepository.salvar( cozinha );
	}

	public void excluir( Long conzinhaId )
	{
		try
		{
			cozinhaRepository.remover( conzinhaId );
		}
		catch ( EmptyResultDataAccessException e )
		{
			throw new EntidadeNaoEncontradaException(
					String.format( "Não existe um cadastro de cozinha com o código %d.", conzinhaId )
			);
		}
		catch ( DataIntegrityViolationException e )
		{
			throw new EntidadeEmUsoException(
					String.format( "Cozinha de código %d não pode ser removida, pois está em uso.", conzinhaId )
			);
		}
	}
}