package com.projetos.algafood.domain.exception;

public class EntidadeEmUsoException extends NegocioException
{
	public EntidadeEmUsoException( String mensagem )
	{
		super( mensagem );
	}

	public EntidadeEmUsoException( Class<?> entidade, Long estadoId )
	{
		this( String.format( "%s de código %d não pode ser removida, pois está em uso.", entidade.getSimpleName(), estadoId ) );
	}
}
