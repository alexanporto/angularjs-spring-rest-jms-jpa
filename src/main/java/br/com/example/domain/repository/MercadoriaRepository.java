package br.com.example.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.example.domain.model.Mercadoria;


@Repository
public interface MercadoriaRepository extends JpaRepository<Mercadoria, Integer> {
	
}
