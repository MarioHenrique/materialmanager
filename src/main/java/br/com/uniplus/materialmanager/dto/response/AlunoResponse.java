package br.com.uniplus.materialmanager.dto.response;

import br.com.uniplus.materialmanager.entities.User;
import lombok.Data;

@Data
public class AlunoResponse {

	private String nome;
	private String email;
	
	public static AlunoResponse from(User aluno) {
		AlunoResponse alunoResponse = new AlunoResponse();
		alunoResponse.setNome(aluno.getName());
		alunoResponse.setEmail(aluno.getUsername());
		return alunoResponse;
	}
	
}
