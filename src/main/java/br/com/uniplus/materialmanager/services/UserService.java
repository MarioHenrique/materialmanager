package br.com.uniplus.materialmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.uniplus.materialmanager.dto.request.RegisterRequestDTO;
import br.com.uniplus.materialmanager.entities.User;
import br.com.uniplus.materialmanager.exception.UserAlreadyExistException;
import br.com.uniplus.materialmanager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void register(RegisterRequestDTO userRequested) throws UserAlreadyExistException {
		User user = userRepository.findByUsername(userRequested.getUsername());
		
		if(user != null) {
			throw new UserAlreadyExistException();
		}
		
		User newUser = new User();
		newUser.setName(userRequested.getName());
		newUser.setUsername(userRequested.getUsername());
		newUser.setPassword(encoder.encode(userRequested.getPassword()));
		
		userRepository.save(newUser);
	}
	
}
