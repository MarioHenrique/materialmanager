package br.com.uniplus.materialmanager.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.uniplus.materialmanager.dto.request.ConviteRequest;
import br.com.uniplus.materialmanager.dto.request.RespostaConviteRequest;
import br.com.uniplus.materialmanager.dto.response.ConviteResponse;
import br.com.uniplus.materialmanager.entities.Convite;
import br.com.uniplus.materialmanager.entities.Turma;
import br.com.uniplus.materialmanager.entities.User;
import br.com.uniplus.materialmanager.exception.UserNotFoundException;
import br.com.uniplus.materialmanager.repository.ConviteRepository;
import br.com.uniplus.materialmanager.repository.TurmaRepository;
import br.com.uniplus.materialmanager.repository.UserRepository;

@Service
public class AlunoService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private ConviteRepository conviteRepository;
	
	public void enviarConvite(ConviteRequest request, Long turmaId) throws UserNotFoundException {
		
		User student = userRepository.findByUsername(request.getEmail());
		if(student == null) {
			throw new UserNotFoundException();
		}
		
		String currentUser = getCurrentUser();
		
		User teacher = userRepository.findByUsername(currentUser);
		
		Turma turma = turmaRepository.findOne(turmaId);
		
		Convite convite = new Convite();
		convite.setDataSolicitacao(new Date());
		convite.setStudent(student);
		convite.setTeacher(teacher);
		convite.setTurma(turma);
		convite.setVisualizado(Boolean.FALSE);
		
		conviteRepository.save(convite);
		
	}
	
	private String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUser = authentication.getName();
		return currentUser;
	}
	
	public List<ConviteResponse> getConvites(){
		String currentUser = getCurrentUser();
		User student = userRepository.findByUsername(currentUser);
		List<Convite> convites = conviteRepository.findByStudentIdAndVisualizadoIsFalse(student.getId());
		return convites.stream().map(c-> ConviteResponse.from(c)).collect(Collectors.toList());
	}

	public void responderConvite(RespostaConviteRequest request) {
		Convite convite = conviteRepository.findOne(request.getConviteId());
		String currentUser = getCurrentUser();
		User user = userRepository.findByUsername(currentUser);
		
		
		if(request.getResposta().equals(Boolean.TRUE)) {
			Turma turma = convite.getTurma();
			user.getTurmas().add(turma);
			userRepository.save(user);
		}
		
		convite.setVisualizado(Boolean.TRUE);
		conviteRepository.save(convite);
		
	}
	
}
