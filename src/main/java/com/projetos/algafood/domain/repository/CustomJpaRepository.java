package com.projetos.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// Indentifica que este Repositório não deve ser instanciado pelo Spring Data JPA, ou seja, 
// O Spring Data JPA não deve instaciar uma implementação para esta interface, ele deve ignorar.
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID>
{
    Optional<T> buscarPrimeiro();
}
