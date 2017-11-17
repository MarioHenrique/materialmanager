package br.com.uniplus.materialmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.uniplus.materialmanager.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

	Role findByName(String name);	
	
}
