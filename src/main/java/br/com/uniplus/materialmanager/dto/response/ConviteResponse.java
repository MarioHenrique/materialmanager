package br.com.uniplus.materialmanager.dto.response;

import br.com.uniplus.materialmanager.entities.Convite;
import lombok.Data;

@Data
public class ConviteResponse {

	private Long id;
	private String materia;
	private String professor;
	private Integer semestre;
	
	public static ConviteResponse from(Convite convite) {
		ConviteResponse conviteResponse = new ConviteResponse();
		conviteResponse.setId(convite.getId());
		conviteResponse.setMateria(convite.getTurma().getMateria());
		conviteResponse.setProfessor(convite.getTeacher().getName());
		conviteResponse.setSemestre(convite.getTurma().getSemestre());
		return conviteResponse;
	}
	
}
