package com.projetos.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projetos.algafood.domain.exception.EntidadeEmUsoException;
import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.exception.NegocioException;

// Informa que este componente é possível adicionar ExceptionHandler que captura todas as exceções de todos os Controladores do projeto.
// Para que seja possível trata-las em um único local.
@ControllerAdvice
public class ApiExceptionHandler
{
	/**
	 * Método responsável por tratar todas as exceções que são desse tipo (EntidadeNaoEncontradaException) incluindo subclasses dela
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEstadoNaoEncontradoException( EntidadeNaoEncontradaException e )
	{
		// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
		Problema problema = Problema.builder()
				.dataHora( LocalDateTime.now() )
				.mensagem( e.getMessage() ).build();

		return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( problema );
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException( EntidadeEmUsoException e )
	{
		// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
		Problema problema = Problema.builder()
				.dataHora( LocalDateTime.now() )
				.mensagem( e.getMessage() ).build();

		return ResponseEntity.status( HttpStatus.CONFLICT ).body( problema );
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException( NegocioException e )
	{
		// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
		Problema problema = Problema.builder()
				.dataHora( LocalDateTime.now() )
				.mensagem( e.getMessage() ).build();

		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( problema );
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException()
	{
		// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
		Problema problema = Problema.builder()
				.dataHora( LocalDateTime.now() )
				.mensagem( "O tipo de mídia não é aceito." ).build();

		return ResponseEntity.status( HttpStatus.UNSUPPORTED_MEDIA_TYPE ).body( problema );
	}

}
