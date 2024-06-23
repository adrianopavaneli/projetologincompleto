package br.com.pavaneli.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pavaneli.projeto.entity.Recurso;

public interface RecursoRepository extends JpaRepository<Recurso, Long>{

}
