package br.com.uniplus.materialmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.uniplus.materialmanager.dto.request.NovaTurmaDTO;
import br.com.uniplus.materialmanager.dto.response.TurmaResponse;
import br.com.uniplus.materialmanager.entities.Turma;
import br.com.uniplus.materialmanager.services.TurmaService;

@Controller
public class TurmaController {

	@Autowired
	private TurmaService turmaSevice;
	
	@GetMapping("/turmas")
	public ModelAndView turmas() {
		ModelAndView model = new ModelAndView("/turma/turmas");
		List<TurmaResponse> turmas = turmaSevice.buscarTurmas();
		model.addObject("turmas",turmas);
		return model;
	}
	
	@GetMapping("/turmas/nova")
	public String novaTurmaPagina() {
		return "/turma/turmas-nova";
	}
	
	@PostMapping("/turmas")
	public ModelAndView novaTurma(@Valid NovaTurmaDTO request,BindingResult result) {
		ModelAndView model = new ModelAndView("/turma/turmas-nova");
		
		if(result.hasErrors()) {
			model.setViewName("/turma/turmas-nova");
			model.addObject("materia", request.getMateria());
			model.addObject("semestre", request.getSemestre());
			model.addObject("errors",result.getFieldErrors());
			return model;
		}
		
		turmaSevice.criarTurma(request);
		
		return turmas();
	}
	
	
	
}
