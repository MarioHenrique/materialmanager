package br.com.uniplus.materialmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.uniplus.materialmanager.entities.Convite;

@Repository
public interface ConviteRepository extends CrudRepository<Convite, Long> {

	List<Convite> findByStudentIdAndVisualizadoIsFalse(Long studentId);
	
}
