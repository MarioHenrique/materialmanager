package br.com.uniplus.materialmanager.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.uniplus.materialmanager.dto.request.TurmaDTO;
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
	
	public void criarTurma(TurmaDTO request) {
		
		String currentUser = getCurrentUser();
		
		User userLogged = userRepository.findByUsername(currentUser);
		
		Turma turma = new Turma();
		turma.setSemestre(request.getSemestre());
		turma.setMateria(request.getMateria());
		turma.setTeacher(userLogged);
		turma.setArquivada(Boolean.FALSE);
		
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
		List<Turma> turmas = turmaRepository.findByTeacherIdAndArquivadaIsFalse(userLogged.getId());
		turmas.addAll(userLogged.getTurmas().stream().filter(t->t.getArquivada().equals(Boolean.FALSE)).collect(Collectors.toList()));
		return turmas.stream().map(t-> TurmaResponse.from(t)).collect(Collectors.toList());
	}

	public void remove(Long turmaId) {
		Turma turma = turmaRepository.findOne(turmaId);
		turma.setArquivada(Boolean.TRUE);
		turmaRepository.save(turma);
	}

	public void editarTurma(TurmaDTO request, Long turmaId) {
		Turma turma = turmaRepository.findOne(turmaId);
		turma.setMateria(request.getMateria());
		turma.setSemestre(request.getSemestre());
		turmaRepository.save(turma);
	}

	public TurmaResponse buscaTurma(Long turmaId) {
		Turma turma = turmaRepository.findOne(turmaId);
		return TurmaResponse.from(turma);
	}
	
}

