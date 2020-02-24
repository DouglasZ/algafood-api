package com.projetos.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
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
		return restauranteRepository.findAll();
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar( @PathVariable Long restauranteId )
	{
		Optional<Restaurante> restaurante = restauranteRepository.findById( restauranteId );

		if ( restaurante.isPresent() )
		{
			return ResponseEntity.ok( restaurante.get() );
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
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById( restauranteId );

			if ( restauranteAtual.isPresent() )
			{
				BeanUtils.copyProperties( restaurante, restauranteAtual.get(), "id" );

				Restaurante restauranteSalvo = cadastroRestaurante.salvar( restauranteAtual.get() );
				return ResponseEntity.ok( restauranteSalvo );
			}

			return ResponseEntity.notFound().build();
		}
		catch ( EntidadeNaoEncontradaException e )
		{
			return ResponseEntity.badRequest().body( e.getMessage() );
		}
	}

	/**
	 * Método apenas para estudo de como usar o Patch
	 */
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial( @PathVariable Long restauranteId, @RequestBody Map<String, Object> campos )
	{
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById( restauranteId );

		if ( restauranteAtual.isEmpty() )
		{
			return ResponseEntity.notFound().build();
		}

		merge( campos, restauranteAtual.get() );

		return atualizar( restauranteId, restauranteAtual.get() );
	}

	private void merge( Map<String, Object> dadosOrigem, Restaurante restauranteDestino )
	{
		// Responsável por realizar a Serialização, ou seja, Objetos Java em JSON ou JSON em Objetos Java
		ObjectMapper objectMapper = new ObjectMapper(  );
		// Cria uma instância do tipo Restaurante, usando como base o mapa com os dados de origem
		Restaurante restauranteOrigem = objectMapper.convertValue( dadosOrigem, Restaurante.class );

		// Reflections: Torna possível a inspeção de objetos, chamada de métodos e alteração de atributos
		// em tempo de execução e de forma dinâmica.
		dadosOrigem.forEach( ( nomePropriedade, valorPropriedade ) -> {
			Field field = ReflectionUtils.findField( Restaurante.class, nomePropriedade );
			// Torna acessivel a variavel do objeto, porque ele está declarado como private.
			field.setAccessible( true );

			// Usamos os valores dos atributos convertidos pelo objectMapper, pois se usarmos o valorPropriedade,
			// temos problemas de incompatibilidades com o tipo dos atribudos declarado na classe.
			Object novoValor = ReflectionUtils.getField( field, restauranteOrigem );

//			System.out.println( nomePropriedade + " = " + valorPropriedade + " - " + novoValor);

			ReflectionUtils.setField( field, restauranteDestino, novoValor );
		} );
	}
}
