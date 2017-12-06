package br.com.uniplus.materialmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificacoesController {

	@GetMapping("/notificacoes")
	public String notigicacoes() {
		return "/notificacoes";
	}
	
}
