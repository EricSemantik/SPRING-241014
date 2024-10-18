package spring.formation.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("secretaire")
@JsonView(Views.ViewBasic.class)
public class Secretaire extends Individu {
	@ManyToMany
	@JoinTable(name = "secretaire_praticien", joinColumns = @JoinColumn(name = "secretaire_id"), inverseJoinColumns = @JoinColumn(name = "praticien_id"))
	@JsonView(Views.ViewNone.class)
	private List<Praticien> praticiens = new ArrayList<Praticien>();

	public Secretaire() {
		super();
	}

	public Secretaire(Civilite civilite, String nom, String prenom, String email, String telephone) {
		super(civilite, nom, prenom, email, telephone);
	}

	public List<Praticien> getPraticiens() {
		return praticiens;
	}

	public void setPraticiens(List<Praticien> praticiens) {
		this.praticiens = praticiens;
	}
}
