package com.projetos.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

// Só inclui na representação JSON, se o valor da propriedade não estiver nulo
@JsonInclude(Include.NON_NULL)
@Getter
// Builder é um padrão de projeto. Utilizado no lugar do Setter
@Builder
public class Problem
{
	// Propriedades do padrão RFC 7807 (https://tools.ietf.org/html/rfc7807)
	private Integer status;
	private String type;
	private String title;
	private String detail;
}
