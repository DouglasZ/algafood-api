package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projetos.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.projetos.algafood.domain.exception.EntidadeEmUsoException;
import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar( Cozinha cozinha )
	{
		return cozinhaRepository.save( cozinha );
	}

	public void excluir( Long cozinhaId )
	{
		try
		{
			cozinhaRepository.deleteById( cozinhaId );
		}
		catch ( EmptyResultDataAccessException e )
		{
			throw new CozinhaNaoEncontradaException( cozinhaId );
		}
		catch ( DataIntegrityViolationException e )
		{
			throw new EntidadeEmUsoException( Cozinha.class, cozinhaId );
		}
	}

	public Cozinha buscar( Long cozinhaId )
	{
		return cozinhaRepository.findById( cozinhaId )
				.orElseThrow( () -> new CozinhaNaoEncontradaException( cozinhaId ) );
	}
}
