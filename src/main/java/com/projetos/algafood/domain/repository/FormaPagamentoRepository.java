package com.projetos.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>
{
}
