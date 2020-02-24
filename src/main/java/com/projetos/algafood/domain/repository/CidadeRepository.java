package com.projetos.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.algafood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>
{
}
