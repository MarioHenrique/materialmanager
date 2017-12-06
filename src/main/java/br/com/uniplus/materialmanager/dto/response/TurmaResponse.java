package br.com.uniplus.materialmanager.dto.response;

import br.com.uniplus.materialmanager.entities.Turma;
import lombok.Data;

@Data
public class TurmaResponse {

	private String materia;
	private String teacher;
	private Integer semestre; 
	
	public static TurmaResponse from(Turma turma) {
		TurmaResponse turmaResponse = new TurmaResponse();
		turmaResponse.setMateria(turma.getMateria());
		turmaResponse.setTeacher(turma.getTeacher().getName());
		turmaResponse.setSemestre(turma.getSemestre());
		return turmaResponse;
	}
	
}
