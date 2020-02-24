package com.projetos.algafood.domain.repository;

import java.util.List;

import com.projetos.algafood.domain.model.Cozinha;

public interface CozinhaRepository
{
	List<Cozinha> listar();

	List<Cozinha> consultarPorNome( String Nome );

	Cozinha buscar( Long id );

	Cozinha salvar( Cozinha cozinha );

	void remover( Long id );
}
