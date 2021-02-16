package com.projetos.algafood.api.controller.model;

import java.util.List;

//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.projetos.algafood.domain.model.Cozinha;

import lombok.Data;
import lombok.NonNull;

/**
 * Classe reposnável por normalizar o retorno do XML
 * Padrão:
 * <List>
 *     <Item>
 *         <id><id/>
 *  	   <nome><nome/>
 *     <Item/>
 * <List/>
 *
 * Normalizado:
 * <cozinhas>
 *     <cozinha>
 *         <id><id/>
 *         <nome><nome/>
 *     <cozinha/>
 * <cozinhas/>
 */
//@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXmlWrapper
{
	// Lombok só gera construtores em propriedades obrigatórias
	// Por isso anotamos com NonNull do lombok para ser gerado o construtor
//	@NonNull
//	@JacksonXmlProperty(localName = "cozinha")
//	@JacksonXmlElementWrapper(useWrapping = false)
//	private List<Cozinha> cozinhas;
}
