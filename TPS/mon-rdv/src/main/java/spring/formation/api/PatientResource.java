package spring.formation.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import spring.formation.model.Patient;
import spring.formation.model.Views;
import spring.formation.repository.IIndividuRepository;
import spring.formation.repository.IUtilisateurRepository;

@RestController
@RequestMapping("/api/patient")
public class PatientResource {

	private IIndividuRepository individuRepo;

	private IUtilisateurRepository utilisateurRepo;

	public PatientResource(IIndividuRepository individuRepo, IUtilisateurRepository utilisateurRepo) {
		super();
		this.individuRepo = individuRepo;
		this.utilisateurRepo = utilisateurRepo;
	}

	@GetMapping("")
	@JsonView(Views.ViewPatient.class)
	public List<Patient> getAll() {
		return this.individuRepo.findAllPatient();
	}

	@GetMapping("/{id}")
	public Patient get(@PathVariable Long id) {
		return (Patient) this.individuRepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

//	@GetMapping("/{id}/detail")
//	public Patient getDetail(@PathVariable Long id) {
//		return this.individuRepo.findPatientByIdWithConsultations(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//	}

	@PostMapping("")
	public Patient post(@RequestBody Patient patient) {
		patient = individuRepo.save(patient);

		return patient;
	}

	@PutMapping("/{id}")
	public Patient put(@RequestBody Patient patient, @PathVariable Long id) {
		if (id != patient.getId() || !individuRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le patient ne peut être mis à jour.");
		}

		return individuRepo.save(patient);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!individuRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient ne peut être supprimé car inexistant.");
		}

		individuRepo.deleteById(id);
	}

}
