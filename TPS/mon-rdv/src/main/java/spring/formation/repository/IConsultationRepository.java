package spring.formation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import spring.formation.model.Consultation;

public interface IConsultationRepository extends JpaRepository<Consultation, Long> {
	List<Consultation> findAllByPraticien(Long idPraticien);

	List<Consultation> findAllByPatient(@Param("idPatient") Long id);
}
