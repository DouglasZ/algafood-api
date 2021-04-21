package com.projetos.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projetos.algafood.domain.exception.EntidadeEmUsoException;
import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.exception.NegocioException;

// Informa que este componente é possível adicionar ExceptionHandler que captura todas as exceções de todos os Controladores do projeto.
// Para que seja possível trata-las em um único local.
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler
{
	/**
	 * Método responsável por tratar todas as exceções que são desse tipo (EntidadeNaoEncontradaException) incluindo subclasses dela
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEstadoNaoEncontradoException( EntidadeNaoEncontradaException ex, WebRequest request )
	{
		return handleExceptionInternal( ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request );
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException( EntidadeEmUsoException ex, WebRequest request )
	{
		return handleExceptionInternal( ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request );
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException( NegocioException ex, WebRequest request )
	{
		return handleExceptionInternal( ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request );
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal( Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request )
	{
		if ( body == null )
		{
			// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
			body = Problema.builder()
					.dataHora( LocalDateTime.now() )
					.mensagem( status.getReasonPhrase() ).build();
		}
		else if ( body instanceof String )
		{
			// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
			body = Problema.builder()
					.dataHora( LocalDateTime.now() )
					.mensagem( (String) body ).build();
		}

		return super.handleExceptionInternal( ex, body, headers, status, request );
	}
}
