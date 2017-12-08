package br.com.uniplus.materialmanager.dto.response;

import br.com.uniplus.materialmanager.entities.Material;
import lombok.Data;

@Data
public class MaterialResponse {

	private Long id;
	private String name;
	private String link;
	
	public static MaterialResponse from(Material material) {
		MaterialResponse materialResponse = new MaterialResponse();
		
		materialResponse.setName(material.getName());
		materialResponse.setLink(material.getLink());
		materialResponse.setId(material.getId());
		
		return materialResponse;
	}
	
}
