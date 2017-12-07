package br.com.uniplus.materialmanager.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class TurmaDTO {

	@NotEmpty(message="É preciso passar o nome da materia")
	private String materia;
	
	@Min(value=1,message="Selecione um semestre entre 1 e 8")
	@Max(value=8,message="Selecione um semestre entre 1 e 8")
	private Integer semestre;

}
