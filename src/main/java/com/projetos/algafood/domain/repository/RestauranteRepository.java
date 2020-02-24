package com.projetos.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>
{
}
