package br.com.uniplus.materialmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MateriaisController {

	@GetMapping("/materiais")
	public String getMateriais() {
		return "/materiais";
	}
	
}
