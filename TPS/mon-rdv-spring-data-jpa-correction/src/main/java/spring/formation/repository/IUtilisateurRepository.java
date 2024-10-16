package spring.formation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.formation.model.Utilisateur;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	Optional<Utilisateur> findByIdentifiant(String identifiant); // implicite : select u from Utilisateur u where u.identifiant = ?1
}
