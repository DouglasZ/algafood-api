package com.projetos.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.algafood.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>
{
}
