package br.com.uniplus.materialmanager.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.uniplus.materialmanager.services.FileService;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;
	
	@Autowired
	private HttpSession session;
	
	private static String UPLOADED_FOLDER = "/tmp/";

	@PostMapping("/upload")
	public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("name") String name) {
		ModelAndView model = new ModelAndView("/turma/turmas-material");
		
		if(StringUtils.isEmpty(name)) {
			model.addObject("error","Digite o nome do arquivo para enviar !");
			return model;
		}
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			
			Long turmaId = (Long)session.getAttribute("turmaId");
			
			fileService.uploadMaterialS3(turmaId,UPLOADED_FOLDER + file.getOriginalFilename(),file.getOriginalFilename(),name);
			
			model.addObject("message","Arquivo enviado, em breve ficará disponivel !");
		} catch (IOException e) {
			e.printStackTrace();
			model.addObject("error","Não foi possivel enviar o arquivo !");
		}

		return model;
	}

}
