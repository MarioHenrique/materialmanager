package br.com.uniplus.materialmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.uniplus.materialmanager.entities.Material;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Long> {

	List<Material> findByTurmaIdAndArquivadoIsFalse(Long turmaId);
	
}
