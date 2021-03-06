package com.projetos.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projetos.algafood.api.controller.model.CozinhasXmlWrapper;
import com.projetos.algafood.domain.model.Cozinha;
import com.projetos.algafood.domain.repository.CozinhaRepository;
import com.projetos.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController
{
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping
	public List<Cozinha> listar()
	{
		return cozinhaRepository.findAll();
	}

//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public CozinhasXmlWrapper listarXml()
//	{
//		return new CozinhasXmlWrapper( cozinhaRepository.findAll() );
//	}

	@GetMapping("/{cozinhaId}")
	public Cozinha buscar( @PathVariable Long cozinhaId )
	{
		return cadastroCozinha.buscar( cozinhaId );
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar( @RequestBody Cozinha cozinha )
	{
		return cadastroCozinha.salvar( cozinha );
	}

	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar( @PathVariable Long cozinhaId, @RequestBody Cozinha cozinha )
	{
		Cozinha cozinhaAtual = cadastroCozinha.buscar( cozinhaId );

		BeanUtils.copyProperties( cozinha, cozinhaAtual, "id" );

		return cadastroCozinha.salvar( cozinhaAtual );
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover( @PathVariable Long cozinhaId )
	{
		cadastroCozinha.excluir( cozinhaId );
	}
}
