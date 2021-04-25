package com.projetos.algafood.api.exceptionhandler;

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
	public ResponseEntity<?> handleEntidadeNaoEncontradoException( EntidadeNaoEncontradaException ex, WebRequest request )
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem problem = createProblemBuilder( status, ProblemType.ENTIDADE_NAO_ENCONTRADA, ex.getMessage() ).build();

		return handleExceptionInternal( ex, problem, new HttpHeaders(), status, request );
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException( EntidadeEmUsoException ex, WebRequest request )
	{
		HttpStatus status = HttpStatus.CONFLICT;
		Problem problem = createProblemBuilder( status, ProblemType.ENTIDADE_EM_USO, ex.getMessage() ).build();

		return handleExceptionInternal( ex, problem, new HttpHeaders(), status, request );
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException( NegocioException ex, WebRequest request )
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem problem = createProblemBuilder( status, ProblemType.ERRO_NEGOCIO, ex.getMessage() ).build();

		return handleExceptionInternal( ex, problem, new HttpHeaders(), status, request );
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal( Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request )
	{
		if ( body == null )
		{
			// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
			body = Problem.builder()
					.title( status.getReasonPhrase() )
					.status( status.value() )
					.build();
		}
		else if ( body instanceof String )
		{
			// Builder é um padrão de projeto para construir objetos de uma forma mais fluente
			body = Problem.builder()
					.title( (String) body )
					.status( status.value() )
					.build();
		}

		return super.handleExceptionInternal( ex, body, headers, status, request );
	}

	private Problem.ProblemBuilder createProblemBuilder( HttpStatus status, ProblemType problemType, String detail )
	{
		return Problem.builder()
				.status( status.value() )
				.type( problemType.getUri() )
				.title( problemType.getTitle() )
				.detail( detail );
	}

}
