package com.projetos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
