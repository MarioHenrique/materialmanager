package br.com.uniplus.materialmanager.dto.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ConviteRequest {

	@Email(message="Email invalido")
	@NotEmpty(message="É preciso passar o email")
	private String email;
	
}
