package br.com.uniplus.materialmanager.entities;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "convites")
@Data
public class Convite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private User student;
	
	@OneToOne
	private User teacher;
	
	@ManyToOne
	private Turma turma;
	
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacao;
	
	private Boolean visualizado;
	
}
