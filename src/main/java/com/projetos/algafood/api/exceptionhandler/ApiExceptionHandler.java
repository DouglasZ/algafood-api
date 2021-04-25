package com.projetos.algafood.api.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.projetos.algafood.domain.exception.EntidadeEmUsoException;
import com.projetos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.projetos.algafood.domain.exception.NegocioException;

// Informa que este componente é possível adicionar ExceptionHandler que captura todas as exceções de todos os Controladores do projeto.
// Para que seja possível trata-las em um único local.
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler
{
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable( HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request )
	{
		Throwable rootCouse = ExceptionUtils.getRootCause( ex );

		// Verificamos se os valores dos atributos correspondem corretamente ao tipo.
		// Se não corresponde, devemos mostrar uma mensagem mais específica.
		if ( rootCouse instanceof InvalidFormatException )
		{
			return handleInvalidFormatException( (InvalidFormatException) rootCouse, headers, status, request );
		}

		// Caso contrário, informamos uma mensagem mais genérica.
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		Problem problem = createProblemBuilder( status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail ).build();

		return handleExceptionInternal( ex, problem, new HttpHeaders(), status, request );
	}

	/**
	 * Método responsável por detalhar qual a propriedade está com o valor inválido.
	 */
	private ResponseEntity<Object> handleInvalidFormatException( InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request )
	{
		String path = ex.getPath().stream()
				.map( ref -> ref.getFieldName() )
				.collect( Collectors.joining( "." ) );

		String detail = String.format( "A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. " +
				"Corrija e informe um valor compatível com o tipo %s.", path, ex.getValue(), ex.getTargetType().getSimpleName() );

		Problem problem = createProblemBuilder( status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail ).build();

		return handleExceptionInternal( ex, problem, headers, status, request );
	}

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
