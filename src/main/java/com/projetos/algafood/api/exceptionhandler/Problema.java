package com.projetos.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
// Builder é um padrão de projeto. Utilizado no lugar do Setter
@Builder
public class Problema
{
	private LocalDateTime dataHora;
	private String mensagem;
}
