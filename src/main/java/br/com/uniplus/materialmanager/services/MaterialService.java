package br.com.uniplus.materialmanager.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uniplus.materialmanager.dto.response.MaterialResponse;
import br.com.uniplus.materialmanager.entities.Material;
import br.com.uniplus.materialmanager.repository.MaterialRepository;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository materialRepository;
	
	public List<MaterialResponse> getMateriais(Long turmaId){
		List<Material> turmas = materialRepository.findByTurmaIdAndArquivadoIsFalse(turmaId);
		return turmas.stream().filter(t->t.getArquivado().equals(Boolean.FALSE)).map(m-> MaterialResponse.from(m)).collect(Collectors.toList());
	}

	public void remove(Long materialId) {
		Material material = materialRepository.findOne(materialId);
		material.setArquivado(Boolean.TRUE);
		materialRepository.save(material);
	}
	
}
