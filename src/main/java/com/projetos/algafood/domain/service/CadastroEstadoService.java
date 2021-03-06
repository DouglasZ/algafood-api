package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projetos.algafood.domain.exception.EntidadeEmUsoException;
import com.projetos.algafood.domain.exception.EstadoNaoEncontradoException;
import com.projetos.algafood.domain.model.Estado;
import com.projetos.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService
{
	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar( Estado estado )
	{
		return estadoRepository.save( estado );
	}

	public void excluir( Long estadoId )
	{
		try
		{
			estadoRepository.deleteById( estadoId );
		}
		catch ( EmptyResultDataAccessException e )
		{
			throw new EstadoNaoEncontradoException( estadoId );
		}
		catch ( DataIntegrityViolationException e )
		{
			throw new EntidadeEmUsoException( Estado.class, estadoId );
		}
	}

	public Estado buscar( Long estadoId )
	{
		return estadoRepository.findById( estadoId )
				.orElseThrow( () -> new EstadoNaoEncontradoException( estadoId ) );
	}
}
