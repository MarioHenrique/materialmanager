package br.com.uniplus.materialmanager.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.uniplus.materialmanager.entities.Turma;
import br.com.uniplus.materialmanager.entities.User;
import lombok.Data;

@Data
public class TurmaResponse {

	private Long id;
	private String materia;
	private String teacher;
	private Integer semestre;
	private List<AlunoResponse> alunos;

	public static TurmaResponse from(Turma turma) {
		TurmaResponse turmaResponse = new TurmaResponse();
		turmaResponse.setMateria(turma.getMateria());
		turmaResponse.setTeacher(turma.getTeacher().getName());
		turmaResponse.setSemestre(turma.getSemestre());
		turmaResponse.setId(turma.getId());

		List<User> users = turma.getUsers();
		if (users != null && !users.isEmpty()) {
			List<AlunoResponse> alunos = users.stream().map(u -> AlunoResponse.from(u)).collect(Collectors.toList());
			turmaResponse.setAlunos(alunos);
		} else {
			turmaResponse.setAlunos(new ArrayList<>());
		}

		return turmaResponse;
	}

}
