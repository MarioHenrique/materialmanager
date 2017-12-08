package br.com.uniplus.materialmanager.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "turmas")
@Data
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String materia;
	
	private Integer semestre;
	
	@OneToOne
	private User teacher;
	
	private Boolean arquivada;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_turmas", joinColumns = { @JoinColumn(name = "turma_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private List<User> users;
	
}
