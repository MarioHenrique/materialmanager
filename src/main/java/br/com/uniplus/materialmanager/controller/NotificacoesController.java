package br.com.uniplus.materialmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.uniplus.materialmanager.dto.response.ConviteResponse;
import br.com.uniplus.materialmanager.services.AlunoService;

@Controller
public class NotificacoesController {

	@Autowired
	private AlunoService alunoService;
	
	@GetMapping("/notificacoes")
	public ModelAndView notigicacoes() {
		ModelAndView model = new ModelAndView("/notificacoes");
		List<ConviteResponse> convites = alunoService.getConvites();
		model.addObject("convites", convites);
		return model;
	}
	
}
