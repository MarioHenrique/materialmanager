package br.com.uniplus.materialmanager.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.uniplus.materialmanager.dto.request.RegisterRequestDTO;
import br.com.uniplus.materialmanager.entities.Role;
import br.com.uniplus.materialmanager.entities.User;
import br.com.uniplus.materialmanager.exception.UserAlreadyExistException;
import br.com.uniplus.materialmanager.repository.RoleRepository;
import br.com.uniplus.materialmanager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
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
		
		Role role = roleRepository.findByName(userRequested.getProfile().toString());
		if(role == null) {
			role = new Role();
			role.setName(userRequested.getProfile().toString());
			roleRepository.save(role);
		}
		
		HashSet<Role> roles = new HashSet<>();
		roles.add(role);
		
		newUser.setRoles(roles);
		
		userRepository.save(newUser);
	}
	
}
