package br.com.uniplus.materialmanager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.uniplus.materialmanager.dto.request.ConviteRequest;
import br.com.uniplus.materialmanager.dto.request.TurmaDTO;
import br.com.uniplus.materialmanager.dto.request.RemocaoTurmaRequest;
import br.com.uniplus.materialmanager.dto.request.RespostaConviteRequest;
import br.com.uniplus.materialmanager.dto.response.TurmaResponse;
import br.com.uniplus.materialmanager.exception.UserAlreadyExistException;
import br.com.uniplus.materialmanager.exception.UserNotFoundException;
import br.com.uniplus.materialmanager.services.AlunoService;
import br.com.uniplus.materialmanager.services.TurmaService;

@Controller
public class TurmaController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private TurmaService turmaSevice;
	
	@Autowired
	private AlunoService alunoService;
	
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
	
	@GetMapping("/turmas/board")
	public String turmaBoard(@RequestParam(value="id",required=false) Long turmaId ) {
		session.setAttribute("turmaId",turmaId);
		return "/turma/turmas-board";
	}
	
	@GetMapping("/turmas/editar")
	public ModelAndView turmaEditar(@RequestParam(value="id",required=false) Long turmaId ) {
		ModelAndView model = new ModelAndView("/turma/turmas-editar");
		session.setAttribute("turmaId",turmaId);
		TurmaResponse turma = turmaSevice.buscaTurma(turmaId);
		model.addObject("materia", turma.getMateria());
		model.addObject("semestre", turma.getSemestre());
		return model;
	}
	
	@PostMapping("/turmas/editar")
	public ModelAndView editarTurma(@Valid TurmaDTO request,BindingResult result) {
		ModelAndView model = new ModelAndView("/turma/turmas-editar");
		
		if(result.hasErrors()) {
			model.setViewName("/turma/turmas-nova");
			model.addObject("materia", request.getMateria());
			model.addObject("semestre", request.getSemestre());
			model.addObject("errors",result.getFieldErrors());
			return model;
		}
		
		Long turmaId = (Long)session.getAttribute("turmaId");
		
		turmaSevice.editarTurma(request,turmaId);
		
		return turmas();
	}
	
	@PostMapping("/turmas/remove")
	public ModelAndView turmaRemover(RemocaoTurmaRequest request) {
		turmaSevice.remove(request.getTurmaId());
		return turmas();
	}
	
	@PostMapping("/turmas")
	public ModelAndView novaTurma(@Valid TurmaDTO request,BindingResult result) {
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
	
	@GetMapping("/turmas/convite")
	public String paginaConvite() {
		return "/turma/turmas-convite";
	}
	
	@PostMapping("/turmas/convite/resposta")
	public String conviteResposta(RespostaConviteRequest request) {
		alunoService.responderConvite(request);
		return "redirect:/notificacoes";
	}
	
	@PostMapping("/turmas/convite")
	public ModelAndView convidar(@Valid ConviteRequest request,BindingResult result) {
		ModelAndView model = new ModelAndView("/turma/turmas-convite");
		try {
			
			if(result.hasErrors()) {
				model.addObject("errors",result.getFieldErrors());
				return model;
			}
			
			Long turmaId = (Long)session.getAttribute("turmaId");
			alunoService.enviarConvite(request,turmaId);
			model.addObject("message","Convite enviado !");
		} catch (UserNotFoundException e) {
			model.addObject("error","Aluno não localizado !");
		} catch(UserAlreadyExistException e) {
			model.addObject("error","Aluno já adicionado na turma !");
		}
		return model;
	}

}
