package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projetos.algafood.domain.exception.EntidadeEmUsoException;
import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.model.Cidade;
import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.model.Estado;
import com.projetos.algafood.domain.repository.CidadeRepository;
import com.projetos.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService
{
	public static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com o código %d.";
	public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	public Cidade salvar( Cidade cidade )
	{
		Long estadoId = cidade.getEstado().getId();
		
		Estado estado = cadastroEstado.buscar( estadoId );

		cidade.setEstado( estado );

		return cidadeRepository.save( cidade );
	}

	public void excluir( Long cidadeId )
	{
		try
		{
			cidadeRepository.deleteById( cidadeId );
		}
		catch ( EmptyResultDataAccessException e )
		{
			throw new EntidadeNaoEncontradaException(
					String.format( MSG_CIDADE_NAO_ENCONTRADA, cidadeId )
			);
		}
		catch ( DataIntegrityViolationException e )
		{
			throw new EntidadeEmUsoException(
					String.format( MSG_CIDADE_EM_USO, cidadeId ) );
		}
	}

	public Cidade buscar( Long cidadeId )
	{
		return cidadeRepository.findById( cidadeId )
				.orElseThrow( () -> new EntidadeNaoEncontradaException(
						String.format( MSG_CIDADE_NAO_ENCONTRADA, cidadeId )
				) );
	}
}
