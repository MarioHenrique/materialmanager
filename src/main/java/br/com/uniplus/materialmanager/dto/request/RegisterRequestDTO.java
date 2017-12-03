package br.com.uniplus.materialmanager.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.uniplus.materialmanager.enums.Profiles;
import lombok.Data;

@Data
public class RegisterRequestDTO {

	@NotEmpty(message="O Email é obrigatório")
	@Email(message="O Email é invalido")
	private String username;

	@NotEmpty(message="O Nome é obrigatório")
	private String name;

	@NotEmpty(message="É preciso repetir o password para continuar")
	@Size(min=8,message="A confirmação da senha precisa ter no mínimo 8 caracteres")
	private String repeatPassword;

	@NotEmpty(message="O password é obrigatório")
	@Size(min=8,message="A senha precisa ter no mínimo 8 caracteres")
	private String password;

	@NotNull(message="É preciso selecionar um perfil")
	private Profiles profile;

}
