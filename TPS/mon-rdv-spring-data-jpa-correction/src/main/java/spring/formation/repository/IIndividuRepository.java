package spring.formation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import spring.formation.model.Individu;
import spring.formation.model.Patient;
import spring.formation.model.Praticien;
import spring.formation.model.Secretaire;
import spring.formation.repository.custom.IndividuRepositoryCustom;

public interface IIndividuRepository extends JpaRepository<Individu, Long>, IndividuRepositoryCustom {
	@Query("select p from Patient p")
	List<Patient> findAllPatient();
	
	@Query("from Praticien p")
	List<Praticien> findAllPraticien();
	
	@Query("from Secretaire")
	List<Secretaire> findAllSecretaire();
	
	Optional<Individu> findByUtilisateurIdentifiant(String identifiant);
	
	@Query("select p from Praticien p join p.specialites spe where spe.nom = ?1")
	List<Praticien> findAllPraticienBySpecialite(String nom);
}
