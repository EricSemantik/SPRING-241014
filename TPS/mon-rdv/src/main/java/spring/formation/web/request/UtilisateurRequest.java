package spring.formation.web.request;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UtilisateurRequest {
	private Long id;
	@NotEmpty(message="Login obligatoire")
	private String identifiant;
	@NotEmpty(message="Mot de passe obligatoire")
	@Pattern(regexp = "^[a-zA-Z0-9]{8,}", message ="Mot de passe invalide (débrouilles-toi)")
	private String motDePasse;
	private boolean active = true;
	private Set<String> roles = new HashSet<>();

	public UtilisateurRequest() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
