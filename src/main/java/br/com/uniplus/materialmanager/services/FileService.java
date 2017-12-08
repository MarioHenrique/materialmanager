package br.com.uniplus.materialmanager.services;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import br.com.uniplus.materialmanager.entities.Material;
import br.com.uniplus.materialmanager.entities.Turma;
import br.com.uniplus.materialmanager.entities.User;
import br.com.uniplus.materialmanager.repository.MaterialRepository;
import br.com.uniplus.materialmanager.repository.TurmaRepository;

@Service
public class FileService {

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	private static final String BUCKET_NAME = "material-manager";
	private static final String BUCKET_URL_NAME = "https://s3-sa-east-1.amazonaws.com/material-manager/";

	@Async
	public void uploadMaterialS3(Long turmaId, String file, String fileName, String name) {

		try {

			AmazonS3 s3client = new AmazonS3Client();
			File tempFile = new File(file);
			String key = UUID.randomUUID().toString();
			s3client.putObject(new PutObjectRequest(BUCKET_NAME,key,tempFile)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			tempFile.delete();

			Turma turma = turmaRepository.findOne(turmaId);

			Material material = new Material();
			material.setArquivado(Boolean.FALSE);
			material.setName(name);
			material.setTurma(turma);
			material.setLink(BUCKET_URL_NAME + key);

			materialRepository.save(material);

			List<User> users = turma.getUsers();
			List<String> emails = users.stream().map(u -> u.getUsername()).collect(Collectors.toList());

			if (!emails.isEmpty())
				noticar(emails.toArray(new String[emails.size()]), turma.getMateria(), material.getLink());

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

	}

	private void noticar(String[] students, String turma, String material) {

		String HTMLBODY = "<h1>Novo material adicionado na turma " + turma
				+ "</h1><p>Para baixar acesse o link <a href='" + material + "'>Baixar</a>";
		String TEXTBODY = "Um novo material foi colocado na turma " + turma + ", Acesse para conferir.";

		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_EAST_1).build();
			SendEmailRequest request = new SendEmailRequest()
					.withDestination(new Destination().withToAddresses(students))
					.withMessage(new Message()
							.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(HTMLBODY))
									.withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
							.withSubject(new Content().withCharset("UTF-8").withData("Notificação de novo material")))
					.withSource("noreplymaterialmanager37@gmail.com");
			client.sendEmail(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
