package br.com.uniplus.materialmanager.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.uniplus.materialmanager.dto.request.NovaTurmaDTO;
import br.com.uniplus.materialmanager.dto.response.TurmaResponse;
import br.com.uniplus.materialmanager.entities.Turma;
import br.com.uniplus.materialmanager.entities.User;
import br.com.uniplus.materialmanager.repository.TurmaRepository;
import br.com.uniplus.materialmanager.repository.UserRepository;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void criarTurma(NovaTurmaDTO request) {
		
		String currentUser = getCurrentUser();
		
		User userLogged = userRepository.findByUsername(currentUser);
		
		Turma turma = new Turma();
		turma.setSemestre(request.getSemestre());
		turma.setMateria(request.getMateria());
		turma.setTeacher(userLogged);
		
		turmaRepository.save(turma);
	}

	private String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUser = authentication.getName();
		return currentUser;
	}

	public List<TurmaResponse> buscarTurmas() {
		String currentUser = getCurrentUser();
		User userLogged = userRepository.findByUsername(currentUser);
		List<Turma> turmas = turmaRepository.findByTeacherId(userLogged.getId());
		return turmas.stream().map(t-> TurmaResponse.from(t)).collect(Collectors.toList());
	}
	
}

