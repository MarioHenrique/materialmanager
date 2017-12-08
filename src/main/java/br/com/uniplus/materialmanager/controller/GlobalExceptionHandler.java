package br.com.uniplus.materialmanager.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MultipartException.class)
	public ModelAndView handleError1(MultipartException e) {
		ModelAndView model = new ModelAndView("/turma/turmas-material");
		model.addObject("error","O tamanho maximo de um arquivo é 50 mb!");
		return model;
	}

}
