package br.com.uniplus.materialmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.uniplus.materialmanager.entities.Turma;

@Repository
public interface TurmaRepository extends CrudRepository<Turma, Long> {

	List<Turma> findByTeacherId(Long id);
	
}
