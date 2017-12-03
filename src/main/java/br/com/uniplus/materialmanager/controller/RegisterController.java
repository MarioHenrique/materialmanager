package br.com.uniplus.materialmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.uniplus.materialmanager.dto.request.RegisterRequestDTO;
import br.com.uniplus.materialmanager.exception.UserAlreadyExistException;
import br.com.uniplus.materialmanager.services.UserService;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String register() {
		return "/register";
	}
	
	@PostMapping("/register")
	public ModelAndView doRegister(@Valid RegisterRequestDTO request, BindingResult result) {
		ModelAndView model = new ModelAndView("/login");
		try {
			if(result.hasErrors()) {
				model.setViewName("/register");
				setFormAttributes(request, model);
				model.addObject("errors",result.getFieldErrors());
				return model;
			}
			
			if(!request.getPassword().equals(request.getRepeatPassword())) {
				model.setViewName("/register");
				setFormAttributes(request, model);
				model.addObject("error","A senha e o repetir senha não são iguais !");
				return model;
			}
			
			userService.register(request);
			
			model.addObject("message","Usuario criado com sucesso !");
			
		}catch(UserAlreadyExistException e) {
			model.setViewName("/register");
			model.addObject("error","Usuário já cadastrado !");
			setFormAttributes(request, model);
		}
		return model;
	}

	private void setFormAttributes(RegisterRequestDTO request, ModelAndView model) {
		model.addObject("username", request.getUsername());
		model.addObject("name", request.getName());
		model.addObject("password", request.getPassword());
		model.addObject("repeatPassword",request.getRepeatPassword());
		model.addObject("profile",request.getProfile().toString());
	}
	
}
