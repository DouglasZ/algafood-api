package com.projetos.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.model.Restaurante;
import com.projetos.algafood.domain.repository.RestauranteRepository;
import com.projetos.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController
{
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public List<Restaurante> listar()
	{
		return restauranteRepository.listar();
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar( @PathVariable Long restauranteId )
	{
		Restaurante restaurante = restauranteRepository.buscar( restauranteId );

		if ( restaurante != null )
		{
			return ResponseEntity.ok( restaurante );
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	// ? = wildcard - Retorno gernérico. Pode ser um objeto, string, etc.
	public ResponseEntity<?> adicionar( @RequestBody Restaurante restaurante )
	{
		try
		{
			restaurante = cadastroRestaurante.salvar( restaurante );
			return ResponseEntity.status( HttpStatus.CREATED ).body( restaurante );
		}
		catch ( EntidadeNaoEncontradaException e )
		{
			return ResponseEntity.badRequest().body( e.getMessage() );
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar( @PathVariable Long restauranteId, @RequestBody Restaurante restaurante )
	{
		try
		{
			Restaurante restauranteAtual = restauranteRepository.buscar( restauranteId );

			if ( restauranteAtual != null )
			{
				BeanUtils.copyProperties( restaurante, restauranteAtual, "id" );

				restauranteAtual = cadastroRestaurante.salvar( restauranteAtual );
				return ResponseEntity.ok( restauranteAtual );
			}

			return ResponseEntity.notFound().build();
		}
		catch ( EntidadeNaoEncontradaException e )
		{
			return ResponseEntity.badRequest().body( e.getMessage() );
		}
	}
}
